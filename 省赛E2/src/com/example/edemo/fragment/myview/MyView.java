package com.example.edemo.fragment.myview;

import java.util.ArrayList;
import java.util.List;

import com.example.edemo.fragment.BaseActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
	Paint paint = new Paint();
	int Xpoint = 450;
	int Ypoint = 250;
	int Xheight = 35;
	int Yheight = 25;
	int Xline = 300;
	int Yline = 160;
	int num = 1;
	private List<Float> data = new ArrayList<Float>();
	private List<String> Xlable = new ArrayList<String>();
	private String[] Ylable = new String[6];
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
						Xlable.remove(0);
						data.remove(0);
					}
					if (BaseActivity.getdata != 0) {
						data.add(BaseActivity.getdata);
						Xlable.add(String.valueOf(BaseActivity.number + "0"
								+ num));
						num++;
					}
					handler.sendEmptyMessage(0x1234);
				}
			}
		}).start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		paint.setTextSize(16);
		paint.setColor(Color.WHITE);

		canvas.drawLine(Xpoint - 20, Ypoint, Xpoint + Xline, Ypoint, paint);
		canvas.drawLine(Xpoint, Ypoint + 20, Xpoint, Ypoint - Yline, paint);

		for (int i = 0; i < 6; i++) {
			canvas.drawLine(Xpoint, Ypoint - i * Yheight, Xpoint - 15, Ypoint
					- i * Yheight, paint);
			canvas.drawText(Ylable[i], Xpoint - 30, Ypoint - i * Yheight, paint);
		}
		for (int i = 1; i < 8; i++) {
			canvas.drawLine(Xpoint + i * Xheight, Ypoint, Xpoint + i * Xheight,
					Ypoint - 10, paint);
		}
		if (data.size() > 1) {
			for (int i = 1; i < data.size(); i++) {
				if (data.get(i) <= 100 && data.get(i) <= 100) {
					canvas.drawLine(Xpoint + i * Xheight,
							Ypoint - data.get(i - 1) * Yheight / 25, Xpoint
									+ (i + 1) * Xheight, Ypoint - data.get(i)
									* Yheight / 25, paint);
				}
				if (data.get(i) > 100 && data.get(i) <= 100) {
					canvas.drawLine(Xpoint + i * Xheight, Ypoint - 5 * Yheight
							/ 20, Xpoint + (i + 1) * Xheight,
							Ypoint - data.get(i) * Yheight / 25, paint);
				}
				if (data.get(i) <= 100 && data.get(i) > 100) {
					canvas.drawLine(Xpoint + i * Xheight,
							Ypoint - data.get(i - 1) * Yheight / 25, Xpoint
									+ (i + 1) * Xheight, Ypoint - 5 * Yheight
									/ 20, paint);
				}
				if (data.get(i) > 100 && data.get(i) > 100) {
					canvas.drawLine(Xpoint + i * Xheight, Ypoint - 5 * Yheight,
							Xpoint + (i + 1) * Xheight, Ypoint - 5 * Yheight,
							paint);
				}
				canvas.drawText(Xlable.get(i), Xpoint + i * Xheight,
						Ypoint + 10, paint);
				canvas.drawText(data.get(i).toString(), Xpoint - 440 + i
						* Xheight + 20, Ypoint + 10, paint);
			}
		}
	}
}
