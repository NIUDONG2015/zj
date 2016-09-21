package com.itheima.zd.ui.anim;

import android.view.View;
import android.view.ViewGroup;

/**
 * 高度变化的动画
 * Created by Administrator on 2016/6/28.
 */
public class HeightAnim extends BaseAnim {


    public HeightAnim(View target, int startValue, int endValue) {
        super(target, startValue, endValue);
    }

    @Override
    protected void doAnim(int animatedValue) {
        //将AnimatedValue设置给tv-des  的高度
        ViewGroup.LayoutParams params = target.getLayoutParams();
        params.height = animatedValue;
        target.setLayoutParams(params);

        //将动画的值的改变通过接口暴漏给外界
        if (listener != null) {
            listener.onHeightChange(animatedValue);//kong
        }
    }

    private OnHeightChangeListener listener;

    public void setOnHeightChangeListener(OnHeightChangeListener listener) {
        this.listener = listener;
    }


    /**
     * 当height改变的监听器
     *
     * @author Administrator
     */
    public interface OnHeightChangeListener {
        void onHeightChange(int animatedValue);
    }

}
