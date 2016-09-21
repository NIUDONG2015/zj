package com.itheima.zd.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.itheima.zd.R;
import com.itheima.zd.bean.AppInfo;
import com.itheima.zd.holder.AppInfoHolder;
import com.itheima.zd.holder.AppSafeHolder;
import com.itheima.zd.holder.AppScreenHolder;
import com.itheima.zd.holder.AppDesHolder;
import com.itheima.zd.http.Api;
import com.itheima.zd.http.HttpUtil;
import com.itheima.zd.ui.fragment.LoadingPage;
import com.itheima.zd.util.CommonUtil;
import com.itheima.zd.util.JsonUtil;

/**
 * Created by Administrator on 2016/6/25.
 */
public class AppDetailActivity extends ActionBarActivity {

    private String packageName;
    //app info 模块的控件
//    private ImageView iv_icon;
//    private RatingBar rb_star;
//    private TextView tv_name, tv_download_number, tv_version, tv_date, tv_size;
    private AppInfo appInfo;
    private LinearLayout holder_container;
    private AppInfoHolder appInfoHolder;
    private AppSafeHolder appSafeHolder;
    private AppScreenHolder appScreenHolder;
    private AppDesHolder appDesHolder;
    private ScrollView scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //1.接收数据
        packageName = getIntent().getStringExtra("packageName");
        //接收数据了么？
        //2.设置ActionBar

        setActionBar();

        //3.使用LoadingPage管理ActivityDev数据加载和界面切换
        LoadingPage loadingPage = new LoadingPage(this) {
            @Override
            public Object loadData() {
                return requestData();
            }
            @Override
            public View createSuccessView() {
                View view = View.inflate(AppDetailActivity.this, R.layout.activity_app_detail, null);
                holder_container = (LinearLayout) view.findViewById(R.id.holder_container);
                scrollView = (ScrollView) view.findViewById(R.id.scrollView2);
                //1.初始化app info模块
                appInfoHolder=new AppInfoHolder();
                holder_container.addView(appInfoHolder.getHolderView()); //暂无数据      将holder的数据添加到线性布局中
                //2.初始化 app safe模块
                appSafeHolder=new AppSafeHolder();
                holder_container.addView(appSafeHolder.getHolderView());//加进来

         /*       iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                rb_star = (RatingBar) view.findViewById(R.id.rb_star);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_download_number = (TextView) view.findViewById(R.id.tv_download_number);
                tv_version = (TextView) view.findViewById(R.id.tv_version);
                tv_date = (TextView) view.findViewById(R.id.tv_date);
                tv_size = (TextView) view.findViewById(R.id.tv_size);*/
                //3.初始化 app screen模块
                appScreenHolder=new AppScreenHolder();
                appScreenHolder.attachActivity(AppDetailActivity.this);
                holder_container.addView(appScreenHolder.getHolderView());//加进来
                // 4.初始化 app des模块
                appDesHolder =new AppDesHolder();
                holder_container.addView(appDesHolder.getHolderView());//加进来
                appDesHolder.setScrollView(scrollView);
                return view;
            }


        };
        setContentView(loadingPage);

    }

    protected Object requestData() {
        String url = String.format(Api.Detail, packageName);
        String result = HttpUtil.get(url);
        appInfo = JsonUtil.parseJsonToBean(result, AppInfo.class);
        if (appInfo != null) {
            //更新UI
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    updateUI();
                }


            });
        }
        return appInfo;

    }

    /**
     * 更新UId的方法
     */
    protected void updateUI() {
           //1.绑定app info的数据
        appInfoHolder.bindData(appInfo);   //空
        //2.绑定app safe模块的数据
        appSafeHolder.bindData(appInfo);
        //3.绑定app screen模块的数据
        appScreenHolder.bindData(appInfo);
        //4.绑定app des模块的数据
        appDesHolder.bindData(appInfo);

     /*   ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + appInfo.getIconUrl(), iv_icon, ImageLoaderOptions.round_options);
        tv_name.setText(appInfo.getName());
        rb_star.setRating(appInfo.getStars());
        tv_download_number.setText("下载 :" + appInfo.getDownloadNum());
        tv_version.setText("版本 :" + appInfo.getVersion());
        tv_date.setText("日期 :" + appInfo.getDate());
        tv_size.setText("大小 :" + Formatter.formatFileSize(this, appInfo.getSize()));*/
    }

    /**
     * 设置ActionBar
     */

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getString(R.string.app_detail));
        //显示home按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
