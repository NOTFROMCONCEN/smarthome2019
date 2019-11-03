package com.example.cdemo;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.cdemo.mysql.MyDataBaseHelper;
import com.example.cdemo.toast.DiyToast;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TextView;

/**
 * 连接
 * 
 * @author A
 * 
 */
public class LinkStateActivity extends Activity {
	private TextView tv_a8;
	private TextView tv_smo;
	private TextView tv_hum;
	private TextView tv_press;
	private TextView tv_gas;
	private TextView tv_co;
	private TextView tv_temp;
	private TextView tv_ill;
	private TextView tv_pm;
	private TextView tv_per;
	MyDataBaseHelper dbHelper;
	SQLiteDatabase db;
	String online = "在线";
	String offline = "离线";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link_state);
		dbHelper = new MyDataBaseHelper(getApplicationContext(), "info.db",
				null, 2);
		db = dbHelper.getWritableDatabase();
		tv_a8 = (TextView) findViewById(R.id.tv_link_a8);
		tv_smo = (TextView) findViewById(R.id.tv_link_smo);
		tv_hum = (TextView) findViewById(R.id.tv_link_hum);
		tv_press = (TextView) findViewById(R.id.tv_link_press);
		tv_gas = (TextView) findViewById(R.id.tv_link_gas);
		tv_temp = (TextView) findViewById(R.id.tv_link_temp);
		tv_ill = (TextView) findViewById(R.id.tv_link_ill);
		tv_pm = (TextView) findViewById(R.id.tv_link_pm);
		tv_per = (TextView) findViewById(R.id.tv_link_per);
		tv_co = (TextView) findViewById(R.id.tv_link_co);
		SocketClient.getInstance().creatConnect();

		handler.post(timeRunnable);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_link_state, menu);
		return true;
	}

	/**
	 * 在线离线
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (IndexActivity.ill > 10) {
				tv_ill.setText("在线");
			} else if (IndexActivity.ill < 10) {
				tv_ill.setText("离线");
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

}
