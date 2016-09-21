package com.itheima.zd.util;

import android.widget.Toast;

import com.itheima.zd.global.GooglePlayApplication;


public class ToastUtil {
	private static Toast toast;
	/**
	 * 强大的吐司，能够连续弹的吐司
	 * @param text
	 */
	public static void showToast(String text){
		if(toast==null){
			toast = Toast.makeText(GooglePlayApplication.context, text,0);
		}else {
			toast.setText(text);//如果不为空，则直接改变当前toast的文本
		}
		toast.show();
	}
}
