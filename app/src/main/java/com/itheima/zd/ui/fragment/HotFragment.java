package com.itheima.zd.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.itheima.zd.R;
import com.itheima.zd.http.Api;
import com.itheima.zd.http.HttpUtil;
import com.itheima.zd.ui.widget.FlowLayout;
import com.itheima.zd.util.ColorUtils;
import com.itheima.zd.util.CommonUtil;
import com.itheima.zd.util.DrawableUtil;
import com.itheima.zd.util.JsonUtil;
import com.itheima.zd.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class HotFragment extends BaseFragment {

    private FlowLayout flowLayout;
    private ScrollView scrollView;
    private int vPadding, hPadding;

    @Override
    protected View getSuccessView() {
        //添加可以滑动的ScrollView
        scrollView = new ScrollView(getActivity());
        flowLayout = new FlowLayout(getActivity());
        vPadding = CommonUtil.getDimens(R.dimen.dp6);
        hPadding = CommonUtil.getDimens(R.dimen.dp9);
        //1.设置padding值
        int padding = CommonUtil.getDimens(R.dimen.dp15);
        flowLayout.setPadding(padding, padding, padding, padding);
        //2.设置水平间距和垂直间距
        flowLayout.setHorizontalSpacing(padding);
        flowLayout.setVerticalSpacing(padding);
        scrollView.addView(flowLayout);
        return scrollView;
    }

    @Override
    protected Object requestData() {
        String result = HttpUtil.get(Api.Hot);
        final ArrayList<String> list = (ArrayList<String>) JsonUtil.parseJsonToList(result, new TypeToken<List<String>>() {
        }.getType());
        if (list != null) {
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {

                    //给FlowLayout添加对应的子view
                    for (int i = 0; i < list.size(); i++) {
                        final TextView textView = new TextView(getActivity());
                        textView.setTextSize(16);
                        textView.setGravity(Gravity.CENTER);
                        textView.setText(list.get(i));
                        textView.setTextColor(Color.WHITE);
                        textView.setPadding(hPadding, vPadding, hPadding, vPadding);
//                        textView.setBackgroundColor(ColorUtils.randomColor());

                        Drawable pressed = DrawableUtil.generateDrawable(ColorUtils.randomColor(), hPadding);
                        Drawable nomal = DrawableUtil.generateDrawable(ColorUtils.randomColor(), hPadding);
                        textView.setBackgroundDrawable(DrawableUtil.generateSelector(pressed, nomal));
                        flowLayout.addView(textView);
                        //设置点击事件
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showToast(textView.getText().toString());
                            }
                        });
                    }
                }
            });
        }
        return list;
    }
}
