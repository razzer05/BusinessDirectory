package com.divine.jsonData;

import java.util.ArrayList;

import com.divine.directory4u.R;
import com.divine.directory4u.R.id;
import com.divine.directory4u.R.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * <p>This class sets up how the companies list should be displayed on the corresponding xml layouts.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class JSONListAdapter extends BaseAdapter {

	ArrayList<SimpleCompany> myList = new ArrayList<SimpleCompany>();
	LayoutInflater inflater;
	Context context;
	
	public JSONListAdapter(Context context, ArrayList<SimpleCompany> myList){
		this.myList = myList;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}
	
	@Override
	public int getCount() {
		return myList.size();
	}

	@Override
	public Object getItem(int position) {
		return myList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyViewHolder mViewHolder;
        
        if(convertView == null) {
                convertView = inflater.inflate(R.layout.company_list_item, null);
                mViewHolder = new MyViewHolder();
                convertView.setTag(mViewHolder);
        } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
        }
        
        mViewHolder.companyName = detail(convertView, R.id.company_name_list, myList.get(position).getName());
        mViewHolder.vicinity = detail(convertView, R.id.company_formatted_Address_list, myList.get(position).getFormattedAddress());
        return convertView;
	}
	
	private TextView detail(View v, int resId, String text) {
        TextView tv = (TextView) v.findViewById(resId);
        tv.setText(text);
        return tv;
    }
	
	private class MyViewHolder {
		@SuppressWarnings("unused")
		TextView companyName;
		@SuppressWarnings("unused")
		TextView vicinity;
	}

}
