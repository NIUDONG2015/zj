package com.itheima.zd.adapter;

import com.itheima.zd.bean.Subject;
import com.itheima.zd.holder.BaseHolder;
import com.itheima.zd.holder.SubjectHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/23.
 */
public class SubjectAdapter extends BasicAdapter<Subject>{


    public SubjectAdapter(ArrayList<Subject> list) {
        super(list);
    }

    @Override
    protected BaseHolder<Subject> getHolder(int position) {
        return new SubjectHolder();
    }
}
