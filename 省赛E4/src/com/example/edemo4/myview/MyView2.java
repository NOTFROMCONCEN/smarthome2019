package com.example.edemo4.myview;

import java.util.ArrayList;
import java.util.List;

import com.example.edemo4.fragment.BaseActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class MyView2 extends View {
	Paint paint = new Paint();
	int number = 0;
	private int Xpoint = 400;
	private int Ypoint = 250;
	private int Xline = 300;
	private int Yline = 200;
	private int Xheight = 40;
	private int Yheight = 35;
	private List<String> Xlable = new ArrayList<String>();
	private String[] Ylable = new String[6];
	private List<Float> data = new ArrayList<Float>();
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0x1234) {
				MyView2.this.invalidate();
			}
		}
	};

	public MyView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		for (int i = 0; i < 6; i++) {
			Ylable[i] = String.valueOf(i * 20);
		}
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
					if (data.size() > 7) {
						data.remove(0);
						Xlable.remove(0);
					}
					if (BaseActivity.getdata != 0) {
						data.add(BaseActivity.getdata);
						Xlable.add(String.valueOf(BaseActivity.numberString
								+ number));
						number++;
					}

				}
			}
		}).start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setTextSize(16);
		canvas.drawLine(Xpoint - 20, Ypoint, Xpoint + Xline, Ypoint, paint);
		canvas.drawLine(Xpoint, Ypoint + 20, Xpoint, Ypoint - Yline, paint);
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(Xpoint, Ypoint - i * Yheight, Xpoint - 20, Ypoint
					- i * Yheight, paint);
			canvas.drawText(Ylable[i], Xpoint - 40, Ypoint - i * Yheight, paint);
		}
		for (int i = 1; i < 8; i++) {
			canvas.drawLine(Xpoint + i * Xheight, Ypoint, Xpoint + i * Xheight,
					Ypoint - 10, paint);
		}
		if (data.size() > 1) {
			for (int i = 1; i < data.size(); i++) {
				if (data.get(i) <= 100) {
					canvas.drawLine(Xpoint - i * Xheight + 10,
							Ypoint - data.get(i) * Yheight / 20, Xpoint - i
									* Xheight + 30, Ypoint - data.get(i)
									* Yheight / 20, paint);
				}
				if (data.get(i) > 100) {
					canvas.drawLine(Xpoint - i * Xheight + 10, Ypoint - 5
							* Yheight, Xpoint - i * Xheight + 30, Ypoint - 5
							* Yheight, paint);
				}
			}
		}
	}
}
