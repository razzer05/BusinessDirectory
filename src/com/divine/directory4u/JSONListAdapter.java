package com.divine.directory4u;

import java.util.ArrayList;

import com.divine.jsonData.SimpleCompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
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
	
	private ImageView detail(View v, int resId, int icon) {
        ImageView iv = (ImageView) v.findViewById(resId);
        iv.setImageResource(icon); // 
        
        return iv;
    }
	
	private class MyViewHolder {
		TextView companyName;
		TextView vicinity;
	}

}
