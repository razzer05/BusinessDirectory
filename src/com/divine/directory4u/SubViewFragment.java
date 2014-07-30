package com.divine.directory4u;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;


public class SubViewFragment extends SherlockFragment implements UpdatedText{
	
	onSubCatSelectedListener mCallBack;
	subAddressListener mLocation;
	
	public interface onSubCatSelectedListener{
		public void onSubItemSelected(); //put in long ID when its pulling from server.
	}
	
	public interface subAddressListener{
		public String getSubLocationAddress();
	}
		

		public static final String ARG_POSITION = null;
		
		public SubViewFragment(){
			
		}
		
		TextView textview;

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
			View view = inflater.inflate(R.layout.subcat_view, container, false);
			Bundle bundle = getArguments();
			long longPositionArguement = bundle.getLong(ARG_POSITION);
			int castPositionToInt = (int) longPositionArguement;

			
			GridView subGridView = (GridView)view.findViewById(R.id.grid_view_sub);
			textview = (TextView)view.findViewById(R.id.sub_address_location);
			textview.setText(mLocation.getSubLocationAddress());
			
			DatabaseHandler dh = new DatabaseHandler(getActivity());
			int[] to = new int[] { R.id.grid_item_text_sub, R.id.grid_item_image_sub };
			String[] from = new String[] {DatabaseHandler.getSubCatName(), DatabaseHandler.getSubCatIcon()};
			CategoryGridAdapter gridadapter = new CategoryGridAdapter(view.getContext(),
					R.layout.grid_item_sub,
					dh.getSubCatCursor(castPositionToInt),
					from,
					to,
					2);
			subGridView.setAdapter(gridadapter);
			subGridView.setOnItemClickListener(new OnItemClickListener() {
			    @Override
			    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			    	mCallBack.onSubItemSelected();
			    }});
			return view;
		}
		
		public void onCreate(){
			setRetainInstance(true);
		}

		@Override
		public void onStart() {
			super.onStart();
		}
		
		@Override
		public void onPause(){
			super.onPause();
		}
		
		
		@Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	    }

		@Override
		public void updateText(String s) {
			textview.setText(s);
		}
		
		public void onAttach(Activity activity){
			super.onAttach(activity);
			try {
				mCallBack = (onSubCatSelectedListener) activity;
				mLocation = (subAddressListener) activity;
			}
			catch (ClassCastException e) {
				throw new ClassCastException(activity.toString() + e.getMessage());
			}
		}

}

