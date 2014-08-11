package com.divine.directory4u;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class CompaniesDetailFragment extends SherlockFragment {
	
	final static String REC_POS = "position";
	int mCurrentRec = -1;

	//will be used to show the full companies details indepth..
	public CompaniesDetailFragment(){
		
	}
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		if (savedInstanceState != null) {
            mCurrentRec = savedInstanceState.getInt(REC_POS);
        }
		
		return inflater.inflate(R.layout.details, container, false);
	}

	/**
	 * During startup, check if there are arguments passed to the fragment.
	 * Apparently onStart is the best place to do this as the layout
	 * has already been created.
	 */
	@Override
	public void onStart() {
		super.onStart();
		Bundle args = getArguments();
		if (args != null) {
            // Set details based on argument passed in
            updateDetailView(args.getInt(REC_POS));
        } else if (mCurrentRec != -1) {
            // Set details based on saved instance state defined during onCreateView
            updateDetailView(mCurrentRec);
        }
	}

	
	public void onAttach(Activity activity){

	}
	
	public void updateDetailView(int position){
		mCurrentRec = position;
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        // Save the current company selection in case we need to recreate the fragment
        outState.putInt(REC_POS, mCurrentRec);
    }
}