package com.itheima.zd.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/23.
 */
public class BasePagerAdapter<T> extends PagerAdapter {
    protected ArrayList<T> list;

    public BasePagerAdapter(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
