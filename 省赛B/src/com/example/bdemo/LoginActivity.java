package com.example.bdemo;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.example.bdemo.textchanger.TextChanger;
import com.example.bdemo.toast.DiyToast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	private Button btn_login;
	private EditText et_user, et_pass, et_port, et_ip;
	String user, pass, port, ip;
	TextView tv_tips, tv_time;
	SharedPreferences sharedPreferences;
	int number = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		et_ip.setText("12.1.6.2");
		/*
		 * 記住密碼
		 */
		sharedPreferences = getSharedPreferences("remebr", MODE_WORLD_WRITEABLE);
		if (sharedPreferences != null) {
			if (sharedPreferences.getBoolean("rember", false) == true) {
				et_ip.setText(sharedPreferences.getString("ip", null));
				et_pass.setText(sharedPreferences.getString("pass", null));
				et_port.setText(sharedPreferences.getString("port", null));
				et_user.setText(sharedPreferences.getString("user", null));
			} else {
				et_ip.setText("12.1.6.2");
				et_port.setText("6006");
				et_user.setText("bizideal001");
				et_pass.setText("123456");
			}
		}
		// 登录
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				user = et_user.getText().toString();
				pass = et_pass.getText().toString();
				ip = et_ip.getText().toString();
				port = et_port.getText().toString();
				if (user.isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入用户名");
				} else if (port.isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入端口号");
				} else if (ip.isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入IP地址");
				} else if (pass.isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入密码");
				} else {
					if (user.equals("bizideal001") && pass.equals("123456")) {
						// 登陆成功
						startActivity(new Intent(LoginActivity.this,
								UnLockActivity.class));
						finish();
						// 记住密码
						sharedPreferences.edit().putString("user", user)
								.putBoolean("rember", true)
								.putString("pass", pass).putString("ip", ip)
								.putString("port", port).commit();
					} else {
						new AlertDialog.Builder(LoginActivity.this)
								.setMessage("密码或用户名错误").setTitle("登陆失败")
								.setPositiveButton("Ok", null);
					}
				}
			}
		});
		/*
		 * 字符转换
		 */
		et_pass.setTransformationMethod(new TextChanger());
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_login = (Button) findViewById(R.id.btn_login);
		et_ip = (EditText) findViewById(R.id.et_ip);
		et_port = (EditText) findViewById(R.id.et_port);
		et_pass = (EditText) findViewById(R.id.et_pass);
		et_user = (EditText) findViewById(R.id.et_user);
		sharedPreferences = getSharedPreferences("rember", MODE_WORLD_WRITEABLE);
		tv_time = (TextView) findViewById(R.id.tv_login_time);
		tv_tips = (TextView) findViewById(R.id.tv_login_tips);
		handler.post(timeRunnable);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy年MM月dd日 HH:mm:ss");
			tv_time.setText(simpleDateFormat.format(new java.util.Date()));
			if (msg.what % 2 == 0) {
				tv_tips.setVisibility(View.INVISIBLE);
			} else {
				tv_tips.setVisibility(View.VISIBLE);
			}
			handler.postDelayed(timeRunnable, 1000);
		}
	};
	Runnable timeRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			number++;
			Message msg = handler.obtainMessage();
			msg.what = number;
			handler.sendMessage(msg);
		}
	};

}
