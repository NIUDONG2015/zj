package com.itheima.zd.holder;

/**
 * Created by Administrator on 2016/6/21.
 */

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.zd.R;
import com.itheima.zd.bean.AppInfo;
import com.itheima.zd.global.GooglePlayApplication;
import com.itheima.zd.global.ImageLoaderOptions;
import com.itheima.zd.http.Api;
import com.nostra13.universalimageloader.core.ImageLoader;


public class HomeHolder extends BaseHolder<AppInfo>{
    ImageView iv_icon;
    RatingBar rb_star;
    TextView tv_name,tv_size,tv_des;
    //初始化HolderView
    public View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.adapter_home, null);
        iv_icon= (ImageView) view.findViewById(R.id.iv_icon);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        return view;
    }



    //绑定数据的方法
    public void bindData(AppInfo appInfo) {
        tv_name.setText(appInfo.getName());
        rb_star.setRating(appInfo.getStars());
        tv_size.setText(Formatter.formatFileSize(GooglePlayApplication.context,appInfo.getSize()));
        tv_des.setText(appInfo.getDes());

//        使用ImageLoader加载图片

        ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX+appInfo.getIconUrl(),iv_icon, ImageLoaderOptions.round_options);

    }


}
