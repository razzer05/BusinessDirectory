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
import org.json.JSONArray;
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
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.divine.jsonData.JSONListAdapter;
import com.divine.jsonData.SimpleCompany;

/**
 * <p>This class downloads and shows a list of all the companies within the selected categories.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class CompaniesListFragment extends SherlockListFragment {

	/**
	 * Used for telling the content_frame to change fragment.
	 */
	public interface onCompanyListItemSelectedListener{
		public void onCompanyListItemSelected();
	}
	
	/**
	 * The google places unique key for the application.
	 */
	private String apiKey = "AIzaSyDr3YPRA6Xn5lLzPmT2FlcDXmih4OUFRPQ";
	/**
	 * Default value for users latitude.
	 */
	private double latitude = 53.7406750;
	/**
	 * Default value for users longitude.
	 */
	private double longitude = -1.4883490;
	/**
	 * Default radius to prioritise the companies which are nearer.
	 */
	private int defaultRadius = 1000;
	public static final String ARG_POSITION = null;
	/**
	 * An empty array of simpleCompany objects used for filling listview later
	 */
	private ArrayList<SimpleCompany> list = new ArrayList<SimpleCompany>();
	/**
	 * Holder for listview.
	 */
	private ListView lvDetail;
	/**
	 * Holder for view.
	 */
	private View view;
	/**
	 * Holder for the list adapter
	 */
	private ListAdapter adapter;
	/**
	 * Holder for the shared preferences.
	 */
	private SharedPreferences mPrefs;
	/**
	 * Holder for shared preferences editor
	 */
	private SharedPreferences.Editor mEditor;
	/**
	 * Static location of where the shared Preferences data is stored.
	 */
	private static final String MY_PREFERENCES = "MyPrefs";
	/**
	 * Holder for the progress dialog.
	 */
	private ProgressDialog dialog;
	/**
	 * Holder for onCompanyListItemSelectedListener
	 */
	onCompanyListItemSelectedListener mCallBack;
	
	/**
	 * Empty class constructor
	 */
	public CompaniesListFragment(){
	}
	
	/**
	 * Creation of the view which the user sees.
	 * 
	 * @param inflater Instantiates a layout XML file into its corresponding view.
	 * @param container The view group which this view corresponds too.
	 * @param saveInstanceState any data that needs to be recalled for this view.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		view = inflater.inflate(R.layout.companies, container, false);
		lvDetail = (ListView) view.findViewById(android.R.id.list);
		adapter = (new JSONListAdapter(view.getContext(), list));
		lvDetail.setAdapter(adapter);
		return view;
	}
	
	@Override
	/**
	 * Starts up the background thread for downloading the relevant companies to the categories.
	 */
	public void onStart() {
		super.onStart();
		boolean connection = new NetworkConnection(getActivity()).ConnectionAvailable();
		if(connection) {
			// If the details are empty then retrieve data for when back button is used to stop re-downloading.
			if(lvDetail.getCount() == 0){
				mPrefs = this.getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
				// Changes the latitude back to a double from a string.
				latitude = Double.parseDouble(mPrefs.getString("lat", ""));
				// Changes the longitude back to a double from a string.
				longitude = Double.parseDouble(mPrefs.getString("lng", ""));
				// Adds both the category name and subcatgory's name together in a format for json.
				String query = mPrefs.getString("CatName", "") + "+" + mPrefs.getString("SubCatName", "");
				// Removes all the white spaces for json's benefit.
				query = query.replaceAll("\\s","");
				URL url = null;
				try {
					url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?" +
							"query=" + query +
							"&location=" + latitude + "," + longitude + 
							"&radius=" + defaultRadius +
							"&key=" + apiKey);
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				//start the list of companies download.
				new CompaniesListFragment.getHTTPList().execute(url);
			}
			// If details isn't empty then just re-add the ability to click the items.
			else addItemClicks();
		}
		else {
			Toast.makeText(getActivity(), "No Connection", Toast.LENGTH_SHORT).show();
		}
	}


	
	protected class getHTTPList extends AsyncTask<URL, Void, JSONObject>{
		
		HttpURLConnection connection = null;
		@Override
		protected JSONObject doInBackground(URL... params) {
			JSONObject object = null;
			
			InputStream inputstream = null;
			Log.v(getClass().getName(), "do in background");
			
			try {
				// Opens a new connection for input and output related to the url.
			    connection = (HttpURLConnection) params[0].openConnection();
			    Log.v(getClass().getName(), params[0].openConnection().toString());
			    // Sets amount of time to try it has to read data before disconnection.
				connection.setReadTimeout(10000);
				// Sets amount of time to try and connect to a server that is not responding.
				connection.setConnectTimeout(15000);
				// The request type of the connection.
				connection.setRequestMethod("POST");
				// Sets that the connection is allowed to output data.
				connection.setDoInput(true);
				// Sets that the connection is allowed to input data.
				connection.setDoOutput(true);
				// Does the actual connection with all the connection variables setup.
				connection.connect();
				// Stream of data from the connection.
				inputstream = new BufferedInputStream(connection.getInputStream());
				// Reads the data from the stream.
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    inputstream, "UTF-8"), 8);
	            // A new string builder for building the data output in string format.
	            StringBuilder sb = new StringBuilder();
	            // A string for each line of data received.
	            String line = null;
	            // Builds the string while there is data on each new line.
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            // Closes the stream when finished gathering data.
	            inputstream.close();
	            // Transfers all the data collected to one big string from string builder.
				String jsonText = sb.toString();
	            
				Log.v(getClass().getName(), jsonText);
				
				//Adds the string data to one whole JSONObject.
				object = new JSONObject(jsonText);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				try {
					// Call to close the stream if not already closed.
					inputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Disconnects from the server.
				connection.disconnect();
			}
			return object;
		}
		
		@Override
		/**
		 * Method for what to do while the data is downloading, shows a progress dialog and tells the user to please wait.
		 */
		protected void onPreExecute() {
			// The Dialog data to show to user while the data is downloading.
			dialog = ProgressDialog.show(view.getContext(), "","Loading Data. Please wait...", true);
		}
		
		/**
		 * When the data is downloaded create new simpleCompany objects and update view for the user.
		 */
		protected void onPostExecute(JSONObject object) {
			if(object != null) {
				// Select which part of the json data is needed.
				JSONArray results = object.optJSONArray("results");
				if(results != null){
					// Loop through for each individual simple company object
					for(int i = 0; i < results.length(); i++){
						JSONObject obj = results.optJSONObject(i);
						
						SimpleCompany sc = new SimpleCompany(obj);
						list.add(sc);
					}
				}
				// Tells the adapter to reload the view with the new data.
				((JSONListAdapter) adapter).notifyDataSetChanged();
				// Makes all the items in the list clickable.
				addItemClicks();
				//If showing used for if user rotates which destroys the dialog, aysnc continues and tries to close an non-existent dialog.
				if(dialog.isShowing()) {
					dialog.dismiss();
				}
			}
			else {
				Toast.makeText(getActivity(), "No data collected, Check connection..", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * Makes the list items clickable and saves which item was clicked.
	 * The unique place id is saved and communicates a fragment switch.
	 */
	public void addItemClicks(){
		lvDetail.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	String placeID = list.get(position).getPlace_id();
		    	mPrefs = view.getContext().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
		    	mEditor = mPrefs.edit();
		    	mEditor.putString("PlaceID", placeID);
		    	mEditor.commit();
		    	mCallBack.onCompanyListItemSelected();
		    }});
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallBack = (onCompanyListItemSelectedListener) activity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + e.getMessage() );
		}
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}