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
/**
 * <p>This class displays the categories, sets up the events to allow for addition and deletion of categories.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class MainView extends SherlockFragment 
	implements OnClickListener, UpdatedText {
	
	// Static variable for sharedPrefernces data storage.
	private static final String MY_PREFERENCES = "MyPrefs";
	/* Holder variables */
	private onCatSelectedListener mCallBack;
	private onLocationListener mLocation;
	private CategoryGridAdapter gridadapter;
	private GridView mainGridView;
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mEditor;
	private View view;
	private TextView textview;
	
	public interface onCatSelectedListener{
		public void onItemSelected(long id);
	}
	
	public interface onLocationListener{
		public String getLocationAddress();
	}
	
	public MainView(){
		
	}
	
	
	

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
		
		//on item click save category name to sharedPreferences, send category id to next fragment and call for fragment swap.
		mainGridView.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	DatabaseHandler dh = new DatabaseHandler(getActivity());
		    	String catName = dh.getCatName((int)id);
		    	mPrefs = view.getContext().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
		    	mEditor = mPrefs.edit();
		    	mEditor.putString("CatName", catName);
		    	mEditor.commit();
		    	mCallBack.onItemSelected(id);
		    	dh.close();
		    }});
		
		//on long click delete call for deletion of that category and children.
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
	
	/**
	 * "Refreshes" the categories when a new one is added or deleted. Or sets to the new data.
	 */
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
    		DatabaseHandler dh = new DatabaseHandler(getActivity());
    		dh.addCategory(c);
    		refresh();
    	}
   });

    	builder.create();
    	builder.show();
    } 
    
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

