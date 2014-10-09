package com.marktesLabs.months;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MonthAdapter extends BaseAdapter {
	 private final Context mContext;
	 private ArrayList<String> mMonths;
	
	 
	 public MonthAdapter(Context context,ArrayList<String> months)
	 {	
		 super();
		 this.mContext = context;
		 this.mMonths = months;
		
		 
	 }
	 

	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		 View view = null;
		 if (convertView == null) {
			 LayoutInflater inflater = LayoutInflater.from(mContext);
			 view = inflater.inflate(R.layout.month_item, null);
			 final ViewHolder viewHolder = new ViewHolder();
			 viewHolder.text = (TextView) view.findViewById(R.id.monthName);
			 view.setTag(viewHolder);
		 } 
		 else 
		 {
			 view = convertView;
		 }
		 ViewHolder holder = (ViewHolder) view.getTag();
		 holder.text.setText(mMonths.get(position));
		 return view;
	 }
	 static class ViewHolder {
		 protected TextView text;
	 }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMonths.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mMonths.get(position);
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
}
