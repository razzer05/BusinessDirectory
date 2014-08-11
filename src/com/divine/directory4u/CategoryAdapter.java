package com.divine.directory4u;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends ArrayAdapter<String> {

	
	final Context context;
	int count;
	
	public CategoryAdapter(Context context) {
		super(context, R.layout.grid_item);
		this.context = context;
		count++;
	}
	
	@Override
	public int getCount() {
		return count;
	}
	
	@Override
	public View getView(int position, View ConvertView, ViewGroup parent){
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
