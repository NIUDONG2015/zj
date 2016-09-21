package com.itheima.zd.holder;

import android.view.View;
import android.widget.TextView;

import com.itheima.zd.R;
import com.itheima.zd.global.GooglePlayApplication;

/**
 * Created by Administrator on 2016/6/24.
 */
public class CategoryTitleHolder extends BaseHolder<Object> {
    private TextView tv_title;
    @Override
    public View initHolderView() {

        View view=View.inflate(GooglePlayApplication.context, R.layout.adapter_category_title,null);
        tv_title= (TextView) view.findViewById(R.id.tv_title);

        return view;
    }

    @Override
    public void bindData(Object data) {
        tv_title.setText((String)data);
    }
}
