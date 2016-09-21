package com.itheima.zd.util;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Administrator on 2016/6/25.
 */
public class ColorUtils {
    //设置随机生成字体的颜色

    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150);//0-190
        int green = random.nextInt(150);//0-190
        int blue = random.nextInt(150);//0-190
        return Color.rgb(red, green, blue);//使用rgb混合生成一种新的颜色

    }
}
