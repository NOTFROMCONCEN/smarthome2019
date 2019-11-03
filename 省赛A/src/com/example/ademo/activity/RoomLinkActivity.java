package com.example.ademo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.ademo.R;
import com.example.ademo.fragment.LinkActivity;
import com.example.ademo.toast.DiyToast;

public class RoomLinkActivity extends Activity {
	private TextView tv_number;
	Spinner sp_1;
	Spinner sp_2;
	Spinner sp_3;
	Spinner sp_4;

	Switch sw_link_state;
	EditText et_number;
	boolean link_mode = false;

	static float temp, ill, hum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_link);
		tv_number = (TextView) findViewById(R.id.tv_room_number_link);
		tv_number.setText("∑øº‰∫≈£∫" + LinkActivity.room_numberString);
		sp_1 = (Spinner) findViewById(R.id.spinner1);
		sp_2 = (Spinner) findViewById(R.id.spinner2);
		sp_3 = (Spinner) findViewById(R.id.spinner3);
		sp_4 = (Spinner) findViewById(R.id.spinner4);
		sw_link_state = (Switch) findViewById(R.id.sw_link_state);
		et_number = (EditText) findViewById(R.id.et_number_get);
		ControlUtils.getData();
		SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {

			@Override
			public void onResult(final DeviceBean getdata) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (!TextUtils.isEmpty(getdata.getHumidity())) {
							hum = Float.valueOf(getdata.getHumidity());
						}
						if (!TextUtils.isEmpty(getdata.getIllumination())) {
							ill = Float.valueOf(getdata.getIllumination());
						}
						if (!TextUtils.isEmpty(getdata.getTemperature())) {
							temp = Float.valueOf(getdata.getTemperature());
						}
					}
				});
			}
		});
		sw_link_state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (et_number.getText().toString().isEmpty()) {
						DiyToast.showToast(getApplicationContext(), "«Î ‰»Î ˝÷µ");
						link_mode = false;
						sw_link_state.setChecked(false);
					} else {
						link_mode = true;
					}
				} else {
					link_mode = false;
				}
			}
		});
		hand.post(timeRunnable);
	}

	Handler hand = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (link_mode) {
				if (et_number.getText().toString().isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "«Î ‰»Î ˝÷µ");
					link_mode = false;
					sw_link_state.setChecked(false);
				} else {
					String spinner_1 = sp_1.getSelectedItem().toString();
					String spinner_2 = sp_2.getSelectedItem().toString();
					String spinner_3 = sp_3.getSelectedItem().toString();
					String spinner_4 = sp_4.getSelectedItem().toString();
					int number_get = Integer.valueOf(et_number.getText()
							.toString());
					System.out.println("runnaing");
					if (spinner_1.equals("’’∂»")) {
						if (spinner_2.equals(">")) {
							System.out.println(ill);
							if (ill > number_get) {
								if (spinner_4.equals("ø™")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
								}
								if (spinner_4.equals("πÿ")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
								}
							}

						}
						if (spinner_2.equals("<")) {

							if (ill < number_get) {
								if (spinner_4.equals("ø™")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
								}
								if (spinner_4.equals("πÿ")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);

									}
								}
							}

						}
					}
					if (spinner_1.equals(" ™∂»")) {

						if (spinner_2.equals(">")) {
							if (hum > number_get) {
								if (spinner_4.equals("ø™")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
								}
								if (spinner_4.equals("πÿ")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
								}

							}
						}
						if (spinner_2.equals("<")) {

							if (hum < number_get) {
								if (spinner_4.equals("ø™")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
								}
								if (spinner_4.equals("πÿ")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
								}
							}

						}

					}
					if (spinner_1.equals("Œ¬∂»")) {
						if (spinner_2.equals(">")) {
							if (temp > number_get) {
								if (spinner_4.equals("ø™")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
								}
								if (spinner_4.equals("πÿ")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
								}
							}
						}
						if (spinner_2.equals("<")) {

							if (temp < number_get) {
								if (spinner_4.equals("ø™")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									}
								}
								if (spinner_4.equals("πÿ")) {
									if (spinner_3.equals("∑Á…»")) {
										ControlUtils.control(ConstantUtil.Fan,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("…‰µ∆")) {
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
									if (spinner_3.equals("¥∞¡±")) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
								}
							}
						}
					}

				}
			}
			hand.postDelayed(timeRunnable, 1000);
		}
	};
	Runnable timeRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = hand.obtainMessage();
			hand.sendMessage(msg);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_room_link, menu);
		return true;
	}

}
