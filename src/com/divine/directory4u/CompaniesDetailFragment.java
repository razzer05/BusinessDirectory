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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListFragment;
import com.divine.jsonData.SimpleCompanyDetails;

public class CompaniesDetailFragment extends SherlockListFragment {
	
	final static String REC_POS = "position";
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	public static final String MyPREFERENCES = "MyPrefs";
	View view;
	private String apiKey = "AIzaSyDr3YPRA6Xn5lLzPmT2FlcDXmih4OUFRPQ";
	private ListView lvDetail;
	private ListAdapter adapter;
	ArrayList<SimpleCompanyDetails> list = new ArrayList<SimpleCompanyDetails>();
	private ProgressDialog dialog;
	
	//will be used to show the full companies details in-depth..
	public CompaniesDetailFragment(){
		
	}
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		view = inflater.inflate(R.layout.companies, container, false);
		lvDetail = (ListView) view.findViewById(android.R.id.list);
		adapter = (new JSONDetailAdapter(view.getContext(), list));
		lvDetail.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		mPrefs = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		String placeid = mPrefs.getString("PlaceID", "");
		URL url = null;
		try {
			url = new URL("https://maps.googleapis.com/maps/api/place/details/json?" +
					"placeid=" + placeid +
					"&key=" + apiKey);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new CompaniesDetailFragment.getHTTPList().execute(url);
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
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(view.getContext(), "","Loading Data. Please wait...", true);
		}
		
		protected void onPostExecute(JSONObject object) {
			JSONObject obj = object.optJSONObject("result");
			SimpleCompanyDetails sc = new SimpleCompanyDetails(obj);
			list.add(sc);
			((JSONDetailAdapter) adapter).notifyDataSetChanged();
			dialog.dismiss();
		}
	}

	
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}