package com.example.cdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.cdemo.toast.DiyToast;

/**
 * 登录
 * 
 * @author A
 * 
 */
public class LoginActivity extends Activity {
	private Button btn_login;
	private SeekBar sk_1;
	private EditText et_ip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btn_login = (Button) findViewById(R.id.btn_login);
		et_ip = (EditText) findViewById(R.id.editText1);
		sk_1 = (SeekBar) findViewById(R.id.seekBar1);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub\
				if (et_ip.getText().toString().isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入IP地址");
				} else {
					if (et_ip.getText().toString().equals("18.1.10.7")) {
						ControlUtils.setUser("bizideal", "123456", "18.1.10.7");
						SocketClient.getInstance().creatConnect();
						SocketClient.getInstance().login(new LoginCallback() {

							@Override
							public void onEvent(final String arg0) {
								// TODO Auto-generated method stub
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated
										// method stub
										if (arg0.equals(ConstantUtil.FAILURE)) {
											DiyToast.showToast(
													getApplicationContext(),
													"网络连接成功");
											IndexActivity.web_state = "在线";
											startActivity(new Intent(
													getApplicationContext(),
													IndexActivity.class));
											finish();
										} else {
											DiyToast.showToast(
													getApplicationContext(),
													"网路连接失败");
											IndexActivity.web_state = "离线";
											startActivity(new Intent(
													getApplicationContext(),
													IndexActivity.class));
										}
									}
								});
							}
						});
					} else {
						DiyToast.showToast(getApplicationContext(), "IP地址错误");
					}
				}
			}
		});
		sk_1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() == 100) {
					if (et_ip.getText().toString().isEmpty()) {
						DiyToast.showToast(getApplicationContext(), "请输入IP地址");
						sk_1.setProgress(0);
					} else {
						if (et_ip.getText().toString().equals("18.1.10.7")) {
							ControlUtils.setUser("bizideal", "123456",
									"18.1.10.7");
							SocketClient.getInstance().creatConnect();
							SocketClient.getInstance().login(
									new LoginCallback() {

										@Override
										public void onEvent(final String arg0) {
											// TODO Auto-generated method stub
											runOnUiThread(new Runnable() {

												@Override
												public void run() {
													// TODO Auto-generated
													// method stub
													if (arg0.equals(ConstantUtil.SUCCESS)) {
														DiyToast.showToast(
																getApplicationContext(),
																"网络连接成功");
														startActivity(new Intent(
																getApplicationContext(),
																IndexActivity.class));
														finish();
													} else {
														DiyToast.showToast(
																getApplicationContext(),
																"网路连接失败");
														startActivity(new Intent(
																getApplicationContext(),
																IndexActivity.class));
													}
												}
											});
										}
									});
						} else {
							DiyToast.showToast(getApplicationContext(),
									"IP地址错误");
							sk_1.setProgress(0);
						}
					}
				} else {
					sk_1.setProgress(0);
					DiyToast.showToast(getApplicationContext(), "请完成滑动验证");
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

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
