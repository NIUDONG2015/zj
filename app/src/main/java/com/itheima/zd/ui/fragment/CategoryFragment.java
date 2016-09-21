package com.itheima.zd.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.itheima.zd.R;
import com.itheima.zd.adapter.CategoryAdapter;
import com.itheima.zd.bean.Category;
import com.itheima.zd.bean.CategoryInfo;
import com.itheima.zd.http.Api;
import com.itheima.zd.http.HttpUtil;
import com.itheima.zd.util.CommonUtil;
import com.itheima.zd.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment {
private CategoryAdapter adapter;
    private ListView listView;
    private ArrayList<Object> list = new ArrayList<Object>();

    @Override
    protected View getSuccessView() {   //不用下拉刷新
        listView = (ListView) View.inflate(getActivity(), R.layout.listview, null);
        return listView;
    }

    @Override
    protected Object requestData() {
        String result = HttpUtil.get(Api.Category);
        ArrayList<Category> categories = (ArrayList<Category>) JsonUtil.parseJsonToList(result, new TypeToken<List<Category>>() {
        }.getType());
        if (list != null) {
            //将categories中的title和CategoryInfo放入同一个list中
            for (Category category : categories) {
                //1.强title放入list中
                list.add(category.getTitle());
                //2.强infos中的CategoryInfo 放入list中
                ArrayList<CategoryInfo> infos = category.getInfos();
                list.addAll(infos);
            }

            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    //更新UI 给界面设置数据适配器
                    listView.setAdapter(new CategoryAdapter(list));
                }
            });

        }
        return list;
    }
}
