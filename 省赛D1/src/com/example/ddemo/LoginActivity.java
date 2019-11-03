package com.example.ddemo;

import com.example.ddemo.fragment.BarActivity;
import com.example.ddemo.textchanger.TextChanger;
import com.example.ddemo.toast.DiyToast;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends Activity implements OnClickListener {
	private Button btn_login;
	private Button btn_reg;
	private Button btn_cls;
	private Button btn_exit;
	private Button btn_con;
	private Button btn_updata_pass;
	private Button btn_updata_con;
	private Button btn_updata_cls;
	private EditText et_user, et_pass, et_ip;
	private EditText et_euser, et_epass, et_repass;
	private EditText et_updata_user, et_updata_oldpass, et_updata_newpass;

	SharedPreferences sharedPreferences;
	MyDataBaseHelper dbHelper;
	SQLiteDatabase db;

	private LinearLayout line_login;
	private LinearLayout line_reg;
	private LinearLayout line_updata_pass;

	String euser, epass, repass;
	String user, pass, ip;
	String updata_user, updata_newpass, updata_oldpass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		dbHelper = new MyDataBaseHelper(getApplicationContext(), "info.db",
				null, 2);
		db = dbHelper.getWritableDatabase();
		sharedPreferences = getSharedPreferences("rember", MODE_WORLD_WRITEABLE);
		if (sharedPreferences != null) {
			et_pass.setText(sharedPreferences.getString("pass", null));
			et_user.setText(sharedPreferences.getString("user", null));
			et_ip.setText(sharedPreferences.getString("ip", null));
		}
		et_pass.setTransformationMethod(new TextChanger());
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_cls = (Button) findViewById(R.id.btn_cls);
		btn_con = (Button) findViewById(R.id.btn_con);
		btn_exit = (Button) findViewById(R.id.btn_exit);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_reg = (Button) findViewById(R.id.btn_reg);
		btn_updata_cls = (Button) findViewById(R.id.btn_updata_cls);
		btn_updata_con = (Button) findViewById(R.id.btn_updata_con);
		btn_updata_pass = (Button) findViewById(R.id.btn_updata_pass);

		et_epass = (EditText) findViewById(R.id.et_epass);
		et_euser = (EditText) findViewById(R.id.et_euser);
		et_ip = (EditText) findViewById(R.id.et_ip);
		et_pass = (EditText) findViewById(R.id.et_pass);
		et_repass = (EditText) findViewById(R.id.et_repass);
		et_updata_newpass = (EditText) findViewById(R.id.et_updata_newpass);
		et_updata_oldpass = (EditText) findViewById(R.id.et_updata_oldpass);
		et_updata_user = (EditText) findViewById(R.id.et_updata_user);
		et_user = (EditText) findViewById(R.id.et_user);
		btn_cls.setOnClickListener(this);
		btn_con.setOnClickListener(this);
		btn_exit.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		btn_reg.setOnClickListener(this);
		btn_updata_con.setOnClickListener(this);
		btn_updata_cls.setOnClickListener(this);
		btn_updata_pass.setOnClickListener(this);
		line_login = (LinearLayout) findViewById(R.id.line_login);
		line_updata_pass = (LinearLayout) findViewById(R.id.line_updata_pass);
		line_reg = (LinearLayout) findViewById(R.id.line_reg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cls:
			line_login.setVisibility(View.VISIBLE);
			line_reg.setVisibility(View.INVISIBLE);
			line_updata_pass.setVisibility(View.INVISIBLE);
			break;
		case R.id.btn_exit:
			System.exit(0);
			break;
		case R.id.btn_con:
			euser = et_euser.getText().toString();
			epass = et_epass.getText().toString();
			repass = et_repass.getText().toString();
			if (euser.isEmpty()) {
				DiyToast.showToast(getApplicationContext(), "请输入用户名");
			} else if (epass.isEmpty()) {
				DiyToast.showToast(getApplicationContext(), "请输入密码");
			} else if (repass.isEmpty()) {
				DiyToast.showToast(getApplicationContext(), "请输入确认密码");
			} else {
				if (epass.equals(repass)) {
					Cursor cursor = db.rawQuery(
							"select * from user where username = ?",
							new String[] { euser });
					if (cursor.moveToNext()) {
						new AlertDialog.Builder(LoginActivity.this)
								.setTitle("注册失败")
								.setMessage("用户名已存在，请重新注册")
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
					} else {
						db.execSQL(
								"insert into user (username,passward)values(?,?)",
								new String[] { euser, epass });
						new AlertDialog.Builder(LoginActivity.this)
								.setTitle("注册成功")
								.setMessage("用户注册成功")
								.setPositiveButton("Ok",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												line_login
														.setVisibility(View.VISIBLE);
												line_reg.setVisibility(View.INVISIBLE);
												line_updata_pass
														.setVisibility(View.INVISIBLE);
											}
										}).show();
					}
				} else {
					new AlertDialog.Builder(LoginActivity.this)
							.setTitle("注册失败")
							.setMessage("重复密码不一致")
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
			break;
		case R.id.btn_login:
			user = et_user.getText().toString();
			pass = et_pass.getText().toString();
			ip = et_ip.getText().toString();
			if (ip.isEmpty()) {
				DiyToast.showToast(getApplicationContext(), "请输入IP地址");
			} else if (user.isEmpty()) {
				DiyToast.showToast(getApplicationContext(), "请输入用户名");
			} else if (pass.isEmpty()) {
				DiyToast.showToast(getApplicationContext(), "请输入密码");
			} else {
				Cursor condition = db
						.rawQuery(
								"select * from user where username = ? and passward =?",
								new String[] { user, pass });
				if (condition.moveToNext()) {
					DiyToast.showToast(getApplicationContext(), "登陆成功");
					startActivity(new Intent(getApplicationContext(),
							BarActivity.class));
					sharedPreferences.edit().putString("user", user)
							.putString("pass", pass).putString("ip", ip)
							.commit();
				} else {
					new AlertDialog.Builder(LoginActivity.this)
							.setTitle("登录失败")
							.setMessage("用户名或密码输入错误")
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
			break;
		case R.id.btn_reg:
			line_login.setVisibility(View.INVISIBLE);
			line_reg.setVisibility(View.VISIBLE);
			line_updata_pass.setVisibility(View.INVISIBLE);
			break;
		case R.id.btn_updata_cls:
			line_login.setVisibility(View.VISIBLE);
			line_reg.setVisibility(View.INVISIBLE);
			line_updata_pass.setVisibility(View.INVISIBLE);
			break;
		case R.id.btn_updata_con:
			updata_user = et_updata_user.getText().toString();
			updata_newpass = et_updata_newpass.getText().toString();
			updata_oldpass = et_updata_oldpass.getText().toString();
			if (updata_user.isEmpty()) {
				DiyToast.showToast(getApplicationContext(), "请输入用户名");
			} else if (updata_oldpass.isEmpty()) {
				DiyToast.showToast(getApplicationContext(), "请输入旧密码");
			} else if (updata_newpass.isEmpty()) {
				DiyToast.showToast(getApplicationContext(), "请输入新密码");
			} else {
				Cursor condition = db.rawQuery(
						"select * from user where username = ? ",
						new String[] { updata_user });
				if (condition.moveToNext()) {
					if (condition
							.getString(condition.getColumnIndex("passward"))
							.toString()
							.equals(et_updata_oldpass.getText().toString())) {
						if (updata_newpass.equals(updata_oldpass)) {
							new AlertDialog.Builder(LoginActivity.this)
									.setTitle("修改失败")
									.setMessage("新旧密码不能一致")
									.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method
													// stub

												}
											}).show();
						} else {
							db.execSQL(
									"update user set passward = ? where username = ?",
									new String[] { updata_newpass, updata_user });
							new AlertDialog.Builder(LoginActivity.this)
									.setTitle("修改成功")
									.setMessage("密码修改成功")
									.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method
													// stub

												}
											}).show();
						}
					} else {
						new AlertDialog.Builder(LoginActivity.this)
								.setTitle("修改失败")
								.setMessage("旧密码错误")
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
				} else {
					new AlertDialog.Builder(LoginActivity.this)
							.setTitle("修改失败")
							.setMessage("修改失败：输入的用户名不存在")
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
			break;
		case R.id.btn_updata_pass:
			line_login.setVisibility(View.INVISIBLE);
			line_reg.setVisibility(View.INVISIBLE);
			line_updata_pass.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}
}
