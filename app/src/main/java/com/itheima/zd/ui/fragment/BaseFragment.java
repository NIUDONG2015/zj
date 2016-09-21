package com.itheima.zd.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.zd.util.CommonUtil;


public abstract class BaseFragment extends Fragment {
	protected LoadingPage loadingPage;//注意：修饰符不能是private
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(loadingPage==null){
			loadingPage = new LoadingPage(getActivity()) {
				@Override
				public Object loadData() {
					return requestData();
				}
				@Override
				public View createSuccessView() {
					return getSuccessView();
				}
			};
		}else {
			//需要拿loadingPage的父View（NoSaveStateFramelayout）去移除它自己
//			LogUtil.e(this.getClass().getSimpleName(), "loadingPage已经不为空了: parent: "+loadingPage.getParent().getClass().getSimpleName());
			CommonUtil.removeSelfFromParent(loadingPage);  // 报错了牛栋
			//但是呢，在Android5.0之后的FragmentManager已经不会在Fragment的view外边包裹一层，这意味着不用移除也不会报错;
		}
		return loadingPage;
	}
	/**
	 * 获取每个子类的successView
	 * @return
	 */
	protected abstract View getSuccessView();
	/**
	 * 获取每个子类的数据
	 * @return
	 */
	protected abstract Object requestData();
}
