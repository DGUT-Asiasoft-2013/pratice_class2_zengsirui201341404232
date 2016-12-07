package com.example.helloworld;

import Fragment.MainTabbarFragment;
import Fragment.MainTabbarFragment.OnTabSelectedListener;
import Fragment.pages.FeedListFragment;
import Fragment.pages.MyProfileFragment;
import Fragment.pages.NoteListFragment;
import Fragment.pages.SearchPageFragment;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

//É»»›
public class HelloWorldActivity extends Activity {

	FeedListFragment contentFeedList = new FeedListFragment();
	NoteListFragment contentNoteList = new NoteListFragment();
	SearchPageFragment contentSearchPage = new SearchPageFragment();
	MyProfileFragment contentMyProFile = new MyProfileFragment();

	MainTabbarFragment tabbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helloworld);

		tabbar = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.frag_tabbar);
		tabbar.setOnTabSelectedListener(new OnTabSelectedListener() {

			@Override
			public void onTabSelected(int index) {
				changeContentFragment(index);
			}
		});

	}


	@Override
	protected void onResume() {
		super.onResume();

		tabbar.setSelectedItem(0);
}
	
	void changeContentFragment(int index) {
		Fragment newFrag = null;

		switch (index) {
		case 0:
			newFrag = contentFeedList;
			break;
		case 1:
			newFrag = contentNoteList;
			break;
		case 2:
			newFrag = contentSearchPage;
			break;
		case 3:
			newFrag = contentMyProFile;
			break;
		default:
			break;
		}
		if (newFrag == null)
			return;

		getFragmentManager().beginTransaction().replace(R.id.content, newFrag).commit();
	}

}
