package com.example.ademo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.ademo.R;
import com.example.ademo.toast.DiyToast;

public class BarActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	List<Fragment> faFragments = new ArrayList<Fragment>();
	Button btn_base;
	Button btn_control;
	Button btn_link;
	Button btn_draw;

	Button btn_show_keruzhu;
	Button btn_show_weidasao;
	Button btn_show_yiruzhu;

	View view_1;
	View view_2;
	View view_3;
	View view_4;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar);
		btn_show_keruzhu = (Button) findViewById(R.id.btn_show_keruzhu);
		btn_show_weidasao = (Button) findViewById(R.id.btn_show_weidasao);
		btn_show_yiruzhu = (Button) findViewById(R.id.btn_show_yiruzhu);

		btn_show_keruzhu.setBackgroundColor(Color.GREEN);
		btn_show_weidasao.setBackgroundColor(Color.GRAY);
		btn_show_yiruzhu.setBackgroundColor(Color.RED);

		faFragments.add(new BaseActivity());
		faFragments.add(new ControlActivity());
		faFragments.add(new LinkActivity());
		faFragments.add(new DrawActivity());

		btn_base = (Button) findViewById(R.id.btn_base);
		btn_control = (Button) findViewById(R.id.btn_control);
		btn_draw = (Button) findViewById(R.id.btn_temp);
		btn_link = (Button) findViewById(R.id.btn_link);

		view_1 = (View) findViewById(R.id.View1);
		view_2 = (View) findViewById(R.id.View2);
		view_3 = (View) findViewById(R.id.View3);
		view_4 = (View) findViewById(R.id.View4);
		view_1.setBackgroundColor(Color.CYAN);
		view_2.setBackgroundColor(Color.WHITE);
		view_3.setBackgroundColor(Color.WHITE);
		view_4.setBackgroundColor(Color.WHITE);

		ControlUtils.setUser("bizideal", "123456", "18.1.10.7");
		SocketClient.getInstance().creatConnect();
		SocketClient.getInstance().login(new LoginCallback() {

			@Override
			public void onEvent(final String arg0) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (arg0.equals(ConstantUtil.SUCCESS)) {
							DiyToast.showToast(getApplicationContext(), "组网成功");
						} else {
							DiyToast.showToast(getApplicationContext(), "组网失败");
						}
					}
				});
			}
		});

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), faFragments);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(5);
		btn_base.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(0);
			}
		});
		btn_control.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(1);
			}
		});
		btn_draw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(3);
			}
		});
		btn_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(2);
			}
		});

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						switch (position) {
						case 0:
							view_1.setBackgroundColor(Color.CYAN);
							view_2.setBackgroundColor(Color.WHITE);
							view_3.setBackgroundColor(Color.WHITE);
							view_4.setBackgroundColor(Color.WHITE);
							break;
						case 1:
							view_1.setBackgroundColor(Color.WHITE);
							view_2.setBackgroundColor(Color.CYAN);
							view_3.setBackgroundColor(Color.WHITE);
							view_4.setBackgroundColor(Color.WHITE);
							break;
						case 2:
							view_1.setBackgroundColor(Color.WHITE);
							view_2.setBackgroundColor(Color.WHITE);
							view_3.setBackgroundColor(Color.CYAN);
							view_4.setBackgroundColor(Color.WHITE);
							break;
						case 3:
							view_1.setBackgroundColor(Color.WHITE);
							view_2.setBackgroundColor(Color.WHITE);
							view_3.setBackgroundColor(Color.WHITE);
							view_4.setBackgroundColor(Color.CYAN);
							break;

						default:
							break;
						}
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bar, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		List<Fragment> fragments = new ArrayList<Fragment>();

		public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return fragments.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments.get(arg0);
		}
	}

}
