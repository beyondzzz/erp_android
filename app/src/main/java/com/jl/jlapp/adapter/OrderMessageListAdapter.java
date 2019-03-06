package com.jl.jlapp.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.OrderListModel;
import com.jl.jlapp.eneity.OrderMessageListModel;
import com.jl.jlapp.nets.AppFinal;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class OrderMessageListAdapter extends BaseQuickAdapter<OrderMessageListModel.ResultDataBean, BaseViewHolder> {

    public OrderMessageListAdapter(@LayoutRes int layoutResId, @Nullable List<OrderMessageListModel.ResultDataBean> data) {

        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderMessageListModel.ResultDataBean item) {
        helper.setText(R.id.order_no, item.getOrderTable().getOrderNo());
        helper.setText(R.id.order_state,item.getOrderStateDetail());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        helper.setText(R.id.state_change_time,simpleDateFormat.format(item.getAddTime()));
        simpleDateFormat=new SimpleDateFormat("M月d日 HH:mm:ss");
        helper.setText(R.id.order_message_time,simpleDateFormat.format(item.getAddTime()));
    }

}
