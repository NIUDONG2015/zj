package com.itheima.zd.adapter;

import android.view.ViewGroup;

import com.itheima.zd.global.GooglePlayApplication;
import com.itheima.zd.global.ImageLoaderOptions;
import com.itheima.zd.http.Api;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ImageScaleAdapter extends BasePagerAdapter<String> {
    public ImageScaleAdapter(ArrayList<String> list) {
        super(list);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        ImageView imageView = new ImageView(GooglePlayApplication.context);
        PhotoView imageView = new PhotoView(GooglePlayApplication.context);

        ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + list.get(position), imageView, ImageLoaderOptions.fadein_options);//数组越界异常

        //将ImageView加入到ViewPager中
        container.addView(imageView);
        return imageView;
    }
}
