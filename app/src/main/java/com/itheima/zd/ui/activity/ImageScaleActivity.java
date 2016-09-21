package com.itheima.zd.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.itheima.zd.R;
import com.itheima.zd.adapter.ImageScaleAdapter;
import com.itheima.zd.ui.widget.HackyViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ImageScaleActivity extends Activity{
//    private ViewPager viewPager;//骇客
    private HackyViewPager viewPager;//骇客  非常规操作
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale);
        viewPager= (HackyViewPager) findViewById(R.id.viewPager);
        //1.获取集合
        ArrayList<String> urlList = getIntent().getStringArrayListExtra("urlList");
        int currentItem = getIntent().getIntExtra("currentItem", 0);
        //2.填充数据
        ImageScaleAdapter adapter = new ImageScaleAdapter(urlList);
        viewPager.setAdapter(adapter);
        //设置默认选中的页
        viewPager.setCurrentItem(currentItem);

    }
}
