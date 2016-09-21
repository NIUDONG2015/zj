package com.itheima.zd.holder;

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

/**
 * Created by Administrator on 2016/6/26.
 */
public class AppInfoHolder  extends BaseHolder<AppInfo>{
    private ImageView iv_icon;
    private RatingBar rb_star;
    private TextView tv_name, tv_download_number, tv_version, tv_date, tv_size;
    private AppInfo appInfo;
    @Override
    public View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.layout_detail_app_info, null);
        //1.初始化app info模块
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_download_number = (TextView) view.findViewById(R.id.tv_download_number);
        tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        //2.初始化 app safe模块
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + appInfo.getIconUrl(), iv_icon, ImageLoaderOptions.round_options);
        tv_name.setText(appInfo.getName());
        rb_star.setRating(appInfo.getStars());
        tv_download_number.setText("下载 :" + appInfo.getDownloadNum());
        tv_version.setText("版本 :" + appInfo.getVersion());
        tv_date.setText("日期 :" + appInfo.getDate());
        tv_size.setText("大小 :" + Formatter.formatFileSize(GooglePlayApplication.context, appInfo.getSize()));
    }
}
