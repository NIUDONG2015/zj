package com.itheima.zd.holder;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itheima.zd.R;
import com.itheima.zd.bean.AppInfo;
import com.itheima.zd.global.GooglePlayApplication;
import com.itheima.zd.ui.anim.HeightAnim;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by Administrator on 2016/6/28.
 */
public class AppDesHolder extends BaseHolder<AppInfo> implements View.OnClickListener {

    private TextView tv_des, tv_author;
    private ImageView iv_des_arrow;
    private ScrollView scrollView;

    @Override
    public View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.layout_detail_app_des, null);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_author = (TextView) view.findViewById(R.id.tv_author);
        iv_des_arrow = (ImageView) view.findViewById(R.id.iv_des_arrow);

        view.setOnClickListener(this);
        return view;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        tv_des.setText(appInfo.getDes());
        tv_author.setText(appInfo.getAuthor());
        //1.让描述只显示5行高度
        tv_des.setMaxLines(5);
        tv_des.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                tv_des.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                minHeight = tv_des.getHeight();//193

                //2.获取描述区域全部的高度，由于改变了tv_des的高度，会引起他重新布局
                tv_des.setMaxLines(Integer.MAX_VALUE);//rang TeXView显示全部内容
                tv_des.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
//                        LogUtil.e(this, "Height" + tv_des.getHeight());
                        tv_des.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        //得到全部文本的高度
                        maxHeight = tv_des.getHeight();
                        //3.让描述只显示5行的高度,
//						tv_des.setMaxLines(5);//不要使用这种方式
                        ViewGroup.LayoutParams params = tv_des.getLayoutParams();
                        params.height = minHeight;
                        tv_des.setLayoutParams(params);
                    }
                });

            }
        });
    }

    private int minHeight;
    private int maxHeight;
    private boolean isExtend = false; //是否展开
    private boolean isAniming = false;//是否正在执行动画

    @Override
    public void onClick(View v) {
        if (v == holderView) {
     /*       final ValueAnimator animator = ValueAnimator.ofInt(minHeight, maxHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (Integer) animator.getAnimatedValue();
                    //将AnimatedValue设置给tv-des  的高度
                    ViewGroup.LayoutParams params = tv_des.getLayoutParams();
                    params.height = animatedValue;
                    tv_des.setLayoutParams(params);
                }
            });
            animator.setDuration(350).start();*/
            if (isAniming) {
                return;
            }
            HeightAnim anim = null;
            if (isExtend) {

                //执行收缩
                anim = new HeightAnim(tv_des, maxHeight, minHeight);

            } else {
                //执行展开
                anim = new HeightAnim(tv_des, minHeight, maxHeight);
            }
            anim.start(350);
            //设置监听器
            anim.setOnHeightChangeListener(new HeightAnim.OnHeightChangeListener() {
                @Override
                public void onHeightChange(int animatedValue) {
                    //正值向上滑动
//                   scrollView.scrollBy(0,maxHeight-minHeight);//正十向上滑   kong
                    scrollView.scrollBy(0, 1000);
                }
            });
            //更改标记
            isExtend = !isExtend;

            //让箭头旋转
            ViewPropertyAnimator.animate(iv_des_arrow)
                    .rotationBy(180)
                    .setDuration(350)
                    .setListener(new MyListener())
                    .start();
        }
    }

    class MyListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationCancel(Animator arg0) {
        }

        @Override
        public void onAnimationEnd(Animator arg0) {
            isAniming = false;
        }

        @Override
        public void onAnimationRepeat(Animator arg0) {
        }

        @Override
        public void onAnimationStart(Animator arg0) {
            isAniming = true;
        }
    }


}

