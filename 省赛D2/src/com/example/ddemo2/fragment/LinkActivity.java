package com.example.ddemo2.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.example.ddemo2.R;
import com.example.ddemo2.toast.DiyToast;

public class LinkActivity extends Fragment {
	boolean cur_state = false, lamp_state = false, warm_state = false,
			fan_state = false, link_mode = false, set_link_mode = false;
	Button btn_show;
	private Spinner sp_1;
	private Spinner sp_2;
	private EditText et_number_get;
	private CheckBox cb_link_mode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_link, null, false);
		view.findViewById(R.id.btn_chose).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog_chose();
					}
				});
		sp_1 = (Spinner) view.findViewById(R.id.spinner1);
		sp_2 = (Spinner) view.findViewById(R.id.spinner2);
		et_number_get = (EditText) view.findViewById(R.id.et_number);
		cb_link_mode = (CheckBox) view.findViewById(R.id.cb_link_state);
		cb_link_mode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (cur_state || warm_state || lamp_state || fan_state) {
						if (!et_number_get.getText().toString().isEmpty()) {
							link_mode = true;
						} else {
							DiyToast.showToast(getActivity(), "请输入参数");
							link_mode = false;
							cb_link_mode.setChecked(false);
						}
					} else {
						DiyToast.showToast(getActivity(), "请选择执行器件");
						cb_link_mode.setChecked(false);
						link_mode = false;
					}
				} else {
					link_mode = false;
				}
			}
		});
		handler.post(timeRunnable);
		return view;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (cur_state || warm_state || lamp_state || fan_state) {
				if (!et_number_get.getText().toString().isEmpty()) {
					link_mode = true;
				} else {
					DiyToast.showToast(getActivity(), "请输入参数");
					link_mode = false;
					cb_link_mode.setChecked(false);
				}
			} else {
				cb_link_mode.setChecked(false);
				link_mode = false;
			}
			if (link_mode) {
				if (set_link_mode) {

					String spinner_1 = sp_1.getSelectedItem().toString();
					String spinner_2 = sp_2.getSelectedItem().toString();
					int number_get = Integer.valueOf(et_number_get.getText()
							.toString());
					if (spinner_1.equals("温度")) {
						if (spinner_2.equals("大于")) {
							if (BaseActivity.temp > number_get) {
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_3,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								DiyToast.showToast(getActivity(), "错误：条件不满足");
								link_mode = false;
								cb_link_mode.setChecked(false);
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_1,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}
						}
						if (spinner_2.equals("小于")) {

							if (BaseActivity.temp < number_get) {
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_3,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								DiyToast.showToast(getActivity(), "错误：条件不满足");
								link_mode = false;
								cb_link_mode.setChecked(false);
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_1,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}

						}
					}
					if (spinner_1.equals("气压")) {

						if (spinner_2.equals("大于")) {
							if (BaseActivity.press > number_get) {
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_3,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								DiyToast.showToast(getActivity(), "错误：条件不满足");
								link_mode = false;
								cb_link_mode.setChecked(false);
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_1,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}
						}
						if (spinner_2.equals("小于")) {

							if (BaseActivity.press < number_get) {
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_3,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								DiyToast.showToast(getActivity(), "错误：条件不满足");
								link_mode = false;
								cb_link_mode.setChecked(false);
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_1,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}

						}

					}
					if (spinner_1.equals("光照")) {

						if (spinner_2.equals("大于")) {
							if (BaseActivity.ill > number_get) {
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_3,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								DiyToast.showToast(getActivity(), "错误：条件不满足");
								link_mode = false;
								cb_link_mode.setChecked(false);
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_1,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}
						}
						if (spinner_2.equals("小于")) {

							if (BaseActivity.ill < number_get) {
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_3,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								DiyToast.showToast(getActivity(), "错误：条件不满足");
								link_mode = false;
								cb_link_mode.setChecked(false);
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_1,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}

						}

					}
					if (spinner_1.equals("湿度")) {

						if (spinner_2.equals("大于")) {
							if (BaseActivity.hum > number_get) {
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_3,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								DiyToast.showToast(getActivity(), "错误：条件不满足");
								link_mode = false;
								cb_link_mode.setChecked(false);
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_1,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}
						}
						if (spinner_2.equals("小于")) {

							if (BaseActivity.hum < number_get) {
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_3,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								DiyToast.showToast(getActivity(), "错误：条件不满足");
								link_mode = false;
								cb_link_mode.setChecked(false);
								if (cur_state) {
									ControlUtils.control(ConstantUtil.Curtain,
											ConstantUtil.CHANNEL_1,
											ConstantUtil.OPEN);
								}
								if (fan_state) {
									ControlUtils.control(ConstantUtil.Fan,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (lamp_state) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (warm_state) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}

						}

					}
				}
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

	private void dialog_chose() {
		// TODO Auto-generated method stub
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				getActivity());
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.diaolog_chose_shebei, null, false);
		builder.setView(view);
		Button btn_con = (Button) view.findViewById(R.id.btn_chose_con);
		CheckBox ra_lamp, ra_warm, ra_cur, ra_fan;
		ra_cur = (CheckBox) view.findViewById(R.id.cb_cur);
		ra_fan = (CheckBox) view.findViewById(R.id.cb_fan);
		ra_lamp = (CheckBox) view.findViewById(R.id.cb_lamp);
		ra_warm = (CheckBox) view.findViewById(R.id.cb_warm);
		btn_con.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder.create();
				DiyToast.showToast(getActivity(), "设置成功");

				if (cur_state || fan_state || lamp_state || warm_state) {

				} else {
					DiyToast.showToast(getActivity(), "请选择");
				}
			}
		});
		ra_cur.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					cur_state = true;
				} else {
					cur_state = false;
				}
			}
		});
		ra_fan.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					fan_state = true;
				} else {
					fan_state = false;
				}
			}
		});
		ra_lamp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					lamp_state = true;
				} else {
					lamp_state = false;
				}
			}
		});
		ra_warm.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					warm_state = true;
				} else {
					warm_state = false;
				}
			}
		});
		if (cur_state) {
			ra_cur.setChecked(true);
		}
		if (lamp_state) {
			ra_lamp.setChecked(true);
		}
		if (fan_state) {
			ra_fan.setChecked(true);
		}
		if (warm_state) {
			ra_warm.setChecked(true);
		}
		builder.show();
	}
}
