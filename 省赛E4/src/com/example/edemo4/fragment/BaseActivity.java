package com.example.edemo4.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.edemo4.R;

public class BaseActivity extends Fragment {
	private RadioButton ra_open;
	private RadioButton ra_cls;
	private RadioButton ra_stop;
	private RadioButton ra_tv;
	private RadioButton ra_dvd;
	private RadioButton ra_kt;
	private RadioButton ra_lamp_l;
	private RadioButton ra_lamp_r;
	private RadioButton ra_cur;
	private RadioButton ra_door;
	private RadioButton ra_fan;
	private boolean tv_state = false;
	private boolean dvd_state = false;
	private boolean kt_state = false;
	private boolean lamP_l_state = false;
	private boolean lamP_r_state = false;
	private boolean cur_state = false;
	private boolean door_state = false;
	private boolean fan_state = false;
	public static float temp, hum, ill, press, co, pm, per, gas, smo;
	public static float getdata = 0;
	public static String numberString = "";
	public static String now_chose = "";

	private View view1;
	private View view2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_based, container, false);
		initView(view);
		view1 = (View) view.findViewById(R.id.myview1);
		view2 = (View) view.findViewById(R.id.myview2);
		view1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_show();
			}
		});
		view2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_show();
			}
		});
		view1.setVisibility(View.VISIBLE);
		view2.setVisibility(View.INVISIBLE);
		view1.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				view1.setVisibility(View.INVISIBLE);
				view2.setVisibility(View.VISIBLE);
				return false;
			}
		});
		view2.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				view1.setVisibility(View.VISIBLE);
				view2.setVisibility(View.INVISIBLE);
				return false;
			}
		});
		ControlUtils.getData();
		SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {

			@Override
			public void onResult(final DeviceBean getdata) {
				// TODO Auto-generated method stub

				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (!TextUtils.isEmpty(getdata.getAirPressure())) {
							press = Float.valueOf(getdata.getAirPressure());
						}
						if (!TextUtils.isEmpty(getdata.getCo2())) {
							co = Float.valueOf(getdata.getCo2());
						}
						if (!TextUtils.isEmpty(getdata.getGas())) {
							gas = Float.valueOf(getdata.getGas());
						}
						if (!TextUtils.isEmpty(getdata.getHumidity())) {
							hum = Float.valueOf(getdata.getHumidity());
						}
						if (!TextUtils.isEmpty(getdata.getIllumination())) {
							ill = Float.valueOf(getdata.getIllumination());
						}
						if (!TextUtils.isEmpty(getdata.getPM25())) {
							pm = Float.valueOf(getdata.getPM25());
						}
						if (!TextUtils.isEmpty(getdata.getSmoke())) {
							smo = Float.valueOf(getdata.getSmoke());
						}
						if (!TextUtils.isEmpty(getdata.getTemperature())) {
							temp = Float.valueOf(getdata.getTemperature());
						}
						if (!TextUtils.isEmpty(getdata.getStateHumanInfrared())) {
							if (getdata.getStateHumanInfrared().toString()
									.equals(ConstantUtil.CLOSE)) {
								per = 0;
							} else {
								per = 1;
							}
						}
						System.out.println(getdata.getAirPressure());
					}
				});
			}
		});
		ra_open.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ra_cur.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.Curtain,
							ConstantUtil.CHANNEL_3, ConstantUtil.OPEN);
				}
				if (ra_door.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.RFID_Open_Door,
							ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
				}
				if (ra_dvd.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "8",
							ConstantUtil.OPEN);
				}
				if (ra_fan.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.Fan,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
				}
				if (ra_kt.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "1",
							ConstantUtil.OPEN);
				}
				if (ra_lamp_l.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
				}
				if (ra_lamp_r.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_2, ConstantUtil.OPEN);
				}
				if (ra_tv.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "5",
							ConstantUtil.OPEN);
				}
			}
		});
		ra_cls.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ControlUtils.control(ConstantUtil.WarningLight,
						ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				if (ra_cur.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.Curtain,
							ConstantUtil.CHANNEL_1, ConstantUtil.CLOSE);
				}
				if (ra_door.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.RFID_Open_Door,
							ConstantUtil.CHANNEL_1, ConstantUtil.CLOSE);
				}
				if (ra_dvd.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "8",
							ConstantUtil.OPEN);
				}
				if (ra_fan.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.Fan,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				}
				if (ra_kt.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "1",
							ConstantUtil.OPEN);
				}
				if (ra_lamp_l.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_1, ConstantUtil.CLOSE);
				}
				if (ra_lamp_r.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_2, ConstantUtil.CLOSE);
				}
				if (ra_tv.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "5",
							ConstantUtil.OPEN);
				}
			}
		});
		ra_stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ra_cur.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.Curtain,
							ConstantUtil.CHANNEL_2, ConstantUtil.CLOSE);
				}
			}
		});
		handler.post(timeRunnable);
		return view;
	}

	private void dialog_show() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.dialog_show_chose, null, false);
		final RadioButton ra_temp = (RadioButton) view
				.findViewById(R.id.ra_temp);
		final RadioButton ra_hum = (RadioButton) view.findViewById(R.id.ra_hum);
		final RadioButton ra_ill = (RadioButton) view.findViewById(R.id.ra_ill);
		final RadioButton ra_smo = (RadioButton) view.findViewById(R.id.ra_smo);
		final RadioButton ra_gas = (RadioButton) view.findViewById(R.id.ra_gas);
		final RadioButton ra_per = (RadioButton) view.findViewById(R.id.ra_per);
		final RadioButton ra_pm = (RadioButton) view.findViewById(R.id.ra_pm);
		final RadioButton ra_co = (RadioButton) view.findViewById(R.id.ra_co);
		final RadioButton ra_press = (RadioButton) view
				.findViewById(R.id.ra_press);
		if (now_chose.equals("温度")) {
			ra_temp.setChecked(true);
		}
		if (now_chose.equals("烟雾")) {
			ra_smo.setChecked(true);
		}
		if (now_chose.equals("气压")) {
			ra_press.setChecked(true);
		}
		if (now_chose.equals("PM")) {
			ra_pm.setChecked(true);
		}
		if (now_chose.equals("人体")) {
			ra_per.setChecked(true);
		}
		if (now_chose.equals("光照")) {
			ra_ill.setChecked(true);
		}
		if (now_chose.equals("湿度")) {
			ra_hum.setChecked(true);
		}
		if (now_chose.equals("燃气")) {
			ra_gas.setChecked(true);
		}
		if (now_chose.equals("Co2")) {
			ra_co.setChecked(true);
		}
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (ra_co.isChecked()) {
					now_chose = "Co2";
				}
				if (ra_gas.isChecked()) {
					now_chose = "燃气";
				}
				if (ra_hum.isChecked()) {
					now_chose = "湿度";
				}
				if (ra_ill.isChecked()) {
					now_chose = "光照";
				}
				if (ra_per.isChecked()) {
					now_chose = "人体";
				}
				if (ra_pm.isChecked()) {
					now_chose = "PM";
				}
				if (ra_press.isChecked()) {
					now_chose = "气压";
				}
				if (ra_smo.isChecked()) {
					now_chose = "烟雾";
				}
				if (ra_temp.isChecked()) {
					now_chose = "温度";
				}
			}
		});
		builder.setView(view);
		builder.show();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (cur_state && door_state && dvd_state && fan_state && kt_state
					&& kt_state && lamP_l_state && lamP_r_state && lamP_r_state
					&& tv_state) {
				ControlUtils.control(ConstantUtil.WarningLight,
						ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
			}
			if (now_chose.equals("温度")) {
				getdata = temp;
			}
			if (now_chose.equals("烟雾")) {
				getdata = smo;
			}
			if (now_chose.equals("气压")) {
				getdata = press;
			}
			if (now_chose.equals("PM")) {
				getdata = pm;
			}
			if (now_chose.equals("人体")) {
				getdata = per;
			}
			if (now_chose.equals("光照")) {
				getdata = ill;
			}
			if (now_chose.equals("湿度")) {
				getdata = hum;
			}
			if (now_chose.equals("燃气")) {
				getdata = gas;
			}
			if (now_chose.equals("Co2")) {
				getdata = co;
			}
			System.out.println(getdata);

			handler.postDelayed(timeRunnable, 1000);
		}
	};
	Runnable timeRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);
		}
	};

	private void initView(View view) {
		// TODO Auto-generated method stub
		ra_cls = (RadioButton) view.findViewById(R.id.ra_cls);
		ra_cur = (RadioButton) view.findViewById(R.id.ra_cur);
		ra_door = (RadioButton) view.findViewById(R.id.ra_door);
		ra_dvd = (RadioButton) view.findViewById(R.id.ra_dvd);
		ra_fan = (RadioButton) view.findViewById(R.id.ra_fan);
		ra_kt = (RadioButton) view.findViewById(R.id.ra_kt);
		ra_lamp_l = (RadioButton) view.findViewById(R.id.ra_lamp_l);
		ra_lamp_r = (RadioButton) view.findViewById(R.id.ra_lamp_r);
		ra_open = (RadioButton) view.findViewById(R.id.ra_open);
		ra_stop = (RadioButton) view.findViewById(R.id.ra_stop);
		ra_tv = (RadioButton) view.findViewById(R.id.ra_tv);
	}
}
