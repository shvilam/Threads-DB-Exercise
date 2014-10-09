package com.marktesLabs.months;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String RIGHT_MONTH_DB = "RIGHT_MONTH_DB";
	public static final String TABLE_NAME  = "MyMonth";
	
	public static final int VERSION = 1;
	
	private Context mContext;
	
	public DBHelper(Context context) {
		super(context, RIGHT_MONTH_DB, null, VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createStatement = "CREATE TABLE "+ TABLE_NAME +" (name TEXT ,days INTEGER)";
		db.execSQL(createStatement);
		String[] months = mContext.getString(R.string.months_strings).split(","); 
		for (int i = 0 ; i < months.length; i++ ) {
			db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('"+months[i]+"',-1)");
		}
		
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
