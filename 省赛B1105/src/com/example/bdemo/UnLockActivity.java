package com.example.bdemo;

import com.example.bdemo.fragment.BarActivity;
import com.example.bdemo.toast.DiyToast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/*
 * 滑动解锁界面
 */
public class UnLockActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 绑定界面
		setContentView(R.layout.activity_un_lock);
		// 绑定控件
		final SeekBar skBar = (SeekBar) findViewById(R.id.seekBar1);
		// 设置滑动状态
		skBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() == 100) {
					// 滑动栏满100跳转至基本界面
					startActivity(new Intent(getApplicationContext(),
							BarActivity.class));
					finish();
				} else {
					// 不满足给出提示
					skBar.setProgress(0);
					DiyToast.showToast(getApplicationContext(), "请完成滑动验证");
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_un_lock, menu);
		return true;
	}

}
