package com.itheima.zd.ui.fragment;

import com.google.gson.reflect.TypeToken;
import com.itheima.zd.adapter.BasicAdapter;
import com.itheima.zd.adapter.HomeAdapter;
import com.itheima.zd.bean.AppInfo;
import com.itheima.zd.http.Api;
import com.itheima.zd.http.HttpUtil;
import com.itheima.zd.util.CommonUtil;
import com.itheima.zd.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * app应用界面
 */
public class AppFragment extends BaseListFragment<AppInfo> {


    @Override
    protected BasicAdapter<AppInfo> getAdapter() {
        return new HomeAdapter(list);  //内容都一样
    }

    //请求数据
    @Override
    protected Object requestData() {
        String result = HttpUtil.get(Api.App + list.size());
        ArrayList<AppInfo> appInfos = (ArrayList<AppInfo>) JsonUtil.parseJsonToList(result, new TypeToken<List<AppInfo>>() {
        }.getType());

        if (appInfos != null) {
            list.addAll(appInfos);//更新数据
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    //更新UI
                    adapter.notifyDataSetChanged();
                    //完成刷新
                    refreshListView.onRefreshComplete();
                }
            });

        }
        return appInfos;
    }
}
