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

public class CategoryGridAdapter extends SimpleCursorAdapter {
	
	final Context mContext;
	final Cursor mCursor;
	final String[] mColumns;
	
	public CategoryGridAdapter(Context context, int layout, Cursor c,
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
		View GridItem = inflator.inflate(R.layout.grid_item, parent, false);
		return GridItem;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor){
		TextView textview = (TextView) view.findViewById(R.id.grid_item_text);
		ImageView imageview = (ImageView) view.findViewById(R.id.grid_item_image);
		textview.setText(mCursor.getString(2));
		int resId = view.getResources().getIdentifier(mCursor.getString(3), "drawable", mContext.getPackageName());
		Log.d("Int ID", "" + resId + mCursor.getString(3));
		imageview.setImageResource(R.drawable.ic_launcher);
	
	}
	
}
