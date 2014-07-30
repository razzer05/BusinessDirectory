package com.divine.directory4u;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
