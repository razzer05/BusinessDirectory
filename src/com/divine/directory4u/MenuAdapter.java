package com.divine.directory4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class MenuAdapter extends ArrayAdapter<String> {
	
	public final Context mContext;

	
	public MenuAdapter(Context mContext){
		super(mContext, R.layout.menu_row, MenuList.options);
		this.mContext = mContext;
	}
	
	
	@Override
	public View getView(int position, View ConvertView, ViewGroup parent){
		LayoutInflater inflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View MenuRow = inflator.inflate(R.layout.menu_row, parent, false);
		TextView tv = (TextView) MenuRow.findViewById(R.id.menu_text);
		ImageView iv = (ImageView) MenuRow.findViewById(R.id.menu_icon);
		tv.setText(MenuList.options[position]);
		iv.setImageResource(MenuList.icons[position]);
		
		return MenuRow;
	}

}
