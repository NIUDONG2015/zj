package com.itheima.zd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itheima.zd.R;
import com.itheima.zd.ui.fragment.FragmentFactory;
import com.itheima.zd.util.CommonUtil;


public class MainPagerAdapter extends FragmentPagerAdapter {
	private String[] tabs;
	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
		tabs = CommonUtil.getStringArray(R.array.tab_names);
	}
	/**
	 * 返回每一页需要的fragment
	 */
	@Override
	public Fragment getItem(int position) {
		return FragmentFactory.create(position);
	}

	@Override
	public int getCount() {
		return tabs.length;
	}
	/**
	 * 返回每一页对应的title
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		return tabs[position];
	}
//	/**
//	 * 根据不同的位置返回不同的图片
//	 */
//	@Override
//	public int getPageIconResId(int position) {
//		return R.drawable.ic_download;
//	}

}
