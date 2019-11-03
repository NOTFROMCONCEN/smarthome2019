package com.example.ddemo2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.example.ddemo2.R;

public class ModeActivity extends Fragment {
	private ToggleButton tg_mode_state;
	private RadioButton ra_day_mode, ra_night_mode, ra_fd_mode;
	private boolean day_mode = false, night_mode = false, fd_mode = false,
			mode_state = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_mode, null, false);
		initView(view);
		tg_mode_state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					mode_state = true;
					if (ra_day_mode.isChecked()) {
						day_mode = true;
						night_mode = false;
						fd_mode = false;
						ControlUtils.control(ConstantUtil.Lamp,
								ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								while (day_mode && mode_state) {
									try {
										Thread.sleep(1000);
									} catch (Exception e) {
										// TODO: handle exception
									}
									if (BaseActivity.ill > 150) {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_1,
												ConstantUtil.CLOSE);
									} else {
										ControlUtils.control(
												ConstantUtil.Curtain,
												ConstantUtil.CHANNEL_3,
												ConstantUtil.OPEN);
									}
								}
							}
						}).start();
					}
					if (ra_night_mode.isChecked()) {
						day_mode = false;
						night_mode = true;
						fd_mode = false;
						ControlUtils.control(ConstantUtil.Lamp,
								ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
						ControlUtils.control(ConstantUtil.Curtain,
								ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								while (night_mode && mode_state) {
									try {
										Thread.sleep(1000);
									} catch (Exception e) {
										// TODO: handle exception
									}
									if (BaseActivity.ill < 50) {
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
					}
					if (ra_fd_mode.isChecked()) {
						day_mode = false;
						night_mode = false;
						fd_mode = true;
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								while (fd_mode && mode_state) {
									try {
										Thread.sleep(1000);
									} catch (Exception e) {
										// TODO: handle exception
									}
									if (BaseActivity.per == 1) {
										ControlUtils.control(
												ConstantUtil.WarningLight,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.OPEN);
									} else {
										ControlUtils.control(
												ConstantUtil.WarningLight,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
										ControlUtils.control(ConstantUtil.Lamp,
												ConstantUtil.CHANNEL_ALL,
												ConstantUtil.CLOSE);
									}
								}
							}
						}).start();
					}
				} else {
					mode_state = false;
					day_mode = false;
					night_mode = false;
					fd_mode = false;
				}
			}
		});
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		tg_mode_state = (ToggleButton) view.findViewById(R.id.tg_mode_state);
		ra_day_mode = (RadioButton) view.findViewById(R.id.ra_day_mode);
		ra_fd_mode = (RadioButton) view.findViewById(R.id.ra_fd_mode);
		ra_night_mode = (RadioButton) view.findViewById(R.id.ra_night_mode);
	}
}
