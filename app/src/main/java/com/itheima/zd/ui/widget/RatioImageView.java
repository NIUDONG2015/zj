package com.itheima.zd.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/24.
 * 根据宽高比动态设置自身的高度
 */
public class RatioImageView extends ImageView {

    private float ratio = 1.5f;//宽高比

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public RatioImageView(Context context) {
        super(context);
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec 父类计算并且传给子view的
     *                          测量规则MeasureSpec 封装了Size和Mode
     *                          MeasureSpec.AT_MOST---对应的是wrap_content
     *                          MeasureSpec.EXACTLY---对应的是match_parent,具体的dp值
     *                          MeasureSpec.UNSPECIFIED  表示未指定的 一般不用 只在adapter的测量用到
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1.获取当前 ImageView的高度
        //通过widthMeasureSpec来取出当前ImageView的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //2.根据宽高比，计算对应的高度
        if (ratio != 0) {
            float height = width / ratio;//获取对应的高度
            //3.将高度设置给ImageView
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
