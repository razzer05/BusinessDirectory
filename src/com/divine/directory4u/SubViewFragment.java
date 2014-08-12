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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * <p>This class deals with setting up the subcategory's view, provides the events to add or delete subcategory's</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class SubViewFragment extends SherlockFragment implements UpdatedText{
	
	public interface onSubCatSelectedListener{
		public void onSubItemSelected(long id);
	}
	
	public interface subAddressListener{
		public String getSubLocationAddress();
	}
	
	/* Holder variables */
	private onSubCatSelectedListener mCallBack;
	private subAddressListener mLocation;
	private int castPositionToInt;
	private GridView subGridView;
	private CategoryGridAdapter gridadapter;
	private DatabaseHandler dh;
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mEditor;
	private static final String MyPREFERENCES = "MyPrefs";
	private View view;
	
	public static final String ARG_POSITION = null;
	private TextView textview;
	
	public SubViewFragment() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.subcat_view, container, false);
		Bundle bundle = getArguments();
		long longPositionArgument = bundle.getLong(ARG_POSITION);
		castPositionToInt = (int) longPositionArgument;
		subGridView = (GridView)view.findViewById(R.id.grid_view_sub);
		textview = (TextView)view.findViewById(R.id.sub_address_location);
		textview.setText(mLocation.getSubLocationAddress());
		
		dh = new DatabaseHandler(getActivity());
		int[] to = new int[] { R.id.grid_item_text_sub, R.id.grid_item_image_sub };
		String[] from = new String[] {DatabaseHandler.getSubCatName(), DatabaseHandler.getSubCatIcon()};
		
		gridadapter = new CategoryGridAdapter(view.getContext(),
				R.layout.grid_item_sub,
				dh.getSubCatCursor(castPositionToInt),
				from,
				to,
				2);
		
		subGridView.setAdapter(gridadapter);
		
		// onItemsClick save subcatname to sharedpreferences.
		subGridView.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	String subCatName = dh.getSubCatName((int)id);
		    	mPrefs = view.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		    	mEditor = mPrefs.edit();
		    	mEditor.putString("SubCatName", subCatName);
		    	mEditor.commit();
		    	mCallBack.onSubItemSelected(id);
		    	dh.close();
		    }});
		
		// on long click delete subcategory.
		subGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
            	SubCategory sc = new SubCategory();
            	sc.setID((int)id);
            	dh.deleteSubCat(sc);
            	refresh();
            	return true;
            }
        });
		
		Button addSubCat = (Button)view.findViewById(R.id.add_sub_cat);       
	    addSubCat.setOnClickListener(listener);
		
		return view;
	}
	
	/**
	 * Refreshes the subcategories grid. Or sets to the new data.
	 */
	public void refresh() {
		DatabaseHandler dh = new DatabaseHandler(getActivity());
		int[] to = new int[] { R.id.grid_item_text, R.id.grid_item_image };
		String[] from = new String[] {DatabaseHandler.getSubCatName(), DatabaseHandler.getSubCatIcon()}; 
		
		gridadapter = new CategoryGridAdapter(getView().getContext(),
				R.layout.grid_item,
				dh.getSubCatCursor(castPositionToInt),
				from,
				to,
				2);
		
		subGridView.setAdapter(gridadapter);
	}
	
	View.OnClickListener listener = new OnClickListener() {
	    public void onClick(View v) {
	        addSubCat(castPositionToInt);
	    }
	};
	
	/**
	 * Pops up a dialog box to enter a subcategory name and then adds it to the database.
	 * @param parent category parent to the subcategories been displayed.
	 */
	public void addSubCat(final int parent) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	builder.setTitle("Enter Subcategory Name");

    	final EditText input = new EditText(getActivity());

    	builder.setView(input);
    	
    	builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int id) {
    		final String inputText = input.getText().toString();
    		SubCategory c = new SubCategory(0, parent, inputText, "R.drawable.ic_launcher");
    		DatabaseHandler dh = new DatabaseHandler(getActivity());
    		dh.addSubCategory(c);
    		refresh();
    	}
    	});
    	builder.create();
    	builder.show();
    } 
	
	public void onCreate() {
		setRetainInstance(true);
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onPause() {
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
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mLocation = (subAddressListener) activity;
			mCallBack = (onSubCatSelectedListener) activity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + e.getMessage() );
		}
	}

}

