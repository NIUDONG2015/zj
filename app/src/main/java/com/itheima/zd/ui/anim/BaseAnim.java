package com.itheima.zd.ui.anim;

import android.view.View;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * 动画基类，使用ValueAnimator定义了动画的执行流程 那么具体的动画逻辑由子类去实现
 * Created by Administrator on 2016/6/28.
 */
public abstract class BaseAnim {
    protected ValueAnimator animator = null;
    protected View target;//执行动画目标view

    public BaseAnim(final View target, int startValue, int endValue) {
        this.target = target;
        animator = ValueAnimator.ofInt(startValue, endValue);
        //应该根据动画更新的进度，自己实现动画的逻辑
//        final ValueAnimator finalAnimator = animator;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (Integer) animator.getAnimatedValue();

                doAnim(animatedValue);
              /*  //将动画当前的值animatedValue设为view当前的PaddingTop
                target.setPadding(target.getPaddingLeft(), animatedValue, target.getPaddingRight()
                        , target.getPaddingBottom());*/

            }
        });

    }

    /**
     * 开启动画
     */
    public void start(int duration) {
        animator.setDuration(duration).start();
    }

    /**
     * 执行具体的动画行为，由每个子类去具体实现
     *
     * @param animatedValue
     */
    protected abstract void doAnim(int animatedValue);
}
