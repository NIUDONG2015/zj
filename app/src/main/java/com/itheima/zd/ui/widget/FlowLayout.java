package com.itheima.zd.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/24.
 */
public class FlowLayout extends ViewGroup {
    private int horizontalSpacing = 15;//水平间距
    private int verticalSpacing = 15;//垂直间距
    /**
     * 用来存放所有的line对象
     */
    private ArrayList<Line> lineList = new ArrayList<Line>();
    private Line line;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置水平间距
     */
    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    /**
     * 设置垂直间距
     */
    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    /**
     * 遍历所有的子view  判断哪几个子view在同一行
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1.获取FlowLayout的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //2.获取用于实际比较的宽度，就是除去两边的padding的宽度
        int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();
        //3.遍历所有的子view 拿子view的宽和noPaddingWidth的进行比较
        Line line = new Line();//准备line对象
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);//取出当前位置的子view
            childView.measure(0, 0);//保证能够获取到宽高
            //4.如果当前Line中没有子view，则不用比较直接放入line中，因为要保证每行知识有一个子view
            if (line.getViewList().size() == 0) {
                line.addLineView(childView);

            } else if (line.getLineWidth() + horizontalSpacing + childView.getMeasuredWidth() > noPaddingWidth) {
                //5.如果当前line的宽+水平间距+子View的宽大于noPaddingWidth,则child需要换行
                //需要先存放好之前的line对象，否则会造成丢失
                lineList.add(line);
                line = new Line(); //创建新的line
                line.addLineView(childView);

            } else {
                //6.说明当前的child应该放入line中
                line.addLineView(childView);
            }
            //7.如果当前child是最后的子view，那么需要保存最后的line对象
            if (i == (getChildCount() - 1)) {
                lineList.add(line);//保存最后的line
            }
        }
        //for循环结束了，lineList存放了所有的line 而每个line又记录了自己行的所有的view
        //计算FlowLayout需要的高度
        int height = getPaddingTop() + getPaddingTop();//先计算上下的padding值
        for (int i = 0; i < lineList.size(); i++) {
            height += lineList.get(i).getLineHeigth();//再的加上所有行的高度
        }
        height += (lineList.size() - 1) * verticalSpacing; //最后加上所有的行间距
        //设置当前控件的宽和高
        setMeasuredDimension(width, height);
    }

    /**
     * 去摆放所有的子view  让每个人真正的坐到自己的位置上
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);//获取line对象
            //从第二行开始，每行都比前一行的Top多个行高和垂直间距
            if (i > 0) {
                paddingTop += verticalSpacing + lineList.get(i - 1).getLineHeigth();
            }

            ArrayList<View> viewList = line.getViewList();//获取line的view集合
            //1.获取每行的留白的宽度
            int remainSpacing = getLineRemainSpacing(line);
            //2.计算每个view平均得到的值
            float preSpacing = remainSpacing / viewList.size();

            for (int j = 0; j < viewList.size(); j++) {
                View childVIew = viewList.get(j);
                //3.将得到的preSpacing增加到view的宽度上面
                int widthSpec = MeasureSpec.makeMeasureSpec((int) (childVIew.getMeasuredWidth() + preSpacing), MeasureSpec.EXACTLY);
                childVIew.measure(widthSpec, 0);//高度不变
                if (j == 0) {
                    //如果是第一行，name直接靠左边摆放

                    childVIew.layout(paddingLeft, paddingTop, paddingLeft + childVIew.getMeasuredWidth(), paddingTop + childVIew.getMeasuredHeight());
                } else {
                    //如果不是第一个，要参考前一个view的right
                    View preView = viewList.get(j - 1);
                    //当前view是以前view的right+水平间距
                    int left = preView.getRight() + horizontalSpacing;
                    childVIew.layout(left, preView.getTop(), left + childVIew.getMeasuredWidth(), preView.getBottom());
                }

            }
        }
    }

    /**
     * 获取指定line的留白
     */
    private int getLineRemainSpacing(Line line) {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - line.getLineWidth();
        //当前FlowLayout的总宽度
    }

    /**
     * 封装每行的数据 包括我们所有的子view
     */
    class Line {
        private ArrayList<View> viewList;//用来存放当前行所有的子view
        private int width;//表示所有子view的宽+水平间距
        private int height;//行的高度

        public Line() {
            viewList = new ArrayList<View>();
        }


        /**
         * 记录子view
         */
        public void addLineView(View child) {
            if (!viewList.contains(child)) {
                viewList.add(child);

                //1.新Line的width
                if (viewList.size() == 1) {
                    //说明添加的是第一个子view，那么Line的宽就是子view的宽度
                    width = child.getMeasuredWidth();
                } else {
                    //如果添加的不是第一个子view 应该加等于水平间距和子view的宽度
                    width += child.getMeasuredWidth() + horizontalSpacing;
                }
                //2.更新Line的Height
                height = Math.max(height, child.getMeasuredHeight());
//                height=child.getMeasuredHeight();   高大上
            }
        }

        /**
         * 获取当前行的宽度
         */
        public int getLineWidth() {
            return width;
        }


        /**
         * 获取当前行的高度
         */
        public int getLineHeigth() {
            return height;
        }

        /**
         * 获取当前行的所子view
         *
         * @return
         */

        public ArrayList<View> getViewList() {
            return viewList;
        }
    }
}
