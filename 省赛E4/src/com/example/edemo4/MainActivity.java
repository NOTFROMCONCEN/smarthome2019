package com.example.edemo4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.edemo4.fragment.BarActivity;
import com.example.edemo4.mysql.MyDataBaseHelper;
import com.example.edemo4.toast.DiyToast;

public class MainActivity extends Activity {
	private Button btn_login;
	private Button btn_reg;
	private Button btn_updata_pass;
	private Button btn_reback_pass;

	private EditText et_user;
	private EditText et_pass;
	private EditText et_port;
	private EditText et_ip;
	private Spinner sp_user;
	private String user, pass, port, ip;
	private RadioButton ra_chose_user, ra_set_user;
	private CheckBox cb_remberBox;  
	private boolean user_mode = false;

	MyDataBaseHelper dbHelper;
	SQLiteDatabase db;

	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

		dbHelper = new MyDataBaseHelper(getApplicationContext(), "info.db",
				null, 2);
		db = dbHelper.getWritableDatabase();
		sharedPreferences = getSharedPreferences("rember", MODE_WORLD_WRITEABLE);
		get_user();
		if (sharedPreferences != null) {
			if (sharedPreferences.getBoolean("rember", false) == true) {
				cb_remberBox.setChecked(true);
				if (sharedPreferences.getBoolean("user_mode", false) == true) {
					ra_chose_user.setChecked(true);
					ra_set_user.setChecked(false);
					et_user.setVisibility(View.GONE);
					sp_user.setVisibility(View.VISIBLE);
				} else {
					ra_chose_user.setChecked(false);
					ra_set_user.setChecked(true);
					et_user.setVisibility(View.VISIBLE);
					sp_user.setVisibility(View.GONE);
				}
				et_ip.setText(sharedPreferences.getString("ip", null));
				et_pass.setText(sharedPreferences.getString("pass", null));
				et_port.setText(sharedPreferences.getString("port", null));
				et_user.setText(sharedPreferences.getString("user", null));
			} else {
				et_ip.setText("");
				et_pass.setText("");
				et_port.setText("");
				et_user.setText("");
			}
		}
		btn_reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_reg();
			}
		});
		btn_reback_pass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_reback_pass();
			}
		});
		btn_updata_pass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_updata_pass();
			}
		});
		ra_chose_user.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					user_mode = true;
					et_user.setVisibility(View.GONE);
					sp_user.setVisibility(View.VISIBLE);
				} else {
					user_mode = false;
					sp_user.setVisibility(View.GONE);
					et_user.setVisibility(View.VISIBLE);
				}
			}
		});
		ra_set_user.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					user_mode = false;
					et_user.setVisibility(View.VISIBLE);
					sp_user.setVisibility(View.GONE);
				} else {
					user_mode = true;
					sp_user.setVisibility(View.VISIBLE);
					et_user.setVisibility(View.GONE);
				}
			}
		});
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pass = et_pass.getText().toString();
				port = et_port.getText().toString();
				ip = et_ip.getText().toString();
				if (user_mode) {
					Cursor cursor = (Cursor) sp_user.getItemAtPosition(sp_user
							.getSelectedItemPosition());
					user = cursor.getString(1);
					login_state();
				} else {
					user = et_user.getText().toString();
					login_state();
				}
			}
		});
	}

	private void login_state() {
		// TODO Auto-generated method stub
		if (ip.isEmpty()) {
			DiyToast.showToast(getApplicationContext(), "请输入IP");
		} else if (port.isEmpty()) {
			DiyToast.showToast(getApplicationContext(), "请输入端口");
		} else if (user.isEmpty()) {
			DiyToast.showToast(getApplicationContext(), "请输入用户名");
		} else if (pass.isEmpty()) {
			DiyToast.showToast(getApplicationContext(), "请输入密码");
		} else {
			Cursor cursor = db.rawQuery(
					"select * from user where username = ? and passward =?",
					new String[] { user, pass });
			if (cursor.moveToNext()) {
				DiyToast.showToast(getApplicationContext(), "成功");
				startActivity(new Intent(getApplicationContext(),
						BarActivity.class));
				if (cb_remberBox.isChecked()) {
					if (user_mode) {
						sharedPreferences.edit().putBoolean("rember", true)
								.putBoolean("user_mode", true)
								.putString("user", user)
								.putString("pass", pass).putString("ip", ip)
								.putString("port", port).commit();
					} else {
						sharedPreferences.edit().putBoolean("rember", true)
								.putBoolean("user_mode", false)
								.putString("user", user)
								.putString("pass", pass).putString("ip", ip)
								.putString("port", port).commit();
					}
				} else {
					sharedPreferences.edit().putBoolean("rember", false)
							.putBoolean("user_mode", false)
							.putString("user", user).putString("pass", pass)
							.putString("ip", ip).putString("port", port)
							.commit();
				}
			} else {
				DiyToast.showToast(getApplicationContext(), "用户名或密码输入错误");
			}
		}
	}

	private void dialog_updata_pass() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		View view = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.dialog_updata_pass, null, false);
		builder.setView(view);
		final EditText et_updata_oldpass = (EditText) view
				.findViewById(R.id.et_updata_oldpass);
		final EditText et_updata_user = (EditText) view
				.findViewById(R.id.et_updata_user);
		final EditText et_updata_newpass = (EditText) view
				.findViewById(R.id.et_updata_newpass);
		builder.setPositiveButton("修改密码",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (et_updata_user.getText().toString().isEmpty()) {
							DiyToast.showToast(getApplicationContext(),
									"请输入用户名");
							dialog_updata_pass();
						} else if (et_updata_oldpass.getText().toString()
								.isEmpty()) {
							DiyToast.showToast(getApplicationContext(),
									"请输入旧密码");
							dialog_updata_pass();

						} else if (et_updata_newpass.getText().toString()
								.isEmpty()) {
							DiyToast.showToast(getApplicationContext(),
									"请输入新密码");
							dialog_updata_pass();

						} else {
							Cursor cursor = db.rawQuery(
									"select * from user where username = ?",
									new String[] { et_updata_user.getText()
											.toString() });
							if (cursor.moveToNext()) {
								if (cursor.getString(
										cursor.getColumnIndex("passward"))
										.equals(et_updata_oldpass.getText()
												.toString())) {
									if (et_updata_newpass
											.getText()
											.toString()
											.equals(et_updata_oldpass.getText()
													.toString())) {
										DiyToast.showToast(
												getApplicationContext(),
												"新旧密码不能一致");
										dialog_updata_pass();
									} else {
										db.execSQL(
												"update user set passward = ? where username = ?",
												new String[] {
														et_updata_newpass
																.getText()
																.toString(),
														et_updata_user
																.getText()
																.toString() });
										DiyToast.showToast(
												getApplicationContext(),
												"密码修改成功");
									}
								} else {
									DiyToast.showToast(getApplicationContext(),
											"旧密码错误");
									dialog_updata_pass();
								}
							} else {
								DiyToast.showToast(getApplicationContext(),
										"用户名不存在");
								dialog_updata_pass();
							}
						}
					}
				});
		builder.show();

	}

	private void dialog_reback_pass() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		View view = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.dialog_reback_pass, null, false);
		builder.setView(view);
		final EditText et_user = (EditText) view
				.findViewById(R.id.et_reback_user);
		final EditText et_two_pass = (EditText) view
				.findViewById(R.id.et_reback_twopass);
		builder.setPositiveButton("找回密码",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (et_user.getText().toString().isEmpty()) {
							DiyToast.showToast(getApplicationContext(),
									"请输入用户名");
						} else if (et_two_pass.getText().toString().isEmpty()) {
							DiyToast.showToast(getApplicationContext(),
									"请输入二级密码");
						} else {
							Cursor cursor = db
									.rawQuery(
											"select * from user where username = ?",
											new String[] { et_user.getText()
													.toString() });
							if (cursor.moveToNext()) {
								if (cursor.getString(
										cursor.getColumnIndex("twopass"))
										.equals(et_two_pass.getText()
												.toString())) {
									DiyToast.showToast(
											getApplicationContext(),
											"密码："
													+ cursor.getString(cursor
															.getColumnIndex("passward")));
								} else {
									DiyToast.showToast(getApplicationContext(),
											"二级密码错误");
								}
							} else {
								DiyToast.showToast(getApplicationContext(),
										"用户名不存在");
							}
						}
					}
				});
		builder.setView(view);
		builder.show();
	}

	private void fghjklhjkl() {
		// TODO Auto-generated method stub

	}

	private void dialog_reg() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		View view = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.dialog_rteg, null, false);
		final EditText et_euser = (EditText) view.findViewById(R.id.et_euser);
		final EditText et_epass = (EditText) view.findViewById(R.id.et_euser);
		final EditText et_repass = (EditText) view.findViewById(R.id.et_repass);
		final EditText et_two_pass = (EditText) view
				.findViewById(R.id.et_two_pass);
		builder.setPositiveButton("注册", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				if (et_euser.getText().toString().isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入用户名");
					dialog_reg();
				} else if (et_epass.getText().toString().isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入密码");
					dialog_reg();
				} else if (et_repass.getText().toString().isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入确认密码");
					dialog_reg();
				} else if (et_two_pass.getText().toString().isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入二级密码");
					dialog_reg();
				} else {
					if (et_epass.getText().toString()
							.equals(et_repass.getText().toString())) {
						Cursor cursor = db.rawQuery(
								"select * from user where username = ?",
								new String[] { et_euser.getText().toString() });
						if (cursor.moveToNext()) {
							DiyToast.showToast(getApplicationContext(),
									"用户名已存在");
							dialog_reg();
						} else {
							db.execSQL(
									"insert into user (username,passward,twopass)values(?,?,?)",
									new String[] {
											et_euser.getText().toString(),
											et_epass.getText().toString(),
											et_two_pass.getText().toString() });
							DiyToast.showToast(getApplicationContext(), "注册成功");
							get_user();
						}
					} else {
						DiyToast.showToast(getApplicationContext(), "两次密码输入不一致");
						dialog_reg();
					}
				}
			}
		});


		builder.show();
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_reback_pass = (Button) findViewById(R.id.btn_reback_pass);
		btn_reg = (Button) findViewById(R.id.btn_reg);
		btn_updata_pass = (Button) findViewById(R.id.btn_updata_pass);
		et_ip = (EditText) findViewById(R.id.et_ip);
		et_pass = (EditText) findViewById(R.id.et_pass);
		et_port = (EditText) findViewById(R.id.et_port);
		et_user = (EditText) findViewById(R.id.et_user);
		sp_user = (Spinner) findViewById(R.id.sp_user);
		cb_remberBox = (CheckBox) findViewById(R.id.cb_rember);
		ra_chose_user = (RadioButton) findViewById(R.id.ra_chose_user);
		ra_set_user = (RadioButton) findViewById(R.id.ra_set_pass);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void get_user() {
		// TODO Auto-generated method stub
		Cursor cursor = db.rawQuery("select * from user", null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
					getApplicationContext(), R.layout.item, cursor,
					new String[] { "username" }, new int[] { R.id.tv_set_user });
			sp_user.setAdapter(simpleCursorAdapter);
		}
	}

}
