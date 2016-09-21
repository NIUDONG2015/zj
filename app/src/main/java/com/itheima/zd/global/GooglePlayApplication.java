package com.itheima.zd.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.itheima.zd.com.orhanobut.logger.Logger;


public class GooglePlayApplication extends Application {
    public static Context context;
    public static Handler mainHandler;//主线程的handler

    /**
     * app的入口函数
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //1.初始化Context,Android三大Context:Application,Activity,Service
        context = this;
        //2.初始化mainHandler
        mainHandler = new Handler();
//        Logger.init();
        Logger.init("tag");

//		new Thread(){
//			public void run() {
//				//在子线程创建handler
//				mainHandler = new Handler();
//				Looper.prepare();//创建轮询器
//				Looper.loop();//开启轮询器
//				mainHandler.sendEmptyMessage(0);
//			};
//		}.start();

        //初始化ImageLoader
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }
}
