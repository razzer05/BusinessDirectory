package com.divine.directory4u;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <p>This class adapts how the subcategorys should be displayed corresponding to the xml.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class SubCategoryGridAdapter extends SimpleCursorAdapter {
	
	final Context mContext;
	final Cursor mCursor;
	final String[] mColumns;
	
	public SubCategoryGridAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.mContext = context;
		this.mCursor = c;
		this.mColumns = from;
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent){
		LayoutInflater inflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View GridItem = inflator.inflate(R.layout.grid_item_sub, parent, false);
		return GridItem;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor){
		TextView textview = (TextView) view.findViewById(R.id.grid_item_text_sub);
		ImageView imageview = (ImageView) view.findViewById(R.id.grid_item_image_sub);
		textview.setText(mCursor.getString(3));
		int resId = view.getResources().getIdentifier(mCursor.getString(4), "drawable", mContext.getPackageName());
		Log.d("Int ID", "" + resId + mCursor.getString(4));
		imageview.setImageResource(R.drawable.ic_launcher);
	
	}
	
}
