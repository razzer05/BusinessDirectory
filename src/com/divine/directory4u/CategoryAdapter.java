package com.divine.directory4u;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <p>This sets up how the category should be displayed and associates each data value with its xml value.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class CategoryAdapter extends ArrayAdapter<String> {

	/**
	 * interface for accessing global information
	 */
	final Context context;
	
	public CategoryAdapter(Context context) {
		super(context, R.layout.grid_item);
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return 0;
	}
	

	@SuppressLint("ViewHolder") @Override
	/**
	 * Sets up the view with each individual category item related to which category item.
	 */
	public View getView(int position, View ConvertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View MenuRow = inflator.inflate(R.layout.grid_item, parent, false);
		TextView tv = (TextView) MenuRow.findViewById(R.id.grid_item_text);
		ImageView iv = (ImageView) MenuRow.findViewById(R.id.grid_item_image);
		tv.setText(MenuList.options[position]);
		iv.setImageResource(MenuList.icons[position]);
		
		return MenuRow;
	}

}
