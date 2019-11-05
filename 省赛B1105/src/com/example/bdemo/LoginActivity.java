package com.example.bdemo;

import com.example.bdemo.textchanger.TextChanger;
import com.example.bdemo.time.GetTimeClass;
import com.example.bdemo.toast.DiyToast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

/**
 * ��¼����
 * 
 * @author A
 * 
 */
public class LoginActivity extends Activity {

	private TextView tv_time;
	private TextView tv_tips;
	private EditText et_user;
	private EditText et_port;
	private EditText et_pass;
	private EditText et_ip;

	SharedPreferences sharedPreferences;

	private Button btn_login;

	int number = 0;

	public static String IP_numnber = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		/*
		 * ��ס����
		 */
		sharedPreferences = getSharedPreferences("rember", MODE_WORLD_WRITEABLE);
		if (sharedPreferences != null) {
			if (sharedPreferences.getBoolean("rember", false) == true) {
				et_pass.setText(sharedPreferences.getString("pass", null));
				et_ip.setText(sharedPreferences.getString("ip", null));
				et_port.setText(sharedPreferences.getString("port", null));
				et_user.setText(sharedPreferences.getString("user", null));
			} else {
				et_ip.setText("19.1.10.2");
				et_pass.setText("123456");
				et_port.setText("6006");
				et_user.setText("bizideal004");
			}

		}
		/*
		 * ��¼
		 */
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (et_user.getText().toString().isEmpty()) {// �û���Ϊ��
					DiyToast.showToast(getApplicationContext(), "�������û���");
				} else if (et_port.getText().toString().isEmpty()) {// �˿�Ϊ��
					DiyToast.showToast(getApplicationContext(), "������˿ں�");
				} else if (et_ip.getText().toString().isEmpty()) {// IP��ַΪ��
					DiyToast.showToast(getApplicationContext(), "������IP��ַ");
				} else if (et_pass.getText().toString().isEmpty()) {// ����Ϊ��
					DiyToast.showToast(getApplicationContext(), "����������");
				} else {
					if (et_user.getText().toString().equals("bizideal004")
							&& et_pass.getText().toString().equals("123456")) {
						IP_numnber = et_ip.getText().toString();
						startActivity(new Intent(getApplicationContext(),
								UnLockActivity.class));
						finish();
						// ��ס������뱾�δ洢
						sharedPreferences
								.edit()
								.putString("user", et_user.getText().toString())
								.putBoolean("rember", true)
								.putString("pass", et_pass.getText().toString())
								.putString("port", et_port.getText().toString())
								.putString("ip", et_ip.getText().toString())
								.commit();
					} else {

						new AlertDialog.Builder(LoginActivity.this)
								.setTitle("��½ʧ��")
								.setMessage("������û�������")
								.setPositiveButton("Ok",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub

											}
										}).show();
					}
				}
			}
		});
		GetTimeClass.startthread();
		handler.post(timeRunnable);
		et_pass.setTransformationMethod(new TextChanger());
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_login = (Button) findViewById(R.id.btn_login);
		et_ip = (EditText) findViewById(R.id.et_ip);
		et_pass = (EditText) findViewById(R.id.et_pass);
		et_port = (EditText) findViewById(R.id.et_port);
		et_user = (EditText) findViewById(R.id.et_user);
		tv_time = (TextView) findViewById(R.id.tv_login_time);
		tv_tips = (TextView) findViewById(R.id.tv_login_tips);
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
			if (msg.what % 2 == 0) {
				tv_tips.setVisibility(View.VISIBLE);
			} else {
				tv_tips.setVisibility(View.INVISIBLE);
			}
			tv_time.setText(GetTimeClass.time);
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
