package com.divine.directory4u;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
//import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class MainView extends SherlockFragment 
	implements OnClickListener, UpdatedText {
	
	private static final String MyPREFERENCES = "MyPrefs";
	onCatSelectedListener mCallBack;
	onLocationListener mLocation;
	CategoryGridAdapter gridadapter;
	GridView mainGridView;
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	View view;
	
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
		
		view = inflater.inflate(R.layout.activity_main, container, false);
		mainGridView = (GridView)view.findViewById(R.id.grid_view);
		textview = (TextView)view.findViewById(R.id.address_location);
		textview.setText(mLocation.getLocationAddress());
		
		DatabaseHandler dh = new DatabaseHandler(getActivity());
		int[] to = new int[] { R.id.grid_item_text, R.id.grid_item_image };
		String[] from = new String[] {DatabaseHandler.getCatName(), DatabaseHandler.getCatIcon()}; 
		
		gridadapter = new CategoryGridAdapter(view.getContext(),
				R.layout.grid_item,
				dh.getCatCursor(),
				from,
				to,
				2);
		
		mainGridView.setAdapter(gridadapter);
		
		mainGridView.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	DatabaseHandler dh = new DatabaseHandler(getActivity());
		    	String catName = dh.getCatName((int)id);
		    	mPrefs = view.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		    	mEditor = mPrefs.edit();
		    	mEditor.putString("CatName", catName);
		    	mEditor.commit();
		    	mCallBack.onItemSelected(id);
		    	dh.close();
		    }});
		
		mainGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
            	Category c = new Category();
            	c.setID((int)id);
            	DatabaseHandler dh = new DatabaseHandler(getActivity());
            	dh.deleteCat(c);
            	refresh();
            	return true;
            }
        });
		
		Button addCat = (Button)view.findViewById(R.id.add_cat);       
	    addCat.setOnClickListener(this);
		
		return view;
	}
	
	public void refresh(){
		DatabaseHandler dh = new DatabaseHandler(getActivity());
		int[] to = new int[] { R.id.grid_item_text, R.id.grid_item_image };
		String[] from = new String[] {DatabaseHandler.getCatName(), DatabaseHandler.getCatIcon()}; 
		
		gridadapter = new CategoryGridAdapter(getView().getContext(),
				R.layout.grid_item,
				dh.getCatCursor(),
				from,
				to,
				2);
		
		mainGridView.setAdapter(gridadapter);
	}
	
    public void addCat(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	builder.setTitle("Enter Category Name");

    	final EditText input = new EditText(getActivity());

    	builder.setView(input);
    	
    	builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int id) {
    		final String inputText = input.getText().toString();
    		Category c = new Category();
    		c.setCatName(inputText);
    		c.setCatIcon("R.drawable.ic_launcher");
    		//Toast.makeText(getActivity(), inputText, Toast.LENGTH_SHORT).show();
    		DatabaseHandler dh = new DatabaseHandler(getActivity());
    		dh.addCategory(c);
    		refresh();
    	}
   });

    	builder.create();
    	builder.show();
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
		if(arg0.equals(arg0.findViewById(R.id.add_cat))){
			addCat();
		}
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		try {
			mCallBack = (onCatSelectedListener) activity;
			mLocation = (onLocationListener) activity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + e.getMessage());
		}
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


	@Override
	public void updateText(String s) {
		textview.setText(s);
	}
}

