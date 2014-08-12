package com.divine.directory4u;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

/**
 * <p>This class holds all the menu options in a list.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class MenuList extends SherlockListFragment {

	private static final int HOME = 0;
	private static final int ABOUT = 1;
	

	public final static String[] options = new String[] {"Home", "About" };
	public final static int[] icons = new int[] { R.drawable.ic_launcher,
			R.drawable.ic_launcher};
	private BaseActivity baseActivity;

	public MenuList() {
	}

	public View onCreateView(LayoutInflater inflator, ViewGroup container,
			Bundle savedInstanceState) {

		MenuAdapter adapter = new MenuAdapter(getActivity());

		/** Setting the list adapter for the ListFragment **/
		setListAdapter(adapter);

		return inflator.inflate(R.layout.menu, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	/**
	 * On list item click, change to that activity.
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
		case HOME:
			MainView home = new MainView();
			baseActivity.switchFragment(home);
			baseActivity.toggle();
			break;
		case ABOUT:
			AboutView about = new AboutView();
			baseActivity.switchFragment(about);
			baseActivity.toggle();
			break;
		default:
			break;
		}
	}

	public BaseActivity getBaseActivity() {
		return baseActivity;
	}

	public MenuList setBaseActivity(BaseActivity baseActivity) {
		this.baseActivity = baseActivity;
		return this;
	}

}
