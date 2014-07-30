package com.divine.directory4u;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class MenuList extends SherlockListFragment {

	private static final int HOME = 0;
	private static final int FAVOURITES = 1;
	private static final int HISTORY = 2;
	private static final int LICENCE = 3;
	

	public final static String[] options = new String[] {"Home", "Favourites",
			"History", "Licences" };
	public final static int[] icons = new int[] { R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,  R.drawable.ic_launcher };
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
	public void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
		case HOME:
			MainView home = new MainView();
			baseActivity.switchFragment(home);
			baseActivity.toggle();
			break;
		case FAVOURITES:
			FavouritesView favourites = new FavouritesView();
			baseActivity.switchFragment(favourites);
			baseActivity.toggle();
			break;
		case HISTORY:
			HistoryView history = new HistoryView();
			baseActivity.switchFragment(history);
			baseActivity.toggle();
			break;
		case LICENCE:
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
