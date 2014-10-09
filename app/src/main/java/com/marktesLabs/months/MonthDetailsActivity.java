package com.marktesLabs.months;


import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MonthDetailsActivity extends MyActionBarActivity{

    public static final String TAG = "MAIN";
	private ImageView imgMonth;
	//private Handler handler;
	private ProgressBar progressBar1;
    TextView txtView;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.activity_month_details);

		imgMonth = (ImageView)findViewById(R.id.monthImage);
		progressBar1 =(ProgressBar)findViewById(R.id.progressBar);
        txtView = (TextView)findViewById(R.id.monthName);

		imgMonth.setVisibility(View.INVISIBLE);


        try
        {

            String month = getIntent().getExtras().getString(getString(R.string.month_selected));
            txtView.setText(month);
            loadMonthInfo(month);
            int drawableResourceId = this.getResources().getIdentifier("ic_"+month.toLowerCase(new Locale("en-US")), "drawable", this.getPackageName());
            imgMonth.setImageResource(drawableResourceId);


        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Exception in Parsing month", Toast.LENGTH_LONG).show();
        }

		Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(5000);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {

                    MonthDetailsActivity.this.imgMonth.post(new Runnable() {

                        @Override
                        public void run() {
                            imgMonth.setVisibility(View.VISIBLE);
                            progressBar1.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            }
        };
        welcomeThread.start();


		
	}
    private void loadMonthInfo(final String month) {
        final SQLiteDatabase mDb = new DBHelper(this.getApplicationContext()).getWritableDatabase();
        AsyncTask<String, Integer, Void> task = new AsyncTask<String, Integer, Void>() {

            @Override
            protected Void doInBackground(String... params) {
                final Cursor c = mDb.rawQuery("SELECT * FROM "+ DBHelper.TABLE_NAME + " where name='"+ month+"' ORDER BY name", null);


                final int nameIdx = c.getColumnIndex("name");
                final int daysIdx = c.getColumnIndex("days");

                while (c.moveToNext()) {
                    final int days = c.getInt(daysIdx);
                    Log.d(TAG,"days"+days+"  month" +month);
                    MonthDetailsActivity.this.txtView.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG,"Run days"+days+"  month" +month);
                            if (days > 27) {
                                txtView.setText(month + "  days in month" + days);
                            } else {
                                txtView.setText(month);
                            }
                        }
                    });

                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... progress) {
            }

            @Override
            protected void onPostExecute(Void result) {

            }

        };
        task.execute("");

    }

	
}
