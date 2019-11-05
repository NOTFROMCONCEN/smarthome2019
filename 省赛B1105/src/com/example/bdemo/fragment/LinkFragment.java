package com.example.bdemo.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.example.bdemo.R;
import com.example.bdemo.toast.DiyToast;

/**
 * 联动模式
 * 
 * @author A
 * 
 */
public class LinkFragment extends Fragment {
	private EditText et_time_get, et_number_get;
	private Switch sw_warm;
	private Switch sw_door;
	private Switch sw_lamp;
	private Switch sw_fan;
	private Button btn_link_start;
	private Spinner sp_1;
	private Spinner sp_2;
	TextView tv_down_time;
	private CountDownTimer timer;
	private long num, min, sec;
	boolean link_state = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_link, container, false);
		sp_1 = (Spinner) view.findViewById(R.id.spinner1);
		sp_2 = (Spinner) view.findViewById(R.id.spinner2);
		sw_door = (Switch) view.findViewById(R.id.sw_door);
		sw_fan = (Switch) view.findViewById(R.id.sw_fan);
		sw_lamp = (Switch) view.findViewById(R.id.sw_lamp);
		sw_warm = (Switch) view.findViewById(R.id.sw_warm);
		tv_down_time = (TextView) view.findViewById(R.id.tv_down_time);
		btn_link_start = (Button) view.findViewById(R.id.btn_link_state);
		et_number_get = (EditText) view.findViewById(R.id.et_number_get);
		et_time_get = (EditText) view.findViewById(R.id.et_time_get);
		btn_link_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (et_number_get.getText().toString().isEmpty()
						|| et_time_get.getText().toString().isEmpty()) {
					DiyToast.showToast(getActivity(), "不能有空白");
				} else {
					if (btn_link_start.getText().toString().equals("开启联动")) {
						if (timer != null) {
							timer.cancel();
						}
						String spinner_1 = sp_1.getSelectedItem().toString();
						String spinner_2 = sp_2.getSelectedItem().toString();
						int numebr_get = Integer.valueOf(et_number_get
								.getText().toString());
						if (spinner_1.equals("温度")) {
							if (spinner_2.equals(">")) {
								if (BaseFragment.temp > numebr_get) {
									link_state = true;
									btn_link_start.setText("关闭联动");
									open_sw();
								} else {
									DiyToast.showToast(getActivity(), "条件不满足");
								}
							}
							if (spinner_2.equals("<=")) {
								if (BaseFragment.temp <= numebr_get) {
									link_state = true;
									btn_link_start.setText("关闭联动");
									open_sw();
								} else {
									DiyToast.showToast(getActivity(), "条件不满足");
								}
							}
						}
						if (spinner_1.equals("湿度")) {
							if (spinner_2.equals(">")) {
								if (BaseFragment.hum > numebr_get) {
									link_state = true;
									btn_link_start.setText("关闭联动");
									open_sw();
								} else {
									DiyToast.showToast(getActivity(), "条件不满足");
								}
							}
							if (spinner_2.equals("<=")) {
								if (BaseFragment.hum <= numebr_get) {
									link_state = true;
									btn_link_start.setText("关闭联动");
									open_sw();
								} else {
									DiyToast.showToast(getActivity(), "条件不满足");
								}
							}
						}
						if (spinner_1.equals("光照")) {
							if (spinner_2.equals(">")) {
								if (BaseFragment.ill > numebr_get) {
									link_state = true;
									btn_link_start.setText("关闭联动");
									open_sw();
								} else {
									DiyToast.showToast(getActivity(), "条件不满足");
								}
							}
							if (spinner_2.equals("<=")) {
								if (BaseFragment.ill <= numebr_get) {
									link_state = true;
									btn_link_start.setText("关闭联动");
									open_sw();
								} else {
									DiyToast.showToast(getActivity(), "条件不满足");
								}
							}
						}
					} else if (btn_link_start.getText().toString()
							.equals("关闭联动")) {
						cls_sw();
						/*
						 * 关闭联动模式
						 */
						if (timer != null) {// 清空计时器
							timer.cancel();
						}
						// 关闭联动状态
						link_state = false;
						// 重置时间
						tv_down_time.setText("联动模式还有X分X秒");
						// 重置按钮文本
						btn_link_start.setText("开启联动");
					}
					if (link_state) {
						/*
						 * 联动模式倒计时
						 */
						num = Integer.valueOf(et_time_get.getText().toString()) * 1000 * 60;
						timer = new CountDownTimer(num, 1000) {

							@Override
							public void onTick(long millisUntilFinished) {
								// TODO Auto-generated method stub
								min = millisUntilFinished / 1000 / 60;// 分
								sec = millisUntilFinished / 1000 % 60;// 秒
								tv_down_time.setText("联动模式还有" + min + "分" + sec
										+ "秒");// 开始倒计时
							}

							@Override
							public void onFinish() {
								// TODO Auto-generated method stub
								// 联动倒计时结束后的事件
								btn_link_start.setText("开启联动");// 重置文本
								cls_sw();// 关闭设备
								tv_down_time.setText("联动模式还有" + "X" + "分" + "X"
										+ "秒");// 重置倒计时
							}
						}.start();
					}
				}
			}
		});

		sw_door.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (link_state) {
						ControlUtils.control(ConstantUtil.RFID_Open_Door,
								ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
					} else {
						DiyToast.showToast(getActivity(), "请开启联动");
					}
				} else {

				}
			}
		});
		sw_warm.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (link_state) {
						ControlUtils.control(ConstantUtil.WarningLight,
								ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
					} else {
						DiyToast.showToast(getActivity(), "请开启联动");
					}
				} else {
					ControlUtils.control(ConstantUtil.WarningLight,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				}
			}
		});
		sw_lamp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (link_state) {
						ControlUtils.control(ConstantUtil.Lamp,
								ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
					} else {
						DiyToast.showToast(getActivity(), "请开启联动");
					}
				} else {
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				}
			}
		});
		sw_fan.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (link_state) {
						ControlUtils.control(ConstantUtil.Fan,
								ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
					} else {
						DiyToast.showToast(getActivity(), "请开启联动");
					}
				} else {
					ControlUtils.control(ConstantUtil.Fan,
							ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				}
			}
		});
		return view;
	}

	private void open_sw() {
		// TODO Auto-generated method stub
		if (sw_door.isChecked()) {
			ControlUtils.control(ConstantUtil.RFID_Open_Door,
					ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
		}
		if (sw_fan.isChecked()) {
			ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL,
					ConstantUtil.OPEN);
		}
		if (sw_lamp.isChecked()) {
			ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL,
					ConstantUtil.OPEN);
		}
		if (sw_warm.isChecked()) {
			ControlUtils.control(ConstantUtil.WarningLight,
					ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
		}
	}

	private void cls_sw() {
		// TODO Auto-generated method stub
		if (sw_fan.isChecked()) {
			ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL,
					ConstantUtil.CLOSE);
			sw_door.setChecked(false);
		}
		if (sw_lamp.isChecked()) {
			ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL,
					ConstantUtil.CLOSE);
			sw_lamp.setChecked(false);
		}
		if (sw_warm.isChecked()) {
			ControlUtils.control(ConstantUtil.WarningLight,
					ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
			sw_warm.setChecked(false);
		}
		if (sw_door.isChecked()) {
			sw_door.setChecked(false);
		}
	}
}
