package com.itheima.zd.ui.fragment;

import com.google.gson.reflect.TypeToken;
import com.itheima.zd.adapter.BasicAdapter;
import com.itheima.zd.adapter.SubjectAdapter;
import com.itheima.zd.bean.Subject;
import com.itheima.zd.http.Api;
import com.itheima.zd.http.HttpUtil;
import com.itheima.zd.util.CommonUtil;
import com.itheima.zd.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class SubjectFragment extends BaseListFragment<Subject> {


	@Override
	protected BasicAdapter<Subject> getAdapter() {
		return new SubjectAdapter(list);  //zheli 需要写个适配器
	}

	@Override
	protected Object requestData() {
		String result= HttpUtil.get(Api.Subject+list.size());
		ArrayList<Subject> subjects= (ArrayList<Subject>) JsonUtil.parseJsonToList(result,new TypeToken<List<Subject>>(){}.getType());
		if (subjects!=null){
			list.addAll(subjects);

			CommonUtil.runOnUIThread(new Runnable() {
				@Override
				public void run() {
					adapter.notifyDataSetChanged();//genxin UI
					refreshListView.onRefreshComplete();//刷新数据
				}
			});
		}
		return subjects;//fanhui 本次集合数据
	}
}
