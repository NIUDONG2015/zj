package com.itheima.zd.holder;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.zd.R;
import com.itheima.zd.bean.AppInfo;
import com.itheima.zd.bean.SafeInfo;
import com.itheima.zd.global.GooglePlayApplication;
import com.itheima.zd.global.ImageLoaderOptions;
import com.itheima.zd.http.Api;
import com.itheima.zd.ui.anim.PaddingTopAnim;
import com.itheima.zd.util.LogUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/27.
 */
public class AppSafeHolder extends BaseHolder<AppInfo> implements View.OnClickListener {
    private ImageView iv_safe_image1, iv_safe_image2, iv_safe_image3, iv_safe_arrow;
    private ImageView iv_safe_icon1, iv_safe_icon2, iv_safe_icon3;
    private TextView tv_safe_des1, tv_safe_des2, tv_safe_des3;
    private LinearLayout ll_safe2, ll_safe3, ll_safe_container;

    @Override
    public View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.layout_detail_app_safe, null);
        iv_safe_image1 = (ImageView) view.findViewById(R.id.iv_safe_image1);
        iv_safe_image2 = (ImageView) view.findViewById(R.id.iv_safe_image2);
        iv_safe_image3 = (ImageView) view.findViewById(R.id.iv_safe_image3);
        iv_safe_arrow = (ImageView) view.findViewById(R.id.iv_safe_arrow);

        iv_safe_icon1 = (ImageView) view.findViewById(R.id.iv_safe_icon1);
        iv_safe_icon2 = (ImageView) view.findViewById(R.id.iv_safe_icon2);
        iv_safe_icon3 = (ImageView) view.findViewById(R.id.iv_safe_icon3);

        tv_safe_des1 = (TextView) view.findViewById(R.id.tv_safe_des1);
        tv_safe_des2 = (TextView) view.findViewById(R.id.tv_safe_des2);
        tv_safe_des3 = (TextView) view.findViewById(R.id.tv_safe_des3);

        ll_safe2 = (LinearLayout) view.findViewById(R.id.ll_safe2);
        ll_safe3 = (LinearLayout) view.findViewById(R.id.ll_safe3);
        ll_safe_container = (LinearLayout) view.findViewById(R.id.ll_safe_container);

        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        ArrayList<SafeInfo> safeList = appInfo.getSafe();
        SafeInfo info1 = safeList.get(0);
        ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + info1.getSafeUrl(), iv_safe_image1, ImageLoaderOptions.fadein_options);
        ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + info1.getSafeDesUrl(), iv_safe_icon1, ImageLoaderOptions.fadein_options);
        tv_safe_des1.setText(info1.getSafeDes());

        //由于第2个和第3个可能没有，所以需要判断
        if (safeList.size() > 1) {
            //说明有第2个
            SafeInfo info2 = safeList.get(1);
            ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + info2.getSafeUrl(), iv_safe_image2, ImageLoaderOptions.fadein_options);
            ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + info2.getSafeDesUrl(), iv_safe_icon2, ImageLoaderOptions.fadein_options);
            tv_safe_des2.setText(info2.getSafeDes());
        } else {
            //没有第2个，则隐藏
            ll_safe2.setVisibility(View.GONE);
        }
        if (safeList.size() > 2) {
            //说明有第3个
            SafeInfo info3 = safeList.get(2);
            ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + info3.getSafeUrl(), iv_safe_image3, ImageLoaderOptions.fadein_options);
            ImageLoader.getInstance().displayImage(Api.IMAGE_PREFIX + info3.getSafeDesUrl(), iv_safe_icon3, ImageLoaderOptions.fadein_options);
            tv_safe_des3.setText(info3.getSafeDes());
        } else {
            //没有第3个，则隐藏
            ll_safe3.setVisibility(View.GONE);
        }


        maxPaddingTop = ll_safe_container.getPaddingTop();
        //1.一开始通过给ll_safe_container设置负的paddingTop值来实现隐藏
        ll_safe_container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**
             * 当ll_safe_container在父View中执行完layout(布局)之后才调用该方法；
             * 所以在该方法中肯定可以获取到宽高的值
             */
            @Override
            public void onGlobalLayout() {
                //一般用完立即移除，因为只有该view的宽高改变都会再引起回调该方法
                ll_safe_container.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //获取到高度
                minPaddingTop = -1 * ll_safe_container.getMeasuredHeight();
                ll_safe_container.setPadding(ll_safe_container.getPaddingLeft(), minPaddingTop, ll_safe_container.getPaddingRight()
                        , ll_safe_container.getPaddingBottom());

                LogUtil.e(this, "minPaddingTop:" + minPaddingTop);
            }
        });
        //一开始让整个View移动到左边
        ViewHelper.setTranslationX(holderView, -1 * holderView.getMeasuredWidth());
        ViewPropertyAnimator.animate(holderView)
                .translationXBy(holderView.getMeasuredWidth())
                .setInterpolator(new OvershootInterpolator())
                .setDuration(400)
                .setStartDelay(600)
                .start();
    }

    private int minPaddingTop;//缩进去后的paddingTop
    private int maxPaddingTop;//最大的paddingTop
    private boolean isExtend = false;//是否是伸展 ，默认是收缩的
    private boolean isAniming = false;//是否正在执行动画

    @Override
    public void onClick(View v) {
        if (v == holderView) {
           /* ViewPropertyAnimator<平移 缩放 透明 旋转>  动画代替   ObjectAnimator
            # 扩展-----自定义动画行为---valuesAnimator*/

            if (isAniming) {
                //如果正在执行动画，则不应该再执行以下代码
                return;
            }
            PaddingTopAnim anim = null;
            if (isExtend) {
                //收缩动画

//              anim = ValueAnimator.ofInt(maxPaddingTop, minPaddingTop);
                anim = new PaddingTopAnim(ll_safe_container, maxPaddingTop, minPaddingTop);
            } else {
                //伸展动画
//              anim = ValueAnimator.ofInt(minPaddingTop, maxPaddingTop);
                anim = new PaddingTopAnim(ll_safe_container, minPaddingTop, maxPaddingTop);
            }
       /*     //应该根据动画更新的进度，自己实现动画的逻辑
            final ValueAnimator finalAnimator = animator;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(  ValueAnimator animation) {
                   int animatedValue =(Integer) finalAnimator.getAnimatedValue();
                    //将动画当前的值animatedValue设为view当前的PaddingTop
                    ll_safe_container.setPadding(ll_safe_container.getPaddingLeft(), animatedValue, ll_safe_container.getPaddingRight()
                            , ll_safe_container.getPaddingBottom());

                }
            });*/
            anim.start(350);
            //标记设置为反值
            isExtend = !isExtend;
            //      将箭头旋转
            ViewPropertyAnimator.animate(iv_safe_arrow).rotationBy(180)
                    .setListener(new MyListener())
                    .setDuration(350).start();
        }
    }

    class MyListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            isAniming = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAniming = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
