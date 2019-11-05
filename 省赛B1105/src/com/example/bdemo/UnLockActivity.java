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
 * ������������
 */
public class UnLockActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// �󶨽���
		setContentView(R.layout.activity_un_lock);
		// �󶨿ؼ�
		final SeekBar skBar = (SeekBar) findViewById(R.id.seekBar1);
		// ���û���״̬
		skBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() == 100) {
					// ��������100��ת����������
					startActivity(new Intent(getApplicationContext(),
							BarActivity.class));
					finish();
				} else {
					// �����������ʾ
					skBar.setProgress(0);
					DiyToast.showToast(getApplicationContext(), "����ɻ�����֤");
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
