package com.divine.directory4u;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.divine.directory4u.MainView.onLocationListener;
import com.divine.directory4u.SubViewFragment.subAddressListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * <p>Deals with switching frames in content_frame and data sending between each of them.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class MainActivity extends BaseActivity implements
		MainView.onCatSelectedListener, LocationListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, 
		onLocationListener, subAddressListener, SubViewFragment.onSubCatSelectedListener,
		CompaniesListFragment.onCompanyListItemSelectedListener{

	private Fragment mContent;
	// Handle to SharedPreferences for this application
	private SharedPreferences mPrefs;
	// Handle to a SharedPreferences editor
	private SharedPreferences.Editor mEditor;

	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	public static final String APPTAG = "LocationSample";
	private static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int UPDATE_INTERVAL_IN_SECONDS = 30;
	private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND
			* UPDATE_INTERVAL_IN_SECONDS;
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND
			* FASTEST_INTERVAL_IN_SECONDS;
	
	private LocationClient mLocationClient;
	private static final String MY_PREFERENCES = "MyPrefs";
	private LocationRequest mLocationRequest;

	public String mAddress;
	
	/**
	 * Used for storing users latitude.
	 */
	private double latitude;
	/**
	 * Used for storing users longitude.
	 */
	private double longitude;
	
	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
			Log.v("Saved Instance", "Not Null");
			Log.v("Saved Instance", "" + mContent.toString());
		}
		

		if (savedInstanceState == null) {
			MainView mainview = new MainView();
			mContent = mainview;
			Log.v("Saved Instance", "Null");
			Log.v("Saved Instance", "" + mContent.toString());
		}

		setContentView(R.layout.content_frame);
		
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent)
				.commit();

		setBehindContentView(R.layout.menu_frame);
		
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuList().setBaseActivity(this))
				.commit();

		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		
		setSlidingActionBarEnabled(true);

		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		mLocationClient = new LocationClient(this, this, this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mLocationClient.connect();
	}

	@Override
	public void onStop() {
		if (mLocationClient.isConnected()) {
			stopPeriodicUpdates();
		}
		mLocationClient.disconnect();
		super.onStop();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	private void stopPeriodicUpdates() {
		mLocationClient.removeLocationUpdates(this);
	}

	private void startPeriodicUpdates() {
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
	}

	@Override
	public void onItemSelected(long id) {
		SubViewFragment subview = new SubViewFragment();
		Bundle args = new Bundle();
		args.putLong(SubViewFragment.ARG_POSITION, id);
		subview.setArguments(args);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content_frame, subview);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void onSubItemSelected(long id) {
		CompaniesListFragment companiesListfragment = new CompaniesListFragment();
		Bundle args = new Bundle();
		args.putLong(CompaniesListFragment.ARG_POSITION, id);
		companiesListfragment.setArguments(args);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content_frame, companiesListfragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	/**
	 * Switches from the company list fragment in the content frame to the companies details fragment
	 */
	public void onCompanyListItemSelected() {
		CompaniesDetailFragment cdf = new CompaniesDetailFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content_frame, cdf);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
			} catch (IntentSender.SendIntentException e) {
				e.printStackTrace();
			}
		} else {
			showErrorDialog(connectionResult.getErrorCode());
		}

	}

	private void showErrorDialog(int errorCode) {

		Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
				this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

		if (errorDialog != null) {
			ErrorDialogFragment errorFragment = new ErrorDialogFragment();
			errorFragment.setDialog(errorDialog);

			errorFragment.show(getSupportFragmentManager(), APPTAG);
		}
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		startPeriodicUpdates();
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	public static class ErrorDialogFragment extends DialogFragment {
		private Dialog mDialog;

		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}

	}

	private boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (ConnectionResult.SUCCESS == resultCode) {
			return true;
		} else {
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

			if (errorDialog != null) {
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(errorDialog);
				errorFragment.show(getSupportFragmentManager(),
						"Location Updates");
			}
			return false;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		mLocationClient.removeLocationUpdates(this);
		getAddress();
	}

	public void getLocation() {
		if (servicesConnected()) {
			mLocationClient.getLastLocation();
		}
	}

	public void getAddress() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& !Geocoder.isPresent()) {
			Log.d("Geocode Unavailable", "");
		}

		if (servicesConnected()) {
			Location currentLocation = mLocationClient.getLastLocation();
			(new MainActivity.GetAddressTask(this)).execute(currentLocation);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState){
		mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		super.onRestoreInstanceState(savedInstanceState);
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) { 
	}
	 

	public void switchContent(Fragment fragment){
		Log.d(getClass().getName(), "switch " + fragment.toString());
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
				fragment).commit();
		getSlidingMenu().showContent();
	 }
	
	Location location;
	
	protected class GetAddressTask extends AsyncTask<Location, Void, String> {

		Context localContext;

		public GetAddressTask(Context context) {
			super();
			localContext = context;
		}

		@Override
		protected String doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(localContext, Locale.getDefault());
			 location = params[0];
			List<Address> addresses = null;
			
			try {
				addresses = geocoder.getFromLocation(location.getLatitude(),
						location.getLongitude(), 1);
				
			} catch (IOException exception1) {
				exception1.printStackTrace();

			} catch (IllegalArgumentException exception2) {
				exception2.printStackTrace();
			}

			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);
				String addressText = getString(
						R.string.address_output_string,

						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "", address.getCountryCode()
				);

				return addressText;

			} else {
				return getString(R.string.no_address_found);
			}
		}

		protected void onPostExecute(String address) {
			mAddress = address;
			((UpdatedText)mContent).updateText(mAddress);
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			if(latitude!=0&&longitude!=0){
			saveLocation(latitude, longitude);
			}
			
		}
	}
	
	/**
	 * Saves the users location latitude and longitude to the sharedPreferences as strings.
	 * @param lat latitude of the user.
	 * @param lng longitude of the user.
	 */
	public void saveLocation(double lat, double lng){
		String sLat = String.valueOf(lat);
		String sLng = String.valueOf(lng);
		mPrefs = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
		mEditor.putString("lat", sLat);
		mEditor.putString("lng", sLng);
		mEditor.commit();
	}
	
	@Override
	public String getLocationAddress() {
		if (mAddress == null) {
			return "Resolving location";
		}
		return mAddress;
	}
	
	@Override
	public String getSubLocationAddress(){
		if (mAddress == null) {
			return "Resolving location";
		}
		return mAddress;
	}
}
