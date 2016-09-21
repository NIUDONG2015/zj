package com.itheima.zd.adapter;

import com.itheima.zd.holder.BaseHolder;
import com.itheima.zd.holder.CategoryInfoHolder;
import com.itheima.zd.holder.CategoryTitleHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/24.
 */
public class CategoryAdapter extends BasicAdapter<Object> {
    public CategoryAdapter(ArrayList<Object> list) {
        super(list);
    }

    /**
     * 1.定义条目的类型
     */

    public final int ITEM_TITLE = 0;//title 类型的条目
    public final int ITEM_INFO = 1;//info 类型的条目


    /**
     * 2.返回条目类型的总数
     */
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 返回指定的position的条目是什么类型的
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        //TG指定position的数据类型来判断条目的类型
        Object object = list.get(position);
        if (object instanceof String) {
            //标题
            return ITEM_TITLE;
        } else {//Infos
            return ITEM_INFO;
        }
        //  return super.getItemViewType(position);
    }

/*    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType=getItemViewType(position);//得到条目的类型
        switch (itemViewType){
            case ITEM_TITLE:
                //加载title的布局 绑定title类型数据
                break;

            case ITEM_INFO:
                //加载INFO类型的布局 绑定INFO类型数据
                break;
        }
        return super.getView(position, convertView, parent);

    }*/

    @Override
    protected BaseHolder<Object> getHolder(int position) {
        BaseHolder<Object> holder = null;
        int itemViewType = getItemViewType(position);//得到条目的类型
        switch (itemViewType) {
            case ITEM_TITLE:
                //加载title的布局 绑定title类型数据
                //由于holder封装了加载布局和绑定数距的操作，只需根据不同条目类型返回不同的holder
                holder = new CategoryTitleHolder();
                break;

            case ITEM_INFO:
                //加载INFO类型的布局 绑定INFO类型数据
                holder=new CategoryInfoHolder();
                break;
        }
        return holder;
    }
}
