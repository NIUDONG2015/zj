package com.itheima.zd.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.zd.R;
import com.itheima.zd.bean.Subject;
import com.itheima.zd.global.GooglePlayApplication;
import com.itheima.zd.global.ImageLoaderOptions;
import com.itheima.zd.http.Api;
import com.itheima.zd.util.LogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/6/23.
 */
public class SubjectHolder extends BaseHolder<Subject> {   //空
    private ImageView iv_image;
    private TextView tv_bdes;

    @Override
    public View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.adapter_subject, null);
        tv_bdes = (TextView) view.findViewById(R.id.tv_bdes);
        iv_image = (ImageView) view.findViewById(R.id.iv_image);
        return view;
    }

    @Override
    public void bindData(Subject data) {
        LogUtil.e("data", "初始化数据:" + data);
        tv_bdes.setText(data.getDes());
        ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + data.getUrl(), iv_image, ImageLoaderOptions.fadein_options);

    }
    //空


}
