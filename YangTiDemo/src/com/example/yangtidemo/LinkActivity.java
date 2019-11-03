package com.example.yangtidemo;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.example.yangtidemo.sql.MyDataBaseHelper;
import com.example.yangtidemo.time.GetTimeClass;
import com.example.yangtidemo.toast.DiyToast;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

public class LinkActivity extends Activity {

	private ListView lv_1;
	private CheckBox cb_mode_state;
	private CheckBox cb_link_state;
	private CheckBox cb_op_state;
	private Spinner sp_1;
	private Spinner sp_2;
	private Spinner sp_3;
	private EditText et_number_get;
	private Switch sw_in_out_home;
	int number = 0;
	int log_number = 0;
	boolean in_home_out_home = false;
	Button btn_save;
	Button btn_get;
	EditText et_shebei, et_dongzuo, et_get;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link);
		initView();
		cb_link_state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (et_number_get.getText().toString().isEmpty()) {
						DiyToast.showToast(getApplicationContext(), "请输入数值");
						cb_link_state.setChecked(false);
					}
					// cb_link_state.setChecked(false);
					cb_mode_state.setChecked(false);
					cb_op_state.setChecked(false);
					number = 0;
				}
			}
		});
		cb_mode_state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					cb_link_state.setChecked(false);
					// cb_mode_state.setChecked(false);
					cb_op_state.setChecked(false);
				}
			}
		});
		cb_op_state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					cb_link_state.setChecked(false);
					cb_mode_state.setChecked(false);
					// cb_op_state.setChecked(false);
				}
			}
		});
		sw_in_out_home
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							if (cb_mode_state.isChecked()) {
								number = 0;
								in_home_out_home = true;
							} else {
								DiyToast.showToast(getApplicationContext(),
										"请勾选模式控制");
								in_home_out_home = false;
							}
						} else {
							in_home_out_home = false;
							number = 0;
						}
					}
				});
		handler.post(timeRunnable);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			System.out.println("进程启用");
			if (cb_link_state.isChecked()) {
				String spinner_1 = sp_1.getSelectedItem().toString();
				String spinner_2 = sp_2.getSelectedItem().toString();
				String spinner_3 = sp_3.getSelectedItem().toString();
				int number_get = Integer.valueOf(et_number_get.getText()
						.toString());
				if (spinner_1.equals("温度")) {
					if (spinner_2.equals("≥")) {
						if (BaseActivity.temp >= number_get) {
							if (spinner_3.equals("开")) {
								ControlUtils.control(ConstantUtil.Fan,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
								insert_intolog("风扇", "开");
							}
							if (spinner_3.equals("关")) {
								ControlUtils.control(ConstantUtil.Fan,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
								insert_intolog("风扇", "关");
							}
						}
					}
					if (spinner_2.equals("<")) {
						if (BaseActivity.temp < number_get) {
							if (spinner_3.equals("开")) {
								ControlUtils.control(ConstantUtil.Fan,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
								insert_intolog("风扇", "开");
							}
							if (spinner_3.equals("关")) {
								ControlUtils.control(ConstantUtil.Fan,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
								insert_intolog("风扇", "关");
							}
						}
					}
				}
				if (spinner_1.equals("湿度")) {
					if (spinner_2.equals("≥")) {
						if (BaseActivity.hum >= number_get) {
							if (spinner_3.equals("开")) {
								ControlUtils.control(ConstantUtil.Fan,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
								insert_intolog("风扇", "开");
							}
							if (spinner_3.equals("关")) {
								ControlUtils.control(ConstantUtil.Fan,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
								insert_intolog("风扇", "关");
							}
						}
					}
					if (spinner_2.equals("<")) {
						if (BaseActivity.hum < number_get) {
							if (spinner_3.equals("开")) {
								ControlUtils.control(ConstantUtil.Fan,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
								insert_intolog("风扇", "开");
							}
							if (spinner_3.equals("关")) {
								ControlUtils.control(ConstantUtil.Fan,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
								insert_intolog("风扇", "关");
							}
						}
					}
				}
			}
			if (cb_mode_state.isChecked()) {
				if (in_home_out_home == false) {
					System.out.println("在家");
					switch (msg.what) {
					case 1:
						ControlUtils.control(ConstantUtil.Lamp,
								ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
						insert_intolog("射灯", "关");
						break;
					case 2:
						ControlUtils.control(ConstantUtil.Curtain,
								ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
						insert_intolog("窗帘", "关");
						break;
					case 3:
						ControlUtils.control(ConstantUtil.INFRARED_1_SERVE,
								"1", ConstantUtil.OPEN);
						insert_intolog("空调", "开");
						break;

					default:
						break;
					}
				}
				if (in_home_out_home == true) {
					System.out.println(222);
					if (BaseActivity.smo > 600 || BaseActivity.per == 1) {
						ControlUtils.control(ConstantUtil.WarningLight,
								ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
						insert_intolog("报警灯", "开");
						ControlUtils.control(ConstantUtil.Curtain,
								ConstantUtil.CHANNEL_3, ConstantUtil.OPEN);
						insert_intolog("窗帘", "开");
						ControlUtils.control(ConstantUtil.Fan,
								ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
						insert_intolog("风扇", "开");
					}

				}
			}
			if (cb_link_state.isChecked() || cb_mode_state.isChecked()
					|| cb_op_state.isChecked()) {
				get_log_into_listview();
			}
			handler.postDelayed(timeRunnable, 1500);
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

	private void initView() {
		// TODO Auto-generated method stub
		sw_in_out_home = (Switch) findViewById(R.id.sw_in_out_home);
		sp_1 = (Spinner) findViewById(R.id.spinner1);
		sp_2 = (Spinner) findViewById(R.id.spinner2);
		sp_3 = (Spinner) findViewById(R.id.spinner3);
		cb_mode_state = (CheckBox) findViewById(R.id.cb_mode_state);
		cb_link_state = (CheckBox) findViewById(R.id.cb_link_state);
		cb_op_state = (CheckBox) findViewById(R.id.cb_op_state);
		et_number_get = (EditText) findViewById(R.id.et_number_get);
		lv_1 = (ListView) findViewById(R.id.listView1);
		btn_get = (Button) findViewById(R.id.btn_get);
		btn_save = (Button) findViewById(R.id.btn_save);
		et_dongzuo = (EditText) findViewById(R.id.et_dongzuo);
		et_get = (EditText) findViewById(R.id.et_get);
		et_shebei = (EditText) findViewById(R.id.et_shebei);
	}

	private void get_log_into_listview() {
		// TODO Auto-generated method stub
		MyDataBaseHelper dbHelper = new MyDataBaseHelper(
				getApplicationContext(), "info.db", null, 2);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from log", null);
		if (cursor.getCount() != 0) {
			SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
					getApplicationContext(), R.layout.listview_text, cursor,
					new String[] { "number", "shebei", "state", "time" },
					new int[] { R.id.tv_number, R.id.tv_shebei, R.id.tv_state,
							R.id.tv_time });
			lv_1.setAdapter(simpleCursorAdapter);
		}
	}

	private void insert_intolog(String shebei, String state) {
		// TODO Auto-generated method stub
		log_number++;
		MyDataBaseHelper dbHelper = new MyDataBaseHelper(
				getApplicationContext(), "info.db", null, 2);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("insert into log(number,shebei,state,time)values(?,?,?,?)",
				new String[] { String.valueOf(log_number), shebei, state,
						GetTimeClass.time });
	}

	// private void get_op_sql(String string) {
	// // TODO Auto-generated method stub
	// MyDataBaseHelper dbHelper = new MyDataBaseHelper(
	// getApplicationContext(), "info.db", null, 2);
	// SQLiteDatabase db = dbHelper.getWritableDatabase();
	// Cursor cursor = db.rawQuery("select * from op_save where shebei = ?",
	// new String[] { string });
	// if (cursor.getCount() != 0) {
	// cursor.moveToNext();
	// if (cursor.getString(cursor.getColumnIndex("dongzuo")).equals("开")) {
	// if (cursor.getString(cursor.getColumnIndex("shebei")).equals(
	// "")) {
	// }
	// }
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_link, menu);
		return true;
	}
}