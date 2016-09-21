package com.itheima.zd.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.itheima.zd.global.GooglePlayApplication;
import com.itheima.zd.global.ImageLoaderOptions;
import com.itheima.zd.http.Api;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/23.
 */
public class HomeHeaderAdapter extends BasePagerAdapter<String> {
    //构造传过来参数
    public HomeHeaderAdapter(ArrayList<String> list) {
        super(list);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(GooglePlayApplication.context);
        //显示图片
        ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + list.get(position%list.size()), imageView, ImageLoaderOptions.fadein_options);
        //需要将imageView添加到container
        container.addView(imageView);
        return imageView;
    }
}
