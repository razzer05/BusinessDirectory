package com.divine.directory4u;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class MainView extends SherlockFragment implements OnClickListener, UpdatedText {
	
	onCatSelectedListener mCallBack;
	onLocationListener mLocation;
	
	public interface onCatSelectedListener{
		public void onItemSelected(long id);
	}
	
	public interface onLocationListener{
		public String getLocationAddress();
	}
	
	public MainView(){
		
	}
	
	
	TextView textview;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.activity_main, container, false);
		GridView mainGridView = (GridView)view.findViewById(R.id.grid_view);
		textview = (TextView)view.findViewById(R.id.address_location);
		textview.setText(mLocation.getLocationAddress());
		
		DatabaseHandler dh = new DatabaseHandler(getActivity());
		int[] to = new int[] { R.id.grid_item_text, R.id.grid_item_image };
		String[] from = new String[] {DatabaseHandler.getCatName(), DatabaseHandler.getCatIcon()}; 
		
		CategoryGridAdapter gridadapter = new CategoryGridAdapter(view.getContext(),
				R.layout.grid_item,
				dh.getCatCursor(),
				from,
				to,
				2);
		
		mainGridView.setAdapter(gridadapter);
		
		mainGridView.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	mCallBack.onItemSelected(id);
		    }});
		
		return view;
	}
	
	//@Override
	public void onCreate(){
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		try {
			mCallBack = (onCatSelectedListener) activity;
			mLocation = (onLocationListener) activity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString());
		}
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


	@Override
	public void updateText(String s) {
		textview.setText(s);
	}
}

