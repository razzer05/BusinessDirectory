package com.divine.directory4u;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * <p>This class sets the the title of the application 
 *  and provides the functionality to switch between the contents fragment and the about page within the sliding fragment.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class BaseActivity extends SlidingFragmentActivity {
	/**
	 * Integer relating to the title of the associated activity.
	 */
	private int mTitleRes;
	/**
	 * A list fragment for later use with displaying menu options.
	 */
	protected ListFragment mFragment;
	
	/**
	 * Creates and sets the title of this activity.
	 * @param titleRes title of the activity.
	 */
	public BaseActivity(int titleRes){
		//sets the title variable to correlating to the passed in integer
		mTitleRes = titleRes;
	}

	@Override
	/**
	 * Overrides the super classes onCreate method and sets all the relevant data for this activity.
	 * Sets the title and the sliding fragment menu up.
	 * 
	 * @param savedInstanceState a collection of strings with data needed for this class, if any.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// sets the title.
		setTitle(mTitleRes);
		
		// sets which view to use for the menu.
		setBehindContentView(R.layout.menu);
		
		// Declares which fragment transaction is being used for this activity.
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		
		// sets the data source for the menu.
		mFragment = new MenuList().setBaseActivity(this);
		
		// replaces all the empty data in the menu with data.
		ft.replace(R.id.menu_frame, mFragment);
		
		// finalises the data exchange.
		ft.commit();
		
		// sets the sliding menu to associate with this activity.
		SlidingMenu slidemenu = getSlidingMenu();
		// sets a shadow width for styling.
		slidemenu.setShadowWidth(15);
		// sets an item to be used for the shadow.
		slidemenu.setShadowDrawable(R.drawable.shadow);
		// sets the width of the menu.
		slidemenu.setBehindWidth(500);
		// sets a level of fading.
		slidemenu.setFadeDegree(0.35f);
		// disables the sliding ability for the menu.
		slidemenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		// sets the home to be displayed as an up affordance, so it is clickable.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// sets the icon related to the home option.
		getSupportActionBar().setIcon(R.drawable.ic_launcher);
	}
	
	@Override
	/**
	 * Sets what should happen on each menu option selected.
	 * @param item which menu option was selected.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			toggle();
			return true;
		}
		return onOptionsItemSelected(item);
	}
	
	@Override
	/**
	 * Inflates the options menu with items if present.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	/**
	 * Provides functionality to switch between the fragments
	 * @param fragment
	 */
	public void switchFragment(Fragment fragment) {
		Log.d(getClass().getName(), "switch " + fragment.toString());
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment).commit();
	}
	
	@Override
	/**
	 * Calls to pause the super class.
	 */
	public void onPause() {
		super.onPause();
		Log.d("BaseActivity", "onPause");
	}
	
	@Override
	/**
	 * Calls to the super class from the current activity.
	 */
	public void onResume() {
		super.onResume();
		Log.d(getClass().getName(), "onResume");
	}
}
