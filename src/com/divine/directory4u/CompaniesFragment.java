package com.divine.directory4u;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Empty class that was destined for map views of a company.
 */
public class CompaniesFragment extends SherlockFragment {
	
	public static final String ARG_POSITION = null;
	FrameLayout frameLayout;
	
	public CompaniesFragment(){
	}
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.companies, container, false);
		
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	
	public void onAttach(Activity activity){
		super.onAttach(getActivity());
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}