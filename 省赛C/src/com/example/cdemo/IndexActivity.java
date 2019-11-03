package com.example.cdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.cdemo.toast.DiyToast;

public class IndexActivity extends Activity {
	private TextView tv_temp;
	private TextView tv_hum;
	private TextView tv_ill;
	private TextView tv_press;
	private TextView tv_smo;
	private TextView tv_gas;
	private TextView tv_per;
	private TextView tv_co;
	private TextView tv_pm;
	private ToggleButton tg_lamp;
	private ToggleButton tg_warm;
	private ToggleButton tg_door;
	private ToggleButton tg_fan;
	private Button btn_open;
	private Button btn_stop;
	private Button btn_cls;

	Button btn_link_state;

	private Button btn_dvd;
	private Button btn_kt;
	private Button btn_tv;

	int number = 0;

	private CheckBox cb_af_mode;
	private CheckBox cb_ly_mode;
	private CheckBox cb_temp_mode;
	private CheckBox cb_diy_mode;
	private Spinner sp_1;
	private Spinner sp_2;
	private Spinner sp_3;
	private EditText et_number_get;

	boolean ly_mode = false;
	boolean temp_mode = false;
	boolean af_mode = false;

	static String web_state;

	static float temp, hum, ill, press, smo, gas, per, co, pm;

	private ArrayAdapter<String> mAdapter;
	private String[] mStrings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		initView();

