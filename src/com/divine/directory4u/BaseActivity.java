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

public class BaseActivity extends SlidingFragmentActivity {
	
	private int mTitleRes;
	protected ListFragment mFragment;
	private Fragment savedFragment;
	
	public BaseActivity(int titleRes){
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(mTitleRes);
		
		setBehindContentView(R.layout.menu);
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		mFragment = new MenuList().setBaseActivity(this);
		ft.replace(R.id.menu_frame, mFragment);
		ft.commit();
		
		SlidingMenu slidemenu = getSlidingMenu();
		slidemenu.setShadowWidth(15);
		slidemenu.setShadowDrawable(R.drawable.shadow);
		slidemenu.setBehindWidth(500);
		slidemenu.setFadeDegree(0.35f);
		slidemenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);
	}

	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case android.R.id.home:
			toggle();
			return true;
		}
		return onOptionsItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	int id;
	
	public void switchFragment(Fragment fragment) {
		Log.d(getClass().getName(), "switch " + fragment.toString());
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment).commit();
		id = fragment.getId();
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("BaseActivity", "onPause");
//		if (id != 0)
//			savedFragment = getSupportFragmentManager().findFragmentById(id);
//		switchFragment(new MainView());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(getClass().getName(), "onResume");
		//if (savedFragment != null)
		//	switchFragment(savedFragment);
	}
}
