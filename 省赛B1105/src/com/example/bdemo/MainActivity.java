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
 * ������
 */
public class MainActivity extends Activity {
	private ProgressBar pg_1;
	private TextView tv_number_1;
	private int number = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ��
		pg_1 = (ProgressBar) findViewById(R.id.progressBar1);
		tv_number_1 = (TextView) findViewById(R.id.tv_loading_number);
		// �������
		handler.post(timeRunnable);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * ���½�����
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pg_1.setProgress(msg.what);

			// �ж�����������
			switch (msg.what) {
			case 1:
				tv_number_1.setText("���ڼ��ش�������......");
				break;
			case 20:
				tv_number_1.setText("�������ü������......");
				break;
			case 30:
				tv_number_1.setText("���ڼ��ؽ�������......");
				break;
			case 50:
				tv_number_1.setText("�������ü������......");
				break;
			case 60:
				tv_number_1.setText("���ڳ�ʼ������......");
				break;
			case 80:
				tv_number_1.setText("�����ʼ�����......");
				break;
			case 99:
				tv_number_1.setText("����ϵͳ��......");
				break;

			default:
				break;
			}
			/**
			 * ���ȼ�����ɺ���ת
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
