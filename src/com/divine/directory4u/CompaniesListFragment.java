package com.divine.directory4u;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.divine.jsonData.SimpleCompany;

public class CompaniesListFragment extends SherlockListFragment {

	private String apiKey = "AIzaSyDr3YPRA6Xn5lLzPmT2FlcDXmih4OUFRPQ";
	private double latitude = 53.7406750 , longitude = -1.4883490;
	private String keyword1 = "pub" , keyword2;
	private int defaultRadius = 1000;
	
	public static final String ARG_POSITION = null;
	ArrayList<SimpleCompany> list = new ArrayList<SimpleCompany>();
	ListView lvDetail;
	View view;
	final static String REC_POS = "position";
	int mCurrentRec = -1;
	ListAdapter adapter;
	SharedPreferences mPrefs;
	public static final String MyPREFERENCES = "MyPrefs";
	private String query;
	
	public CompaniesListFragment(){
		
	}
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.companies, container, false);
		lvDetail = (ListView) view.findViewById(android.R.id.list);
		adapter = (new JSONAdapter(view.getContext(), list));
		lvDetail.setAdapter(adapter);
        
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mPrefs = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		latitude = Double.parseDouble(mPrefs.getString("lat", ""));
		longitude = Double.parseDouble(mPrefs.getString("lng", ""));
		query = mPrefs.getString("CatName", "") + "+" + mPrefs.getString("SubCatName", "");
		query = query.replaceAll("\\s","");
		URL url = null;
		try {
			url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?" +
					"query=" + query +
					"&location=" + latitude + "," + longitude + 
					"&radius=" + defaultRadius +
					"&key=" + apiKey);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new CompaniesListFragment.getHTTPList().execute(url);
	}
	
	public void updateView(int position){
		mCurrentRec = position;
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
	
	protected class getHTTPList extends AsyncTask<URL, Void, JSONObject>{
		
		
		@Override
		protected JSONObject doInBackground(URL... params) {
			JSONObject object = null;
			HttpURLConnection connection = null;
			InputStream inputstream = null;
			Log.v(getClass().getName(), "do in background");
			
			try {
			    connection = (HttpURLConnection) params[0].openConnection();
			    Log.v(getClass().getName(), params[0].openConnection().toString());
				connection.setReadTimeout(10000);
				connection.setConnectTimeout(15000);
				connection.setRequestMethod("POST");
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.connect();
				//Potential for a progress bar here..
				//connection.getContentLength()
				inputstream = new BufferedInputStream(connection.getInputStream());
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    inputstream, "UTF-8"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            inputstream.close();
				String jsonText = sb.toString();
	            
				Log.v(getClass().getName(), jsonText);
				object = new JSONObject(jsonText);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					inputstream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				connection.disconnect();
			}
			return object;
		}
		
		
		protected void onPostExecute(JSONObject object) {
			
			JSONArray results = object.optJSONArray("results");
			if(results != null){
				for(int i = 0; i < results.length(); i++){
					JSONObject obj = results.optJSONObject(i);
					
					SimpleCompany sc = new SimpleCompany(obj);
					list.add(sc);
				}
			}
		    
			Iterator<SimpleCompany> it = list.iterator();
			while(it.hasNext()) {
			Log.v(getClass().getName(),
					it.next().toString());
			
			}
			((JSONAdapter) adapter).notifyDataSetChanged();
		}
		
		
	}
}