package com.example.ademo.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.ademo.fragment.DrawActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

	private int Xpoint = 10;
	private int Ypoint = 350;
	private int Xline = 720;
	private int Yline = 250;
	private int Xheight = 90;
	private int Yheight = 50;
	private List<Float> data = new ArrayList<Float>();
	Handler handler = new Handler() {
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
					}
					data.add(DrawActivity.temp);
					handler.sendEmptyMessage(0x1234);
				}
			}
		}).start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(5);
		paint.setColor(Color.BLACK);
		canvas.drawLine(Xpoint + 70, Ypoint, Xpoint + Xline, Ypoint, paint);
		canvas.drawLine(Xpoint + 90, Ypoint + 20, Xpoint + 90, Ypoint - Yline,
				paint);
		if (data.size() > 1) {
			for (int i = 1; i < data.size(); i++) {
				if (data.get(i - 1) > 15) {
					Paint paint1 = new Paint();
					paint1.setAntiAlias(true);
					paint1.setStrokeWidth(5);
					paint1.setColor(Color.RED);
					canvas.drawLine(Xpoint + i * Xheight,
							Ypoint - data.get(i - 1) * Yheight / 20, Xpoint
									+ (i + 1) * Xheight, Ypoint - data.get(i)
									* Yheight / 20, paint1);

				}
				if (data.get(i - 1) <= 15) {
					Paint paint2 = new Paint();
					paint2.setAntiAlias(true);
					paint2.setStrokeWidth(5);
					paint2.setColor(Color.BLACK);
					canvas.drawLine(Xpoint + i * Xheight,
							Ypoint - data.get(i - 1) * Yheight / 20, Xpoint
									+ (i + 1) * Xheight, Ypoint - data.get(i)
									* Yheight / 20, paint2);

				}
			}
		}
	}
}
