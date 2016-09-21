package com.itheima.zd.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;


/**
 * Created by Administrator on 2016/6/25.
 * 生成圆角图片  对应xml中的shape标签
 */
public class DrawableUtil {
    public static Drawable generateDrawable(int rgb, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置为矩形，默认就是矩形吧
        drawable.setColor(rgb);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    public static Drawable generateSelector(Drawable pressed, Drawable normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);//设置按下的图片
        drawable.addState(new int[]{}, normal);//设置默认的图片
      //设置状态选择器过度工具
        if (Build.VERSION.SDK_INT>10){
            drawable.setEnterFadeDuration(500);
            drawable.setExitFadeDuration(500);

        }

        return drawable;

    }
}
