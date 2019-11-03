package com.example.edemo.fragment;

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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.edemo2.MainActivity;
import com.example.edemo2.R;

/**
 * 
 * @author A
 * 
 */
public class BaseActivity extends Fragment {
	private RadioButton ra_open, ra_cls, ra_stop;
	boolean tv_state = false;
	boolean dvd_state = false;
	boolean kt_state = false;
	boolean lamp_l_state = false;
	boolean lamp_r_state = false;
	boolean cur_state = false;
	boolean door_state = false;
	boolean fan_state = false;
	static String string_name = "";
	View view1;
	View view2;
	private SeekBar sk_1;
	TextView tv_l, tv_r;
	private RadioButton ra_tv;
	private RadioButton ra_dvd;
	private RadioButton ra_kt;
	private RadioButton ra_lamp_l;
	private RadioButton ra_lamp_r;
	private RadioButton ra_cur;
	private RadioButton ra_door;
	private RadioButton ra_fan;
	public static float getdata;
	public static int number = 0;
	static float temp, hum, ill, press, pm, co, per, gas, smo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_base, container, false);
		initView(view);
		/*
		 * 数据采集
		 */
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
						if (!TextUtils.isEmpty(getdata.getAirPressure())) {
							if (getdata.getStateHumanInfrared().toString()
									.equals(ConstantUtil.CLOSE)) {
								per = 0;
							} else {
								per = 1;
							}
						}
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
					door_state = true;
					ControlUtils.control(ConstantUtil.RFID_Open_Door,
							ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
				}
				if (ra_fan.isChecked()) {
					fan_state = true;
					ControlUtils.control(ConstantUtil.Fan,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
				}
				if (ra_lamp_l.isChecked()) {
					lamp_l_state = true;
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
				}
				if (ra_lamp_r.isChecked()) {
					lamp_r_state = true;
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_2, ConstantUtil.OPEN);
				}
				if (ra_dvd.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "8",
							ConstantUtil.OPEN);
				}
				if (ra_kt.isChecked()) {
					cur_state = true;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "1",
							ConstantUtil.OPEN);
				}
				if (ra_tv.isChecked()) {
					cur_state = true;
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

		ra_cls.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub.
				ControlUtils.control(ConstantUtil.WarningLight,
						ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				if (ra_cur.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.Curtain,
							ConstantUtil.CHANNEL_1, ConstantUtil.CLOSE);
				}
				if (ra_door.isChecked()) {
					door_state = false;
					ControlUtils.control(ConstantUtil.RFID_Open_Door,
							ConstantUtil.CHANNEL_1, ConstantUtil.CLOSE);
				}
				if (ra_fan.isChecked()) {
					fan_state = false;
					ControlUtils.control(ConstantUtil.Fan,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				}
				if (ra_lamp_l.isChecked()) {
					lamp_l_state = false;
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_1, ConstantUtil.CLOSE);
				}
				if (ra_lamp_r.isChecked()) {
					lamp_r_state = false;
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_2, ConstantUtil.CLOSE);
				}
				if (ra_dvd.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "8",
							ConstantUtil.OPEN);
				}
				if (ra_kt.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "1",
							ConstantUtil.OPEN);
				}
				if (ra_tv.isChecked()) {
					cur_state = false;
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "5",
							ConstantUtil.OPEN);
				}
			}
		});

		handler.post(timeRunnable);
		sk_1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				number = sk_1.getProgress();
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
		view1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_showe_chose();
			}
		});
		view2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_showe_chose();
			}
		});
		view2.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				view2.setVisibility(View.INVISIBLE);
				view1.setVisibility(View.VISIBLE);
				return false;
			}
		});
		view1.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				view1.setVisibility(View.INVISIBLE);
				view2.setVisibility(View.VISIBLE);
				return false;
			}
		});
		return view;
	}

	private void dialog_showe_chose() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.dialog_chose, null, false);
		builder.setView(view);
		final RadioButton ra_temp = (RadioButton) view
				.findViewById(R.id.ra_temp);
		final RadioButton ra_hum = (RadioButton) view.findViewById(R.id.ra_hum);
		final RadioButton ra_press = (RadioButton) view
				.findViewById(R.id.ra_press);
		final RadioButton ra_pm = (RadioButton) view.findViewById(R.id.ra_pm);
		final RadioButton ra_per = (RadioButton) view.findViewById(R.id.ra_per);
		final RadioButton ra_smo = (RadioButton) view.findViewById(R.id.ra_smo);
		final RadioButton ra_gas = (RadioButton) view.findViewById(R.id.ra_gas);
		final RadioButton ra_co = (RadioButton) view.findViewById(R.id.ra_temp);
		final RadioButton ra_ill = (RadioButton) view.findViewById(R.id.ra_ill);
		if (string_name.equals("温度")) {
			ra_temp.setChecked(true);
		}
		if (string_name.equals("湿度")) {
			ra_hum.setChecked(true);
		}
		if (string_name.equals("烟雾")) {
			ra_smo.setChecked(true);
		}
		if (string_name.equals("气压")) {
			ra_press.setChecked(true);
		}
		if (string_name.equals("PM")) {
			ra_pm.setChecked(true);
		}
		if (string_name.equals("Co2")) {
			ra_co.setChecked(true);
		}
		if (string_name.equals("人体")) {
			ra_per.setChecked(true);
		}
		if (string_name.equals("光照")) {
			ra_ill.setChecked(true);
		}
		if (string_name.equals("燃气")) {
			ra_gas.setChecked(true);
		}
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (ra_co.isChecked()) {
					string_name = "Co2";
				}
				if (ra_gas.isChecked()) {
					string_name = "燃气";
				}
				if (ra_hum.isChecked()) {
					string_name = "湿度";
				}
				if (ra_ill.isChecked()) {
					string_name = "光照";
				}
				if (ra_per.isChecked()) {
					string_name = "人体";
				}
				if (ra_pm.isChecked()) {
					string_name = "PM";
				}
				if (ra_press.isChecked()) {
					string_name = "气压";
				}
				if (ra_smo.isChecked()) {
					string_name = "烟雾";
				}
				if (ra_temp.isChecked()) {
					string_name = "温度";
				}
			}
		});

		builder.show();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (cur_state && door_state && dvd_state && fan_state && kt_state
					&& lamp_l_state && lamp_r_state && tv_state) {
				ControlUtils.control(ConstantUtil.WarningLight,
						ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
			}
			if (string_name.equals("温度")) {
				getdata = temp;
			}
			if (string_name.equals("湿度")) {
				getdata = hum;
			}
			if (string_name.equals("烟雾")) {
				getdata = smo;
			}
			if (string_name.equals("气压")) {
				getdata = press;
			}
			if (string_name.equals("PM")) {
				getdata = pm;
			}
			if (string_name.equals("Co2")) {
				getdata = co;
			}
			if (string_name.equals("人体")) {
				getdata = per;
			}
			if (string_name.equals("光照")) {
				getdata = ill;
			}
			if (string_name.equals("燃气")) {
				getdata = gas;
			}
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
		tv_l = (TextView) view.findViewById(R.id.tv_number_l);
		tv_r = (TextView) view.findViewById(R.id.tv_number_r);
		sk_1 = (SeekBar) view.findViewById(R.id.sk_number);
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
		ra_cls = (RadioButton) view.findViewById(R.id.ra_cls);
		view1 = (View) view.findViewById(R.id.myview1);
		view2 = (View) view.findViewById(R.id.myview2);
	}
}
