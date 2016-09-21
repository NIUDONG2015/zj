package com.itheima.zd.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itheima.zd.R;
import com.itheima.zd.adapter.BasicAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/23.
 */
public abstract class BaseListFragment<T> extends BaseFragment implements AdapterView.OnItemClickListener {
    protected ArrayList<T> list = new ArrayList<T>();//集合
    protected PullToRefreshListView refreshListView;
    protected ListView listView;

    protected BasicAdapter<T> adapter;

    @Override
    protected View getSuccessView() {
        refreshListView = (PullToRefreshListView) View.inflate(getActivity(), R.layout.ptr_listview, null);
        //1.设置刷新模式
        setRefreshMode();
        //  2.设置刷新的监听
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            //下拉刷新 和上拉加载更多都会走该方法
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //直接调用
                loadingPage.loadDataAndRefreshPage();
            }
        });
        //3.获取PullToRefreshListView内部包裹的ListView
        listView = refreshListView.getRefreshableView();
        setListView();
        //4.添加头部
        addHeader();
        //5.填充数据
        adapter = getAdapter();//需要动态获取Adapter
        listView.setAdapter(adapter);
        //6.设置条目点击事件
        listView.setOnItemClickListener(this);
        return refreshListView;
    }


    /**
     * 添加头部  zilei 需要 可以重写
     */
    protected void addHeader() {

    }

    /**
     * 获取Adapter
     *
     * @return
     */
    protected abstract BasicAdapter<T> getAdapter();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //默认空实现   让子类实现
    }

    /**
     * 设置和seleListView的divider和selector
     */
    protected void setListView() {
        listView.setDividerHeight(0);//去掉分割线
        listView.setSelector(android.R.color.transparent);//将listveiw的selector设置为透明

    }


    /**
     * 设置刷新模式 父类默认实现是两边都可刷新 子类可以重写
     */
    protected void setRefreshMode() {
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);//既可上拉也可下拉
    }

    /**
     * 检查如果是下拉刷新，则清空集合
     */
    protected void checkPullFromStart() {
        //1.获取数据
        if (refreshListView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
            //下拉刷新
            list.clear();//清空集合中的数据
        }
    }


    @Override
    protected Object requestData() {
        return null;
    }


}
