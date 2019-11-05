package com.example.bdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.bdemo.MyDataBaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

	Paint paint = new Paint();
	private int Xpoint = 50;
	private int Ypoint = 300;
	private int Yheight = 40;
	private int Xheight = 90;
	private int Xline = 400;
	private int num = 1;
	MyDataBaseHelper dbHelper;
	SQLiteDatabase db;
	private List<String> Xlable = new ArrayList<String>();
	private List<Float> data_ill = new ArrayList<Float>();
	private List<Float> data_temp = new ArrayList<Float>();
	private String[] Ylable = new String[5];
	Handler hand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0x1234) {
				MyView.this.invalidate();
			}
		}
	};

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		for (int i = 0; i < 5; i++) {
			Ylable[i] = String.valueOf(i * 100);
		}
		dbHelper = new MyDataBaseHelper(getContext(), "temp_humm_databases.db",
				null, 2);
		db = dbHelper.getWritableDatabase();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}
					if (data_ill.size() > 5) {
						data_ill.remove(0);
						data_temp.remove(0);
						Xlable.remove(0);
					}
					db.execSQL("insert into databases(temp,ill)values(?,?)",
							new String[] { String.valueOf(BaseFragment.temp),
									String.valueOf(BaseFragment.ill) });
					data_ill.add(BaseFragment.ill);
					data_temp.add(BaseFragment.temp);
					Xlable.add(String.valueOf(num));
					num++;
					hand.sendEmptyMessage(0x1234);
				}
			}
		}).start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setTextSize(16);
		paint.setStyle(Style.STROKE);
		for (int i = 0; i < 5; i++) {
			canvas.drawLine(Xpoint, Ypoint - i * Yheight, Xpoint + Xline,
					Ypoint - i * Yheight, paint);
			canvas.drawText(Ylable[i], Xpoint - 30, Ypoint - i * Yheight, paint);
		}
		for (int i = 0; i < 4; i++) {

		}
		if (data_ill.size() > 1) {
			for (int i = 0; i < data_ill.size(); i++) {
				paint.setAntiAlias(true);
				paint.setColor(Color.BLUE);
				paint.setTextSize(16);
				paint.setStyle(Style.FILL);
				if (data_ill.get(i) <= 400) {
					canvas.drawRect(Xpoint + i * Xheight + 60, Ypoint
							- data_ill.get(i) * Yheight / 100, Xpoint + i
							* Xheight + 90, Ypoint, paint);
				}
				if (data_ill.get(i) > 400) {
					canvas.drawRect(Xpoint + i * Xheight + 60, Ypoint - 4
							* Yheight, Xpoint + i * Xheight + 90, Ypoint, paint);
				}
				canvas.drawText(Xlable.get(i), Xpoint + i * Xheight + 30,
						Ypoint + 20, paint);
			}
		}
		if (data_temp.size() > 1) {
			for (int i = 0; i < data_temp.size(); i++) {
				paint.setAntiAlias(true);
				paint.setColor(Color.RED);
				paint.setTextSize(16);
				paint.setStyle(Style.FILL);
				if (data_temp.get(i) <= 400) {
					canvas.drawRect(Xpoint + i * Xheight + 20, Ypoint
							- data_temp.get(i) * Yheight / 100, Xpoint + i
							* Xheight + 50, Ypoint, paint);
				}
				if (data_temp.get(i) > 400) {
					canvas.drawRect(Xpoint + i * Xheight + 20, Ypoint - 4
							* Yheight, Xpoint + i * Xheight + 50, Ypoint, paint);
				}
				canvas.drawText(Xlable.get(i), Xpoint + i * Xheight + 30,
						Ypoint + 20, paint);
			}
		}
	}
}
