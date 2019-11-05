package com.example.bdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bdemo.R;

/**
 * 选择界面
 * 
 * @author A
 * 
 */
public class ChoseFragment extends Fragment implements OnClickListener {
	private TextView tv_base;
	private TextView tv_link;
	private TextView tv_draw;
	private TextView tv_mode;

	private ImageView iv_base;
	private ImageView iv_link;
	private ImageView iv_mode;
	private ImageView iv_draw;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_chose, container, false);
		initView(view);// 绑定
		return view;
	}

	/*
	 * 绑定控件
	 */
	private void initView(View view) {
		// TODO Auto-generated method stub
		tv_base = (TextView) view.findViewById(R.id.tv_base);
		tv_draw = (TextView) view.findViewById(R.id.tv_draw);
		tv_link = (TextView) view.findViewById(R.id.tv_link);
		tv_mode = (TextView) view.findViewById(R.id.tv_mode);
		iv_base = (ImageView) view.findViewById(R.id.iv_base);
		iv_draw = (ImageView) view.findViewById(R.id.iv_draw);
		iv_link = (ImageView) view.findViewById(R.id.iv_link);
		iv_mode = (ImageView) view.findViewById(R.id.iv_mode);

		tv_base.setOnClickListener(this);
		tv_draw.setOnClickListener(this);
		tv_link.setOnClickListener(this);
		tv_mode.setOnClickListener(this);
		// 设置默认显隐状态
		iv_base.setVisibility(View.VISIBLE);
		iv_draw.setVisibility(View.INVISIBLE);
		iv_mode.setVisibility(View.INVISIBLE);
		iv_link.setVisibility(View.INVISIBLE);
	}

	/*
	 * 响应点击事件 (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_base:// 基本
			iv_base.setVisibility(View.VISIBLE);
			iv_draw.setVisibility(View.INVISIBLE);
			iv_mode.setVisibility(View.INVISIBLE);
			iv_link.setVisibility(View.INVISIBLE);

			BarActivity.mViewPager.setCurrentItem(1);
			break;
		case R.id.tv_link:// 联动
			iv_base.setVisibility(View.INVISIBLE);
			iv_draw.setVisibility(View.INVISIBLE);
			iv_mode.setVisibility(View.INVISIBLE);
			iv_link.setVisibility(View.VISIBLE);
			BarActivity.mViewPager.setCurrentItem(2);
			break;
		case R.id.tv_mode:// 模式
			iv_base.setVisibility(View.INVISIBLE);
			iv_draw.setVisibility(View.INVISIBLE);
			iv_mode.setVisibility(View.VISIBLE);
			iv_link.setVisibility(View.INVISIBLE);
			BarActivity.mViewPager.setCurrentItem(3);
			break;
		case R.id.tv_draw:// 绘图
			iv_base.setVisibility(View.INVISIBLE);
			iv_draw.setVisibility(View.VISIBLE);
			iv_mode.setVisibility(View.INVISIBLE);
			iv_link.setVisibility(View.INVISIBLE);
			BarActivity.mViewPager.setCurrentItem(4);
			break;

		default:
			break;
		}
	}
}
