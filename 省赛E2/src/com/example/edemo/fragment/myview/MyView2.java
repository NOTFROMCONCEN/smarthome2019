package com.example.edemo5.fargment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class MyView2 extends View {
	Paint paint = new Paint();
	private int Xpoint = 450;
	private int Ypoint = 300;
	private int Xheight = 30;
	private int Yheight = 20;
	private int Xline = 240;
	private int Yline = 130;
	private int num = 1;
	private List<String> Xlable = new ArrayList<String>();
	private List<Float> data = new ArrayList<Float>();
	private String[] Ylable = new String[6];
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
					}
					data.add(BaseFragment.getdata);
					Xlable.add(BaseFragment.number + String.valueOf(num));
					num++;
					handler.sendEmptyMessage(0x1234);
				}
			}
		}).start();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(2);
		canvas.drawLine(Xpoint, Ypoint + 20, Xpoint, Ypoint - Yline, paint);
		canvas.drawLine(Xpoint - 20, Ypoint, Xpoint + Xline, Ypoint, paint);
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(Xpoint, Ypoint - i * Yheight, Xpoint - 10, Ypoint
					- i * Yheight, paint);
			canvas.drawText(Ylable[i], Xpoint - 50, Ypoint - i * Yheight, paint);
		}
		for (int i = 1; i < 8; i++) {
			canvas.drawRect(Xpoint + i * Xheight, Ypoint - 100, Xpoint
					+ (i + 1) * Xheight - 10, Ypoint, paint);
		}
		for (int i = 1; i < 8; i++) {
			canvas.drawLine(Xpoint + i * Xheight, Ypoint, Xpoint + i * Xheight,
					Ypoint - 6, paint);
		}
		for (int i = 0; i < 8; i++) {
			canvas.drawLine(Xpoint + i * Xheight - 400, Ypoint - 40, Xpoint
					+ (i + 1) * Xheight - 400, Ypoint - 40, paint);
			canvas.drawLine(Xpoint + i * Xheight - 400, Ypoint + 60, Xpoint
					+ (i + 1) * Xheight - 400, Ypoint + 60, paint);

			canvas.drawLine(Xpoint + i * Xheight - 400, Ypoint + 10, Xpoint
					+ (i + 1) * Xheight - 400, Ypoint + 10, paint);

		}
		for (int i = 0; i < 9; i++) {
			canvas.drawLine(Xpoint + i * Xheight - 400, Ypoint + 63, Xpoint
					+ (i + 1) * Xheight - 430, Ypoint - 43, paint);
		}
		if (data.size() > 1) {
			for (int i = 1; i < data.size(); i++) {
				if (data.get(i) > 100) {
					canvas.drawRect(Xpoint + i * Xheight, Ypoint - 5 * Yheight,
							Xpoint + (i + 1) * Xheight - 10, Ypoint, paint);
				}
				if (data.get(i) <= 100) {
					canvas.drawRect(Xpoint + i * Xheight, Ypoint - data.get(i)
							* Yheight / 20, Xpoint + (i + 1) * Xheight - 10,
							Ypoint, paint);
				}
				canvas.drawText(Xlable.get(i), Xpoint + i * Xheight - 400,
						Ypoint, paint);
				canvas.drawText(data.get(i).toString(), Xpoint + i * Xheight
						- 400, Ypoint + 40, paint);
			}
		}
	}
}
