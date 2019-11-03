package com.example.yangtidemo.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.os.Handler;
import android.os.Message;

public class GetTimeClass {
	public static String time;
	static Handler handler;
	static Runnable timeRunnable;

	public static void getTime() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"HH:mm");
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				time = simpleDateFormat.format(new java.util.Date());
				handler.postDelayed(timeRunnable, 500);
			}
		};
		timeRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = handler.obtainMessage();
				handler.sendMessage(msg);
			}
		};
		handler.post(timeRunnable);
	}
}
