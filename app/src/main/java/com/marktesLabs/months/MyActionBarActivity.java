package com.marktesLabs.months;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MyActionBarActivity extends ActionBarActivity {
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
			case R.id.menu_main:
				intent = new Intent(MyActionBarActivity.this,MainActivity.class);
				startActivity(intent);	
				return true;
			case R.id.menu_edit:
				intent = new Intent(MyActionBarActivity.this,UpdateActivity.class);
				intent.putExtra(getString(R.string.extra_action), getString(R.string.add));
				startActivity(intent);
				return true;	
			}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
