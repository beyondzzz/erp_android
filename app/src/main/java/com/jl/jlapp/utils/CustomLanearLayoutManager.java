package com.jl.jlapp.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * 禁止滑动
 * Created by fyf on 2017/8/2.
 */

public class CustomLanearLayoutManager extends LinearLayoutManager {


    public CustomLanearLayoutManager(Context context) {
        super(context);
    }
    public CustomLanearLayoutManager(Context context,boolean isScrollEnabled) {
        super(context);
        this.isScrollEnabled=isScrollEnabled;
    }

    private boolean isScrollEnabled = true;

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
