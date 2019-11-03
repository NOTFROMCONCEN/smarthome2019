package com.example.yangtidemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.yangtidemo.sql.MyDataBaseHelper;
import com.example.yangtidemo.time.GetTimeClass;
import com.example.yangtidemo.toast.DiyToast;

public class MainActivity extends Activity {

	private SeekBar sk_1;
	private EditText et_port;
	private EditText et_ip;
	private Button btn_login;
	private TextView tv_number_login;
	MyDataBaseHelper dbHelper;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GetTimeClass.getTime();
		sk_1 = (SeekBar) findViewById(R.id.seekBar1);
		et_ip = (EditText) findViewById(R.id.et_ip);
		et_port = (EditText) findViewById(R.id.et_port);
		tv_number_login = (TextView) findViewById(R.id.tv_login_number);
		btn_login = (Button) findViewById(R.id.btn_login);
		dbHelper = new MyDataBaseHelper(getApplicationContext(), "info.db",
				null, 2);
		db = dbHelper.getWritableDatabase();
		get_user_login_number();
		sk_1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (sk_1.getProgress() > 66 && sk_1.getProgress() < 76) {

				} else {
					DiyToast.showToast(getApplicationContext(), "请完成滑动验证");
					sk_1.setProgress(0);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				System.out.println(sk_1.getProgress());

				btn_login.setVisibility(View.INVISIBLE);
				if (sk_1.getProgress() > 66 && sk_1.getProgress() < 76) {
					btn_login.setVisibility(View.VISIBLE);
				}

			}
		});
		et_ip.setText("19.1.10.2");
		et_port.setText("6006");
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (et_port.getText().toString().isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入端口号");
				} else if (et_ip.getText().toString().isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入IP地址");
				} else {
					if (sk_1.getProgress() > 66 && sk_1.getProgress() < 76) {
						Cursor cursor = db.rawQuery(
								"select * from user_login_number", null);
						if (et_ip.getText().toString().equals("19.1.10.2")
								&& et_port.getText().toString().equals("6006")) {
							if (cursor.getCount() != 0) {
								cursor.moveToLast();
								int number = Integer.valueOf(cursor.getString(cursor
										.getColumnIndex("login_number")));
								number++;
								db.execSQL(
										"insert into user_login_number (login_number,login_time)values(?,?)",
										new String[] { String.valueOf(number),
												GetTimeClass.time });
								get_user_login_number();
							}
							startActivity(new Intent(getApplicationContext(),
									BaseActivity.class));
						} else {
							DiyToast.showToast(getApplicationContext(),
									"IP或端口输入错误");
						}
					} else {
						sk_1.setProgress(0);
						DiyToast.showToast(getApplicationContext(), "请完成滑动验证");
					}
				}
			}
		});
	}

	private void get_user_login_number() {
		// TODO Auto-generated method stub
		Cursor cursor = db.rawQuery("select * from user_login_number", null);
		if (cursor.getCount() != 0) {
			cursor.moveToLast();
			tv_number_login.setText("之前已有"
					+ cursor.getString(cursor.getColumnIndex("login_number"))
					+ "次登录，上次登录时间为"
					+ cursor.getString(cursor.getColumnIndex("login_time")));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}