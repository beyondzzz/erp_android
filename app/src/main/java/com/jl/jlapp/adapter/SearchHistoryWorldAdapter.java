package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/19 0019.
 */

public class SearchHistoryWorldAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public SearchHistoryWorldAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.search_history_world,item);
    }
}