		/**
		 * ; 网络连接
		 */
		SocketClient.getInstance().login(new LoginCallback() {

			@Override
			public void onEvent(final String arg0) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (arg0.equals(ConstantUtil.SUCCESS)) {
							DiyToast.showToast(getApplicationContext(), "重连成功");
							web_state = "在线";
						} else if (arg0.equals(ConstantUtil.FAILURE)) {
							DiyToast.showToast(getApplicationContext(), "重连失败");
							web_state = "离线";
						} else {
							DiyToast.showToast(getApplicationContext(), "重连中");
							web_state = "重连";
						}
					}
				});
			}
		});
		/**
		 * 数据采集
		 */
		ControlUtils.getData();
		SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {

			@Override
			public void onResult(final DeviceBean getdata) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (!TextUtils.isEmpty(getdata.getAirPressure())) {
							tv_press.setText(getdata.getAirPressure());
							press = Float.valueOf(getdata.getAirPressure());
						}
						if (!TextUtils.isEmpty(getdata.getCo2())) {
							tv_co.setText(getdata.getCo2());
							co = Float.valueOf(getdata.getCo2());
						}
						if (!TextUtils.isEmpty(getdata.getGas())) {
							tv_gas.setText(getdata.getGas());
							gas = Float.valueOf(getdata.getGas());
						}
						if (!TextUtils.isEmpty(getdata.getHumidity())) {
							tv_hum.setText(getdata.getHumidity());
							hum = Float.valueOf(getdata.getAirPressure());
						}
						if (!TextUtils.isEmpty(getdata.getIllumination())) {
							tv_ill.setText(getdata.getIllumination());
							ill = Float.valueOf(getdata.getIllumination());
							System.out.println(ill);
						}
						if (!TextUtils.isEmpty(getdata.getPM25())) {
							tv_pm.setText(getdata.getPM25());
							pm = Float.valueOf(getdata.getPM25());
						}
						if (!TextUtils.isEmpty(getdata.getSmoke())) {
							tv_smo.setText(getdata.getSmoke());
							smo = Float.valueOf(getdata.getSmoke());
						}
						if (!TextUtils.isEmpty(getdata.getTemperature())) {
							tv_temp.setText(getdata.getTemperature());
							temp = Float.valueOf(getdata.getTemperature());
						}
						if (!TextUtils.isEmpty(getdata.getStateHumanInfrared())) {
							if (getdata.getStateHumanInfrared().toString()
									.equals(ConstantUtil.CLOSE)) {
								tv_per.setText("无人");
								per = 0;
							} else {
								tv_per.setText("有人");
								per = 1;
							}
						}
					}
				});
			}
		});
		/**
		 * 設備控制
		 */
		tg_door.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					ControlUtils.control(ConstantUtil.RFID_Open_Door,
							ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
				} else {
					ControlUtils.control(ConstantUtil.RFID_Open_Door,
							ConstantUtil.CHANNEL_1, ConstantUtil.CLOSE);
				}

			}
		});
		tg_fan.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					ControlUtils.control(ConstantUtil.Fan,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
				} else {
					ControlUtils.control(ConstantUtil.Fan,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				}

			}
		});
		tg_lamp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
				} else {
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				}

			}
		});
		tg_warm.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					ControlUtils.control(ConstantUtil.WarningLight,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
				} else {
					ControlUtils.control(ConstantUtil.WarningLight,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				}

			}
		});

		/**
		 * 紅外
		 */
		btn_dvd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "8",
						ConstantUtil.OPEN);
			}
		});
		btn_kt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "1",
						ConstantUtil.OPEN);
			}
		});
		btn_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "5",
						ConstantUtil.OPEN);
			}
		});
		btn_dvd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "8",
						ConstantUtil.OPEN);
			}
		});
		/**
		 * 窗簾
		 */
		btn_cls.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ControlUtils.control(ConstantUtil.Curtain,
						ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);

			}
		});
		btn_open.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ControlUtils.control(ConstantUtil.Curtain,
						ConstantUtil.CHANNEL_3, ConstantUtil.OPEN);

			}
		});
		btn_stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ControlUtils.control(ConstantUtil.Curtain,
						ConstantUtil.CHANNEL_2, ConstantUtil.OPEN);

			}
		});
		cb_diy_mode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (et_number_get.getText().toString().isEmpty()) {
						DiyToast.showToast(getApplicationContext(), "请输入数值");
					} else {
						String spinner_1 = sp_1.getSelectedItem().toString();
						String spinner_2 = sp_2.getSelectedItem().toString();
						String spinner_3 = sp_3.getSelectedItem().toString();
						int number = Integer.valueOf(et_number_get.getText()
								.toString());
						if (spinner_1.equals("温度")) {
							if (spinner_2.equals(">")) {
								if (temp > number) {
									if (spinner_3.equals("射灯")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
										tg_lamp.setChecked(true);
									}
									if (spinner_3.equals("风扇")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
										tg_fan.setChecked(true);
									}
								} else {
									DiyToast.showToast(getApplicationContext(),
											"条件不满足");
									cb_diy_mode.setChecked(false);
								}
							}
							if (spinner_2.equals("<=")) {
								if (temp <= number) {
									if (spinner_3.equals("射灯")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
										tg_lamp.setChecked(true);
									}
									if (spinner_3.equals("风扇")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
										tg_fan.setChecked(true);
									}
								} else {
									DiyToast.showToast(getApplicationContext(),
											"条件不满足");
									cb_diy_mode.setChecked(false);
									tg_warm.setChecked(false);
									tg_lamp.setChecked(false);
								}

							}
						}
						if (spinner_1.equals("光照")) {

							if (spinner_2.equals(">")) {
								if (ill > number) {
									if (spinner_3.equals("射灯")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("风扇")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
								} else {
									DiyToast.showToast(getApplicationContext(),
											"条件不满足");
									cb_diy_mode.setChecked(false);
								}
							}
							if (spinner_2.equals("<=")) {
								if (ill <= number) {
									if (spinner_3.equals("射灯")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("风扇")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
								} else {
									DiyToast.showToast(getApplicationContext(),
											"条件不满足");
									cb_diy_mode.setChecked(false);
								}

							}

						}
					}
				}
			}
		});
		cb_temp_mode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					af_mode = false;
					ly_mode = false;
					temp_mode = true;
					cb_af_mode.setChecked(false);
					cb_ly_mode.setChecked(false);
					cb_temp_mode.setChecked(true);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							while (temp_mode) {
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
									// TODO: handle exception
								}
								number++;
								switch (number) {
								case 1:
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
									break;
								case 2:
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
									break;
								case 3:
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
									break;
								case 4:
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
									break;
								case 5:
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
									break;
								case 6:
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
									break;
								default:
									break;
								}
							}
						}
					}).start();

				} else {
					temp_mode = false;
				}
			}
		});
		cb_ly_mode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					af_mode = false;
					ly_mode = true;
					temp_mode = false;
					cb_af_mode.setChecked(false);
					cb_ly_mode.setChecked(true);
					cb_temp_mode.setChecked(false);
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
					ControlUtils.control(ConstantUtil.Curtain,
							ConstantUtil.CHANNEL_3, ConstantUtil.OPEN);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							while (ly_mode) {
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
									// TODO: handle exception
								}
								if (pm > 75) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								} else {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}
						}
					}).start();
				} else {
					ly_mode = false;
				}
			}
		});

		cb_af_mode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					af_mode = true;
					ly_mode = false;
					temp_mode = false;
					cb_af_mode.setChecked(true);
					cb_ly_mode.setChecked(false);
					cb_temp_mode.setChecked(false);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							while (af_mode) {
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
									// TODO: handle exception
								}
								if (per == 1) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								} else {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}
						}
					}).start();
				} else {
					af_mode = false;
				}
			}
		});
		btn_link_state.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(IndexActivity.this,
						LinkStateActivity.class));
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_index, menu);
		return true;
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_dvd = (Button) findViewById(R.id.btn_dvd);
		btn_cls = (Button) findViewById(R.id.btn_cls);
		btn_kt = (Button) findViewById(R.id.btn_kt);
		btn_open = (Button) findViewById(R.id.btn_open);
		btn_stop = (Button) findViewById(R.id.btn_cstop);
		btn_tv = (Button) findViewById(R.id.btn_tv);
		tg_door = (ToggleButton) findViewById(R.id.tg_door);
		tg_fan = (ToggleButton) findViewById(R.id.tg_fan);
		tg_lamp = (ToggleButton) findViewById(R.id.tg_lamp);
		tg_warm = (ToggleButton) findViewById(R.id.tg_warm);
		tv_co = (TextView) findViewById(R.id.tv_co);
		tv_gas = (TextView) findViewById(R.id.tv_gas);
		tv_hum = (TextView) findViewById(R.id.tv_hum);
		tv_ill = (TextView) findViewById(R.id.tv_ill);
		tv_per = (TextView) findViewById(R.id.tv_per);
		tv_pm = (TextView) findViewById(R.id.tv_pm);
		tv_press = (TextView) findViewById(R.id.tv_press);
		tv_smo = (TextView) findViewById(R.id.tv_smo);
		tv_temp = (TextView) findViewById(R.id.tv_temp);
		cb_af_mode = (CheckBox) findViewById(R.id.cb_af_mode);
		cb_temp_mode = (CheckBox) findViewById(R.id.cb_temp_mode);
		cb_ly_mode = (CheckBox) findViewById(R.id.cb_ly_mode);
		cb_diy_mode = (CheckBox) findViewById(R.id.cb_diy_mode);
		sp_1 = (Spinner) findViewById(R.id.spinner1);
		sp_2 = (Spinner) findViewById(R.id.spinner2);
		sp_3 = (Spinner) findViewById(R.id.spinner3);
		et_number_get = (EditText) findViewById(R.id.et_number_get);
		btn_link_state = (Button) findViewById(R.id.btn_link_state);

		mStrings = getResources().getStringArray(R.array.temp_ill);
		mAdapter = new AdapterHelper(getApplicationContext(), mStrings);
		sp_1.setAdapter(mAdapter);

	}
}
