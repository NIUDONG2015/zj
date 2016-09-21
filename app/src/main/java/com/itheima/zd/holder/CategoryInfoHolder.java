package com.itheima.zd.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.zd.R;
import com.itheima.zd.bean.CategoryInfo;
import com.itheima.zd.global.GooglePlayApplication;
import com.itheima.zd.global.ImageLoaderOptions;
import com.itheima.zd.http.Api;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/6/24.
 */
public class CategoryInfoHolder extends BaseHolder<Object> {
    private ImageView iv_image1, iv_image2, iv_image3;
    private TextView tv_name1, tv_name2, tv_name3;

    @Override
    public View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.adapter_category_info, null);
        iv_image1 = (ImageView) view.findViewById(R.id.iv_image1);
        iv_image2 = (ImageView) view.findViewById(R.id.iv_image2);
        iv_image3 = (ImageView) view.findViewById(R.id.iv_image3);
        tv_name1 = (TextView) view.findViewById(R.id.tv_name1);
        tv_name2 = (TextView) view.findViewById(R.id.tv_name2);
        tv_name3 = (TextView) view.findViewById(R.id.tv_name3);
        return view;
    }

    @Override
    public void bindData(Object data) {
        CategoryInfo info = (CategoryInfo) data;
        //显示第一个方格的数据
        tv_name1.setText(info.getName1());
        ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + info.getUrl1(), iv_image1, ImageLoaderOptions.round_options);

        //由于第2个和第3个可能木有，所以需要判断
        if (!TextUtils.isEmpty(info.getName2())) {

            //由于是复用的view，所以不为空的时候需要设置为可见
            ((ViewGroup) tv_name2.getParent()).setVisibility(View.VISIBLE);
            tv_name2.setText(info.getName2());
            ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + info.getUrl2(), iv_image2, ImageLoaderOptions.round_options);

        } else {
            ((ViewGroup) tv_name2.getParent()).setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(info.getName3())) {
            ((ViewGroup) tv_name3.getParent()).setVisibility(View.VISIBLE);
            tv_name3.setText(info.getName3());
            ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + info.getUrl3(), iv_image3, ImageLoaderOptions.round_options);

        } else {
            ((ViewGroup) tv_name3.getParent()).setVisibility(View.INVISIBLE);
        }
    }
}
