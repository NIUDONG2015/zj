package com.itheima.zd.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.itheima.zd.R;
import com.itheima.zd.http.Api;
import com.itheima.zd.http.HttpUtil;
import com.itheima.zd.ui.widget.randomlayout.StellarMap;
import com.itheima.zd.util.ColorUtils;
import com.itheima.zd.util.CommonUtil;
import com.itheima.zd.util.JsonUtil;
import com.itheima.zd.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecommendFragment extends BaseFragment {


    private StellarMap stellarMap;
    private ArrayList<String> list;

    @Override
    protected View getSuccessView() {
        stellarMap = new StellarMap(getActivity());
        //1.设置内容距四周的距离
        int innerPadding = CommonUtil.getDimens(R.dimen.dp15);
        stellarMap.setInnerPadding(innerPadding, innerPadding, innerPadding, innerPadding);
        return stellarMap;
    }

    /**
     * 请求数据
     *
     * @return
     */
    @Override
    protected Object requestData() {
        String result = HttpUtil.get(Api.Recommend);
        list = (ArrayList<String>) JsonUtil.parseJsonToList(result, new TypeToken<List<String>>() {
        }.getType());

        if (list != null) {
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    stellarMap.setAdapter(new StellarMapAdapter());//更新Ui
                    //设置默认显示第几组
                    stellarMap.setGroup(0, true);//是否播放动画
                    //设置x和y方向显示的密度,一般x和y传入的是每组的数量就行了
                    stellarMap.setRegularity(11, 11);
                }
            });

        }
        return list;
    }

    class StellarMapAdapter implements StellarMap.Adapter {
        /**
         * 返回的几组数据，就是几页数据
         *
         * @return
         */
        @Override
        public int getGroupCount() {
            return list.size() / getCount(0);
        }

        /**
         * 返回每组有多少个数据  每组有11个数据
         *
         * @return
         */
        @Override
        public int getCount(int group) {
            return 11;
        }

        /**
         * @param group       当前第几组
         * @param position    组中位置
         * @param convertView
         * @return 返回需要随机摆放的view
         */
        @Override
        public View getView(int group, int position, View convertView) {
            final TextView textView = new TextView(getActivity());
            //1.设置文本数据
            int listPosition = group * getCount(group) + position;
            textView.setText(list.get(listPosition));
//            LogUtil.e(this,"group"+group+"position"+position);
//            textView.setText(Text);
            //2.设置随机的文字大小
            Random random = new Random();
            textView.setTextSize(random.nextInt(10) + 14);//14-23
            //3.设置随机的字体颜色
            /*int red = random.nextInt(150);//0-190
            int green = random.nextInt(150);//0-190
            int blue = random.nextInt(150);//0-190
            int color = Color.rgb(red, green, blue);//使用rgb混合生成一种新的颜色*/
            textView.setTextColor(ColorUtils.randomColor());
            //4.设置点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(textView.getText().toString());
                }
            });


            return textView;
        }

        /**
         * 当执行完平移动画后的下一组加载哪一组数据    源码没用到 无卵用
         *
         * @return
         */
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        /**
         * 当执行完缩放动画后的下一组加载哪一组数据
         *
         * @return
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            //0--1--2--0
            return (group + 1) % getGroupCount();
        }
    }
}
