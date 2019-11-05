package com.example.bdemo.time;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.os.Handler;
import android.os.Message;

/**
 * 获取时间
 * 
 * @author A
 * 
 */
public class GetTimeClass {
	public static Handler handler;
	public static Runnable timeRunnable;
	public static String time = "";

	public static void startthread() {
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy年MM月dd日 HH:mm:ss");
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
