package com.itheima.zd.ui.anim;

import android.view.View;

/**
 * Created by Administrator on 2016/6/28.  执行PaddingTop变化的动画
 */
public class PaddingTopAnim extends BaseAnim{
    public PaddingTopAnim(View target, int startValue, int endValue) {
        super(target, startValue, endValue);
    }

    @Override
    protected void doAnim(int animatedValue) {
//将动画当前的值animatedValue设为view当前的PaddingTop
        target.setPadding(target.getPaddingLeft(), animatedValue, target.getPaddingRight()
                , target.getPaddingBottom());
    }
}
