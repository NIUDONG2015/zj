package com.itheima.zd.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itheima.zd.R;
import com.itheima.zd.bean.AppInfo;
import com.itheima.zd.global.GooglePlayApplication;
import com.itheima.zd.global.ImageLoaderOptions;
import com.itheima.zd.http.Api;
import com.itheima.zd.ui.activity.ImageScaleActivity;
import com.itheima.zd.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/28.
 */
public class AppScreenHolder extends BaseHolder<AppInfo> {
    private LinearLayout ll_screen;
    private Activity activity;

    @Override
    public View initHolderView() {

        View view = View.inflate(GooglePlayApplication.context, R.layout.layout_detail_app_screen, null);
        ll_screen = (LinearLayout) view.findViewById(R.id.ll_screen);
        return view;
    }

    /**
     * 绑定Activity
     */
    public void attachActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        int width = CommonUtil.getDimens(R.dimen.dp90);
        int height = CommonUtil.getDimens(R.dimen.dp150);
        int margin = CommonUtil.getDimens(R.dimen.dp8);
        final ArrayList<String> screen = appInfo.getScreen();
        for (int i = 0; i < screen.size(); i++) {
            ImageView imageView = new ImageView(GooglePlayApplication.context);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = (i == 0 ? 0 : margin);
            imageView.setLayoutParams(params);
            //显示图片
            ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + screen.get(i), imageView, ImageLoaderOptions.fadein_options);
            //将 ImageView加入进来
            ll_screen.addView(imageView);

            final int temp = i;
            //添加点击事件
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //开启缩放大图的界面
//					Intent intent = new Intent(GooglePlayApplication.context,ImageScaleActivity.class);
//					//如果使用不是Activity的Context来开启Activity，那么需要该标记
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					intent.putStringArrayListExtra("urlList", screen);
//					GooglePlayApplication.context.startActivity(intent);

                    //第二种方案，使用Activity来开启
                    Intent intent = new Intent(activity, ImageScaleActivity.class);
                    intent.putStringArrayListExtra("urlList", screen);
                    intent.putExtra("currentItem", temp);
                    activity.startActivity(intent);
                }
            });
        }


    }
}
