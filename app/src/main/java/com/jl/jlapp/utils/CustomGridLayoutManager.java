package com.jl.jlapp.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by 柳亚婷 on 2018/1/15 0015.
 */

public class CustomGridLayoutManager extends GridLayoutManager {

    private boolean isScrollEnabled = true;

    public CustomGridLayoutManager(Context context, int spanCount, boolean isScrollEnabled) {
        super(context, spanCount);
        this.isScrollEnabled = isScrollEnabled;
    }

    public CustomGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}