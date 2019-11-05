package com.example.bdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

/*
 * 进度条
 */
public class MainActivity extends Activity {
	private ProgressBar pg_1;
	private TextView tv_number_1;
	private int number = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 绑定
		pg_1 = (ProgressBar) findViewById(R.id.progressBar1);
		tv_number_1 = (TextView) findViewById(R.id.tv_loading_number);
		// 激活进程
		handler.post(timeRunnable);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * 更新进度条
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pg_1.setProgress(msg.what);

			// 判读进度条进度
			switch (msg.what) {
			case 1:
				tv_number_1.setText("正在加载串口配置......");
				break;
			case 20:
				tv_number_1.setText("串口配置加载完成......");
				break;
			case 30:
				tv_number_1.setText("正在加载界面配置......");
				break;
			case 50:
				tv_number_1.setText("界面配置加载完成......");
				break;
			case 60:
				tv_number_1.setText("正在初始化界面......");
				break;
			case 80:
				tv_number_1.setText("界面初始化完成......");
				break;
			case 99:
				tv_number_1.setText("进入系统中......");
				break;

			default:
				break;
			}
			/**
			 * 进度加载完成后跳转
			 */
			if (msg.what == 100) {
				startActivity(new Intent(getApplicationContext(),
						LoginActivity.class));
				finish();
			}
			handler.postDelayed(timeRunnable, 50);
		}
	};
	Runnable timeRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			number++;
			Message msg = handler.obtainMessage();
			msg.what = number;
			if (msg.what > 100) {
				handler.removeCallbacks(timeRunnable);
			} else {
				handler.sendMessage(msg);
			}
		}
	};

}
