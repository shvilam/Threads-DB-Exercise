package com.marktesLabs.months;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends MyActionBarActivity {
	private ListView mList;
	private MonthAdapter mAdapter;
	public ProgressBar mProgressBar;
	private ArrayList<String> mMonths;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 

		mList = (ListView) findViewById(R.id.monthList);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mHandler = new Handler();
		mMonths = new ArrayList<String>();
		loadMonthFromDB();
		mAdapter = new MonthAdapter(this, mMonths);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg3) {
				String monthName = (String) adapter.getItemAtPosition(position);
				Intent intent = new Intent(MainActivity.this,
						MonthDetailsActivity.class);
				intent.putExtra(getString(R.string.month_selected), monthName);
				startActivity(intent);
			}

		});
	}

	private void loadMonthFromDB() {
		final SQLiteDatabase mDb = new DBHelper(this.getApplicationContext())
				.getWritableDatabase();
		AsyncTask<String, Integer, Void> task = new AsyncTask<String, Integer, Void>() {

			@Override
			protected Void doInBackground(String... params) {
				final Cursor c = mDb.rawQuery("SELECT * FROM "+ DBHelper.TABLE_NAME + " ORDER BY name", null);
				
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						mProgressBar.setMax(c.getCount());
					}
				});

				// Country current;
				final int nameIdx = c.getColumnIndex("name");
				// final int colorIdx = c.getColumnIndex("color");
				int looper = 0;
				while (c.moveToNext()) {
					String monathName = c.getString(nameIdx);

					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
					}
					mMonths.add(monathName);
					looper++;

					onProgressUpdate(looper);
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(Integer... progress) {
				mProgressBar.setProgress(progress[0]);
				Log.e("My", Integer.toString(mMonths.size()));
			}

			@Override
			protected void onPostExecute(Void result) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setMessage(R.string.dialog_message)
						.setTitle(R.string.dialog_title)
						.setPositiveButton("Go!",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										dialog.cancel();
									}
								});
				AlertDialog dialog = builder.create();
				dialog.show();

				// mList.setAdapter(mAdapter);
				// mAdapter.notifyDataSetInvalidated();
				mAdapter.notifyDataSetChanged();
				Log.e("My", Integer.toString(mMonths.size()) + "Done");
			}

		};
		task.execute("");

	}
	
}
