package com.example.ademo.activity;

import com.example.ademo.R;
import com.example.ademo.R.layout;
import com.example.ademo.R.menu;
import com.example.ademo.fragment.ControlActivity;
import com.example.ademo.fragment.DrawActivity;
import com.example.ademo.fragment.LinkActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class RoomDrawActivity extends Activity {
	private TextView tv_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_draw);
		tv_number = (TextView) findViewById(R.id.tv_draw_number);
		tv_number.setText("·¿¼äºÅ£º" + DrawActivity.room_numberString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_room_draw, menu);
		return true;
	}

}
