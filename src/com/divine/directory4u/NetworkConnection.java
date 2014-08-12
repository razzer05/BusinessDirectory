package com.divine.directory4u;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * <p>This class checks if there is a network connection.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class NetworkConnection {
	
	private Context context;
	
	
	public NetworkConnection(Context context){
		this.context = context;
	}
	
	public boolean ConnectionAvailable(){
		ConnectivityManager connection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = connection.getActiveNetworkInfo();
		if (networkinfo != null && networkinfo.isConnected()){
			return true;
		} else {
			return false;
		}
	}

}
