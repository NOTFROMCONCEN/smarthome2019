package com.example.ademo.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.ademo.R;
import com.example.ademo.activity.RoomControlActivity;
import com.example.ademo.sql.MyDataBaseHelper;

public class ControlActivity extends Fragment implements OnClickListener {
	private LinearLayout line_8101;
	private LinearLayout line_8102;
	private LinearLayout line_8103;
	private LinearLayout line_8104;
	private LinearLayout line_8201;
	private LinearLayout line_8202;
	private LinearLayout line_8203;
	private LinearLayout line_8204;
	private LinearLayout line_8301;
	private LinearLayout line_8302;
	private LinearLayout line_8303;
	private LinearLayout line_8304;
	private LinearLayout line_8401;
	private LinearLayout line_8402;
	private LinearLayout line_8403;
	private LinearLayout line_8404;

	public static String room_numberString = "";

	MyDataBaseHelper dbHelper;
	SQLiteDatabase db;

	static float temp, hum, ill, press, smo, gas, pm, per, co;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_index, container, false);
		dbHelper = new MyDataBaseHelper(getActivity(), "info.db", null, 2);
		db = dbHelper.getWritableDatabase();
		initView(view);
		get_room_state_810x();
		get_room_state_820x();
		get_room_state_830x();
		get_room_state_840x();
		ControlUtils.getData();
		handler.post(timeRunnable);
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
							if (getdata.getStateHumanInfrared().equals(
									ConstantUtil.CLOSE)) {
								per = 0;
							} else {
								per = 1;
							}
						}

					}
				});
			}
		});

		return view;
	}

	private void dialog_data_base() {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("房间管理");
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.dialog_show_base, null, false);
		builder.setView(view);
		TextView tv_room_number = (TextView) view
				.findViewById(R.id.tv_room_base_number);
		tv_room_number.setText("房间号：" + room_numberString);
		Button btn_yiruzhu = (Button) view.findViewById(R.id.btn_yiruzhu);
		Button btn_weiruzhu = (Button) view.findViewById(R.id.btn_weiruzhu);
		Button btn_weidasao = (Button) view.findViewById(R.id.btn_weidasao);
		btn_weidasao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.execSQL("insert into room" + room_numberString
						+ "(roomstate)values(?)", new String[] { "2" });
				get_room_state_810x();
				get_room_state_820x();
				get_room_state_830x();
				get_room_state_840x();
			}
		});
		btn_weiruzhu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.execSQL("insert into room" + room_numberString
						+ "(roomstate)values(?)", new String[] { "1" });
				get_room_state_810x();
				get_room_state_820x();
				get_room_state_830x();
				get_room_state_840x();
			}
		});
		btn_yiruzhu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.execSQL("insert into room" + room_numberString
						+ "(roomstate)values(?)", new String[] { "3" });
				get_room_state_810x();
				get_room_state_820x();
				get_room_state_830x();
				get_room_state_840x();
			}
		});
		final TextView tv_temp = (TextView) view.findViewById(R.id.tv_temp);
		final TextView tv_hum = (TextView) view.findViewById(R.id.tv_hum);
		final TextView tv_press = (TextView) view.findViewById(R.id.tv_press);
		final TextView tv_gas = (TextView) view.findViewById(R.id.tv_gas);
		final TextView tv_per = (TextView) view.findViewById(R.id.tv_per);
		final TextView tv_smo = (TextView) view.findViewById(R.id.tv_smo);
		final TextView tv_co = (TextView) view.findViewById(R.id.tv_co);
		final TextView tv_pm = (TextView) view.findViewById(R.id.tv_pm);
		final TextView tv_ill = (TextView) view.findViewById(R.id.tv_ill);
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
							tv_press.setText(getdata.getAirPressure());
						}
						if (!TextUtils.isEmpty(getdata.getCo2())) {
							co = Float.valueOf(getdata.getCo2());
							tv_co.setText(getdata.getCo2());
						}
						if (!TextUtils.isEmpty(getdata.getGas())) {
							gas = Float.valueOf(getdata.getGas());
							tv_gas.setText(getdata.getGas());
						}
						if (!TextUtils.isEmpty(getdata.getHumidity())) {
							hum = Float.valueOf(getdata.getHumidity());
							tv_hum.setText(getdata.getHumidity());
						}
						if (!TextUtils.isEmpty(getdata.getIllumination())) {
							ill = Float.valueOf(getdata.getIllumination());
							tv_ill.setText(getdata.getIllumination());
						}
						if (!TextUtils.isEmpty(getdata.getPM25())) {
							pm = Float.valueOf(getdata.getPM25());
							tv_pm.setText(getdata.getPM25());
						}
						if (!TextUtils.isEmpty(getdata.getSmoke())) {
							smo = Float.valueOf(DeviceBean.getSmoke());
							tv_smo.setText(getdata.getSmoke());
						}
						if (!TextUtils.isEmpty(getdata.getTemperature())) {
							temp = Float.valueOf(getdata.getTemperature());
							tv_temp.setText(getdata.getTemperature());
						}
						if (!TextUtils.isEmpty(getdata.getStateHumanInfrared())) {
							if (getdata.getStateHumanInfrared().equals(
									ConstantUtil.CLOSE)) {
								per = 0;
								tv_per.setText("无人");
							} else {
								per = 1;
								tv_per.setText("有人");
							}
						}

					}
				});
			}
		});

		builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		builder.show();
	}

	private void get_room_state_840x() {
		// TODO Auto-generated method stub
		for (int i = 1; i < 5; i++) {
			Cursor cursor = db.rawQuery(
					"select * from room840" + String.valueOf(i), null);
			if (cursor.getCount() != 0) {
				cursor.moveToLast();
				if (i == 1) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8401.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8401.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8401.setBackgroundColor(Color.RED);
					}
				}
				if (i == 2) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8402.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8402.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8402.setBackgroundColor(Color.RED);
					}
				}
				if (i == 3) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8403.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8403.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8403.setBackgroundColor(Color.RED);
					}
				}
				if (i == 4) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8404.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8404.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8404.setBackgroundColor(Color.RED);
					}
				}
			}
		}

	}

	private void get_room_state_830x() {
		// TODO Auto-generated method stub
		for (int i = 1; i < 5; i++) {
			Cursor cursor = db.rawQuery("select * from room830" + i, null);
			if (cursor.getCount() != 0) {
				cursor.moveToLast();
				if (i == 1) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8301.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8301.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8301.setBackgroundColor(Color.RED);
					}
				}
				if (i == 2) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8302.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8302.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8302.setBackgroundColor(Color.RED);
					}
				}
				if (i == 3) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8303.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8303.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8303.setBackgroundColor(Color.RED);
					}
				}
				if (i == 4) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8304.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8304.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8304.setBackgroundColor(Color.RED);
					}
				}
			}
		}

	}

	private void get_room_state_820x() {
		// TODO Auto-generated method stub
		for (int i = 1; i < 5; i++) {
			Cursor cursor = db.rawQuery("select * from room820" + i, null);
			if (cursor.getCount() != 0) {
				cursor.moveToLast();
				if (i == 1) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8201.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8201.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8201.setBackgroundColor(Color.RED);
					}
				}
				if (i == 2) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8202.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8202.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8202.setBackgroundColor(Color.RED);
					}
				}
				if (i == 3) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8203.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8203.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8203.setBackgroundColor(Color.RED);
					}
				}
				if (i == 4) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8204.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8204.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8204.setBackgroundColor(Color.RED);
					}
				}
			}
		}

	}

	private void get_room_state_810x() {
		// TODO Auto-generated method stub
		for (int i = 1; i < 5; i++) {
			Cursor cursor = db.rawQuery("select * from room810" + i, null);
			if (cursor.getCount() != 0) {
				cursor.moveToLast();
				if (i == 1) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8101.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8101.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8101.setBackgroundColor(Color.RED);
					}
				}
				if (i == 2) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8102.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8102.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8102.setBackgroundColor(Color.RED);
					}
				}
				if (i == 3) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8103.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8103.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8103.setBackgroundColor(Color.RED);
					}
				}
				if (i == 4) {
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("1")) {
						line_8104.setBackgroundColor(Color.GREEN);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("2")) {
						line_8104.setBackgroundColor(Color.GRAY);
					}
					if (cursor.getString(cursor.getColumnIndex("roomstate"))
							.equals("3")) {
						line_8104.setBackgroundColor(Color.RED);
					}
				}
			}
		}

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		line_8101 = (LinearLayout) view.findViewById(R.id.line_8101);
		line_8102 = (LinearLayout) view.findViewById(R.id.line_8102);
		line_8103 = (LinearLayout) view.findViewById(R.id.line_8103);
		line_8104 = (LinearLayout) view.findViewById(R.id.line_8104);

		line_8101.setOnClickListener(this);
		line_8102.setOnClickListener(this);
		line_8103.setOnClickListener(this);
		line_8104.setOnClickListener(this);

		line_8201 = (LinearLayout) view.findViewById(R.id.line_8201);
		line_8202 = (LinearLayout) view.findViewById(R.id.line_8202);
		line_8203 = (LinearLayout) view.findViewById(R.id.line_8203);
		line_8204 = (LinearLayout) view.findViewById(R.id.line_8204);

		line_8201.setOnClickListener(this);
		line_8202.setOnClickListener(this);
		line_8203.setOnClickListener(this);
		line_8204.setOnClickListener(this);

		line_8301 = (LinearLayout) view.findViewById(R.id.line_8301);
		line_8302 = (LinearLayout) view.findViewById(R.id.line_8302);
		line_8303 = (LinearLayout) view.findViewById(R.id.line_8303);
		line_8304 = (LinearLayout) view.findViewById(R.id.line_8304);

		line_8301.setOnClickListener(this);
		line_8302.setOnClickListener(this);
		line_8303.setOnClickListener(this);
		line_8304.setOnClickListener(this);

		line_8401 = (LinearLayout) view.findViewById(R.id.line_8401);
		line_8402 = (LinearLayout) view.findViewById(R.id.line_8402);
		line_8403 = (LinearLayout) view.findViewById(R.id.line_8403);
		line_8404 = (LinearLayout) view.findViewById(R.id.line_8404);

		line_8401.setOnClickListener(this);
		line_8402.setOnClickListener(this);
		line_8403.setOnClickListener(this);
		line_8404.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.line_8101:
			room_numberString = "8101";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8102:
			room_numberString = "8102";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8103:
			room_numberString = "8103";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8104:
			room_numberString = "8104";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;

		case R.id.line_8301:
			room_numberString = "8301";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8302:
			room_numberString = "8302";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8303:
			room_numberString = "8303";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8304:
			room_numberString = "8304";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;

		case R.id.line_8401:
			room_numberString = "8401";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8402:
			room_numberString = "8402";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8403:
			room_numberString = "8403";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8404:
			room_numberString = "8404";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;

		case R.id.line_8201:
			room_numberString = "8201";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8202:
			room_numberString = "8202";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8203:
			room_numberString = "8203";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;
		case R.id.line_8204:
			room_numberString = "8204";
			startActivity(new Intent(getActivity(), RoomControlActivity.class));
			break;

		default:
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			get_room_state_810x();
			get_room_state_820x();
			get_room_state_830x();
			get_room_state_840x();
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
}
