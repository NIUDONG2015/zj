package com.itheima.zd.adapter;


import com.itheima.zd.bean.AppInfo;
import com.itheima.zd.holder.BaseHolder;
import com.itheima.zd.holder.HomeHolder;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/6/20.
 */
public class HomeAdapter extends BasicAdapter<AppInfo> {
    public HomeAdapter(ArrayList<AppInfo> list) {
        super(list);
    }

    @Override
    protected BaseHolder<AppInfo> getHolder(int position) {
        return new HomeHolder();
    }


}
