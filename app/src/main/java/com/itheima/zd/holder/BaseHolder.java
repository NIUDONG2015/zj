package com.itheima.zd.holder;

import android.view.View;

/**
 * Created by Administrator on 2016/6/21.
 */
public abstract class BaseHolder<T> {
    public View holderView; //一开始将convertView转移到holder中用一个变量表示
    public BaseHolder() {
        //1.初始化HolderView
        holderView = initHolderView();//需要HolderView
        //2.设置Tag
        holderView.setTag(this);
    }
    public abstract View initHolderView();
    public abstract void bindData(T data);
    //获取holderView
    public View getHolderView() {
        return holderView;
    }


}
