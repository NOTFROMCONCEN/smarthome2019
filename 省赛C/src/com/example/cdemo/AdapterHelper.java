package com.example.cdemo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterHelper extends ArrayAdapter<String> {
	String[] mString;
	Context mContext;

	public AdapterHelper(Context context, String[] mStringArray) {
		super(context, android.R.layout.simple_spinner_item, mStringArray);
		// TODO Auto-generated constructor stub
		mString = mStringArray;
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					android.R.layout.simple_spinner_dropdown_item, parent,
					false); 
			
		}
		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		tv.setText(mString[position]);
		tv.setTextColor(Color.WHITE);
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					android.R.layout.simple_spinner_item, parent, false);
		}
		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		tv.setText(mString[position]);
		tv.setTextColor(Color.BLACK);
		return convertView;
	}
}
