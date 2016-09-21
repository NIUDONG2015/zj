package com.itheima.zd.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.itheima.zd.R;
import com.itheima.zd.adapter.BasicAdapter;
import com.itheima.zd.adapter.HomeAdapter;
import com.itheima.zd.adapter.HomeHeaderAdapter;
import com.itheima.zd.bean.AppInfo;
import com.itheima.zd.bean.Home;
import com.itheima.zd.com.orhanobut.logger.Logger;
import com.itheima.zd.http.Api;
import com.itheima.zd.http.HttpUtil;
import com.itheima.zd.ui.activity.AppDetailActivity;
import com.itheima.zd.util.CommonUtil;
import com.itheima.zd.util.JsonUtil;


public class HomeFragment extends BaseListFragment<AppInfo> {
    //    private PullToRefreshListView refreshListView;
//    private ListView listView;
//    private HomeAdapter homeAdapter;
//    private ArrayList<AppInfo> list = new ArrayList<AppInfo>();
    private ViewPager viewPager;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //让ViewPagerxuanzhong 下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            //重发消息
            handler.sendEmptyMessageDelayed(0, 2500);
//            LogUtil.e(this, "handlerESG");
            Logger.e("handlerESG", "Hello");
        }
    };

    @Override
    protected BasicAdapter<AppInfo> getAdapter() {
        return new HomeAdapter(list);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), AppDetailActivity.class);

//        LogUtil.e(this,"position"+position);
//        Log.d("HomeFragment点击","position"+position);
        Logger.e("HomeFragment点击", "position" + position);


        intent.putExtra("packageName", list.get(position - 2).getPackageName());//传数据
        startActivity(intent);


    }
    //    @Override
//    protected View getSuccessView() {
//
//        refreshListView = (PullToRefreshListView) View.inflate(getActivity(), R.layout.ptr_listview, null);
//        //1.设置 刷新模式
//        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);//既可上拉也可下拉
//        //  2.设置刷新的监听
//        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            //下拉刷新 和上拉加载更多都会走该方法
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
    //上拉加载更多
//                if (refreshListView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {
///*
//                   new Thread(){
//                       @Override
//                       public void run() {
//                 requestData();
//                       }
//                   }.start();*/
//                    /**
//                     * 请求页面  刷新数据
//                     */
//                    loadingPage.loadDataAndRefreshPage();
//
//                } else {//下拉刷新
//                    loadingPage.loadDataAndRefreshPage();
//                }
    //直接调用
//                loadingPage.loadDataAndRefreshPage();
//            }
//        });

//        //3.获取PullToRefreshListView内部包裹的ListView
//        listView = refreshListView.getRefreshableView();
//        listView.setDividerHeight(0);//去掉分割线
//        listView.setSelector(android.R.color.transparent);//将listveiw的selector设置为透明
//
//
//        //4.添加头部
//        addHeader();
//        //5.填充数据
//        homeAdapter = new HomeAdapter(list);
//        listView.setAdapter(homeAdapter);
//        return refreshListView;

  /*      TextView textView=new TextView(getActivity());
        textView.setText("哈喽");
        return textView;
*/
//    }


    /**
     * 添加头布局   ViewPager
     *
     * @return
     */
    public void addHeader() {
        View headeriew = View.inflate(getActivity(), R.layout.home_header, null);
        viewPager = (ViewPager) headeriew.findViewById(R.id.viewPager);
        //动态获取宽高比，去动态设定ViewPager的高度，让它的宽高比和图片保持一致
        //1.获取屏幕的宽度
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        //2.根据宽高比，动态的获取屏幕的高度  <宽高比为2.65>
        float height = width / 2.65f;
        //3.将高度设置给ViewPager   用自己的布局
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = (int) height;
        viewPager.setLayoutParams(params);

        listView.addHeaderView(headeriew);
    }


    //private int size=0;
    @Override
    protected Object requestData() {
        checkPullFromStart();

        String result = HttpUtil.get(Api.Home + list.size());
        //2.解析Json数据
        final Home home = JsonUtil.parseJsonToBean(result, Home.class);
        if (home != null) {
            list.addAll(home.getList());//更新数据


            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {

                    //由于只在第0页才有picture的数据，所以需要增加判断
                    if (home.getPicture() != null && home.getPicture().size() > 0) {
                        //给ViewPager页面设置数据//显示轮播图的数据
                        viewPager.setAdapter(new HomeHeaderAdapter(home.getPicture()));

                        //设置默认选中较大的一页
                        viewPager.setCurrentItem(home.getPicture().size() * 100000);
                        //发送延时消息
//                        handler.sendEmptyMessageDelayed(0,2500);

                    }
                    adapter.notifyDataSetChanged();//更新uI
                    //完成刷新
                    refreshListView.onRefreshComplete();

                }
            });

        }
        return home;
    }


    @Override
    public void onStart() {
        super.onStart();
        //重新发消息

        handler.sendEmptyMessageDelayed(0, 2500);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeMessages(0);
    }
}
