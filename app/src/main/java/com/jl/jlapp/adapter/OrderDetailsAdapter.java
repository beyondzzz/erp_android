package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.jl.jlapp.R;
import com.jl.jlapp.eneity.Evaluate;

import java.util.List;

/**
 * Created by fyf on 2018/2/5.
 */

public class OrderDetailsAdapter extends BaseQuickAdapter<Evaluate, BaseViewHolder> {


    public OrderDetailsAdapter(int layoutResId, @Nullable List<Evaluate> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Evaluate item) {
        helper.setText(R.id.tv_order_title, item.getTitle());
        helper.setText(R.id.tv_order_details, item.getScroe());
    }
}
