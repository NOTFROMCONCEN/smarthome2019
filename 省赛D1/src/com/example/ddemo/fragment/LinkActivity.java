package com.example.ddemo.fragment;

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
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.example.ddemo.R;
import com.example.ddemo.toast.DiyToast;

public class LinkActivity extends Fragment {

	private Button btn_chose;
	private Spinner sp_1;
	private Spinner sp_2;
	private EditText et_number;
	private CheckBox cb_link_state;
	boolean cur_state = false;
	boolean warm_state = false;
	boolean lamp_state = false;
	boolean fan_state = false;
	boolean link_state = false;
	int number_time = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_link, container, false);
		btn_chose = (Button) view.findViewById(R.id.btn_chose);
		sp_1 = (Spinner) view.findViewById(R.id.spinner1);
		sp_2 = (Spinner) view.findViewById(R.id.spinner2);
		et_number = (EditText) view.findViewById(R.id.et_number);
		cb_link_state = (CheckBox) view.findViewById(R.id.cb_link_state);
		cb_link_state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (et_number.getText().toString().isEmpty()) {
						DiyToast.showToast(getActivity(), "请输入输入值");
						cb_link_state.setChecked(false);
						link_state = false;
					} else {
						link_state = true;
					}
				} else {
					link_state = false;
				}
			}
		});
		btn_chose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_chose();
			}
		});
		handler.post(timeRunnable);
		return view;
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (link_state) {
				if (et_number.getText().toString().isEmpty()) {
					DiyToast.showToast(getActivity(), "请输入输入值");
					cb_link_state.setChecked(false);
					link_state = false;
				} else {
					String spinner_1 = sp_1.getSelectedItem().toString();
					String spinner_2 = sp_2.getSelectedItem().toString();
					int number = Integer
							.valueOf(et_number.getText().toString());
					if (spinner_1.equals("温度")) {
						if (spinner_2.equals("大于")) {
							if (BaseActivity.temp > number) {
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_3,
												ConstantUtil.OPEN);
									}
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
								cb_link_state.setChecked(false);
								link_state = false;
								DiyToast.showToast(getActivity(), "条件不满足");
								if (cur_state) {
									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_1,
												ConstantUtil.CLOSE);
									}
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

							if (BaseActivity.temp < number) {
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_3,
												ConstantUtil.OPEN);
									}
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
								cb_link_state.setChecked(false);
								link_state = false;
								DiyToast.showToast(getActivity(), "条件不满足");
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_1,
												ConstantUtil.CLOSE);
									}
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
							if (BaseActivity.hum > number) {
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_3,
												ConstantUtil.OPEN);
									}
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
								cb_link_state.setChecked(false);
								link_state = false;
								DiyToast.showToast(getActivity(), "条件不满足");
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_1,
												ConstantUtil.CLOSE);
									}
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

							if (BaseActivity.hum < number) {
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_3,
												ConstantUtil.OPEN);
									}
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
								cb_link_state.setChecked(false);
								link_state = false;
								DiyToast.showToast(getActivity(), "条件不满足");
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_1,
												ConstantUtil.CLOSE);
									}
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
							if (BaseActivity.ill > number) {
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_3,
												ConstantUtil.OPEN);
									}
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
								cb_link_state.setChecked(false);
								link_state = false;
								DiyToast.showToast(getActivity(), "条件不满足");
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_1,
												ConstantUtil.CLOSE);
									}
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

							if (BaseActivity.ill < number) {
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_3,
												ConstantUtil.OPEN);
									}
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
								cb_link_state.setChecked(false);
								link_state = false;
								DiyToast.showToast(getActivity(), "条件不满足");
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_1,
												ConstantUtil.CLOSE);
									}
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

					if (spinner_1.equals("温度")) {
						if (spinner_2.equals("大于")) {
							if (BaseActivity.press > number) {
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_3,
												ConstantUtil.OPEN);
									}
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
								cb_link_state.setChecked(false);
								link_state = false;
								DiyToast.showToast(getActivity(), "条件不满足");
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_1,
												ConstantUtil.CLOSE);
									}
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

							if (BaseActivity.press < number) {
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_3,
												ConstantUtil.OPEN);
									}
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
								cb_link_state.setChecked(false);
								link_state = false;
								DiyToast.showToast(getActivity(), "条件不满足");
								if (cur_state) {

									if (number_time == 2) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_1,
												ConstantUtil.CLOSE);
									}
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
			handler.postDelayed(timeRunnable, 500);
		}
	};
	Runnable timeRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			number_time++;
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
		final CheckBox cb_curBox = (CheckBox) view.findViewById(R.id.cb_cur);
		final CheckBox cb_warmBox = (CheckBox) view.findViewById(R.id.cb_warm);
		final CheckBox cb_lampBox = (CheckBox) view.findViewById(R.id.cb_lamp);
		final CheckBox cb_fanBox = (CheckBox) view.findViewById(R.id.cb_fan);
		final Button btn_cls = (Button) view.findViewById(R.id.btn_chose_con);
		if (cur_state) {
			cb_curBox.setChecked(true);
		}
		if (lamp_state) {
			cb_lampBox.setChecked(true);
		}
		if (warm_state) {
			cb_warmBox.setChecked(true);
		}
		if (fan_state) {
			cb_fanBox.setChecked(true);
		}
		cb_curBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
		cb_fanBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
		cb_lampBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
		cb_warmBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
		btn_cls.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder.create();
			}
		});
		builder.show();
	}
}