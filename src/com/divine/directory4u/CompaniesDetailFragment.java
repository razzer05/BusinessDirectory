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
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.divine.jsonData.JSONDetailAdapter;
import com.divine.jsonData.SimpleCompanyDetails;

/**
 * <p>This class downloads and shows all the data on a company.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class CompaniesDetailFragment extends SherlockListFragment {
	
	/**
	 * Variable for accessing the company's unique id.
	 */
	private SharedPreferences mPrefs;
	/**
	 * Static value for where sharedPreferences are stored.
	 */
	public static final String MY_PREFERENCES = "MyPrefs";
	/**
	 * Setting up a view accessor.
	 */
	private View view;
	/**
	 * The google places unique API key for this app.
	 */
	private String apiKey = "AIzaSyDr3YPRA6Xn5lLzPmT2FlcDXmih4OUFRPQ";
	/**
	 * Setting up a list view accessor.
	 */
	private ListView lvDetail;
	/**
	 * Setting up a list adapter accessor.
	 */
	private ListAdapter adapter;
	/**
	 * Setting up an empty array list of SimpleCompanyDetails.
	 */
	private ArrayList<SimpleCompanyDetails> list = new ArrayList<SimpleCompanyDetails>();
	/**
	 * Setting up a progress dialog accessor.
	 */
	private ProgressDialog dialog;
	
	/**
	 * Empty class constructor.
	 */
	public CompaniesDetailFragment() {
	}
	
	/**
	 * Creates the view with the associated view, which view group this fragment belongs too.
	 * @param inflater Instantiates the layout XML file into the corresponding view.
	 * @param container sets which view this fragment is the child of.
	 * @param savedInstanceState all the saved instance data it may need.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Set up the view and add relevant display data.
		view = inflater.inflate(R.layout.companies, container, false);
		// Associates the listview with its corresponding xml id version of list.
		lvDetail = (ListView) view.findViewById(android.R.id.list);
		// Sets the adapter up correctly for the details list.
		adapter = (new JSONDetailAdapter(view.getContext(), list));
		// Sets which adapter listview should use.
		lvDetail.setAdapter(adapter);
		return view;
	}

	@Override
	/**
	 * Starts up the background thread for downloading the relevant company's details.
	 */
	public void onStart() {
		super.onStart();
		// Sets where the shared preferences should access the stored data.
		mPrefs = this.getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
		// Gets the company's unique id out of the sharedPreferences.
		String placeid = mPrefs.getString("PlaceID", "");
		// Declares an accessor to the url resource class.
		URL url = null;
		try {
			// Sets the url to correct address for json data for which to download.
			url = new URL("https://maps.googleapis.com/maps/api/place/details/json?" +
					"placeid=" + placeid +
					"&key=" + apiKey);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		boolean connection = new NetworkConnection(getActivity()).ConnectionAvailable();
		if(connection) {
			// Executes the download method passing in the url.
			new CompaniesDetailFragment.getHTTPList().execute(url);
		} 
		else {
			Toast.makeText(getActivity(), "No Connection", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Class for doing tasks in the background while the view is created with empty data, and will the view with data when complete.
	 */
	protected class getHTTPList extends AsyncTask<URL, Void, JSONObject> {
		@Override
		protected JSONObject doInBackground(URL... params) {
			JSONObject object = null;
			HttpURLConnection connection = null;
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
		 * When the data is downloaded create new simpleCompanyDetails object and update view for the user.
		 */
		protected void onPostExecute(JSONObject object) {
			if(object!=null) {
				// Sets which part of the JSONObject is relevant for the company's details.
				JSONObject obj = object.optJSONObject("result");
				// Passes the relevant data to the simpleCompanyDetails class to create the object.
				SimpleCompanyDetails sc = new SimpleCompanyDetails(obj);
				// Adds the company's details to the list view.
				list.add(sc);
				// Tells the adapter that data has changed and needs to be reloaded.
				((JSONDetailAdapter) adapter).notifyDataSetChanged();
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
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}