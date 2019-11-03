package com.example.bdemo.fragment;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.example.bdemo.R;

/**
 * 模式
 * 
 * @author A
 * 
 */
public class ModeActivity extends Fragment {
	TextView tv_time;
	private ToggleButton tg_mode_state;
	private RadioButton ra_day_mode;
	private RadioButton ra_fd_mode;
	private RadioButton ra_night_mode;
	private RadioButton ra_dance_mode;
	private boolean mode_state = false, fd_state = false, day_mode = false,
			dance_mode = false, night_mode = false;
	int number = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_mode, container, false);
		tv_time = (TextView) view.findViewById(R.id.tv_mode_time);
		handler.post(timeRunnable);
		initView(view);
		tg_mode_state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					mode_state = true;
					if (ra_dance_mode.isChecked()) {
						dance_mode = true;
						fd_state = false;
						day_mode = false;
						night_mode = false;
						ControlUtils.control(ConstantUtil.INFRARED_1_SERVE,
								"3", ConstantUtil.OPEN);
					}
				} else {
					mode_state = false;
				}
			}
		});
		handler.post(timeRunnable);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		ra_dance_mode = (RadioButton) view.findViewById(R.id.ra_dance_mode);
		ra_day_mode = (RadioButton) view.findViewById(R.id.ra_day_mode);
		ra_fd_mode = (RadioButton) view.findViewById(R.id.ra_fd_mode);
		ra_night_mode = (RadioButton) view.findViewById(R.id.ra_night_mode);
		tg_mode_state = (ToggleButton) view.findViewById(R.id.tg_mode_state);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (dance_mode && mode_state) {
				System.out.println(msg.what);
				if (msg.what % 2 == 0) {
					System.out.println(111);
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_2, ConstantUtil.CLOSE);
				} else {
					System.out.println(222);
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_1, ConstantUtil.CLOSE);
					ControlUtils.control(ConstantUtil.Lamp,
							ConstantUtil.CHANNEL_2, ConstantUtil.OPEN);
				}
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy年MM月dd日 HH:mm:ss");
			tv_time.setText(simpleDateFormat.format(new java.util.Date()));
			handler.postDelayed(timeRunnable, 1000);
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
}
