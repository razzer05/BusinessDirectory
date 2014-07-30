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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;

public class CompaniesListFragment extends SherlockListFragment {


	public CompaniesListFragment(){
		
	}
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return null;
	}
	

	@Override
	public void onStart() {
		super.onStart();
		URL url = null;
		try {
			url = new URL("http://www.directory4u.co.uk/json_android/json_companies.php");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new CompaniesListFragment.getHTTPList().execute(url);
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
	
	protected class getHTTPList extends AsyncTask<URL, Void, JSONArray>{
		
		
		@Override
		protected JSONArray doInBackground(URL... params) {
			JSONArray object = null;
			HttpURLConnection connection = null;
			InputStream inputstream = null;
			Log.v(getClass().getName(), "do in background");
			try {
			    connection = (HttpURLConnection) params[0].openConnection();
				connection.setReadTimeout(10000);
				connection.setConnectTimeout(15000);
				connection.setRequestMethod("POST");
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.connect();
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
				object = new JSONArray(jsonText);
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
		
		
		protected void onPostExecute(JSONArray array) {
			ArrayList<SimpleCompany> list = new ArrayList<SimpleCompany>();
			for(int i = 0; i < array.length(); i++){
		        JSONObject obj;
				try {
					obj = array.getJSONObject(i);
			        SimpleCompany sc = new SimpleCompany(obj);
			        list.add(sc);
				} catch (JSONException e) {
					e.printStackTrace();
				}
		         
		    }
			Iterator<SimpleCompany> it = list.iterator();
			while(it.hasNext()) {
			Log.v(getClass().getName(),
					it.next().toString());
			}
		}
		
		
	}
}