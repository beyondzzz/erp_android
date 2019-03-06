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
import com.jl.jlapp.eneity.OrderDetail;
import com.jl.jlapp.eneity.OrderListModel;
import com.jl.jlapp.nets.AppFinal;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class ShouldEvaluationOrderListAdapter extends BaseQuickAdapter<OrderDetail.ResultDataBean.OrderDetailsBean,BaseViewHolder> {

    public ShouldEvaluationOrderListAdapter(@LayoutRes int layoutResId, @Nullable List<OrderDetail.ResultDataBean.OrderDetailsBean> data) {

        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, OrderDetail.ResultDataBean.OrderDetailsBean item) {

        ImageView imageView = helper.getView(R.id.goods_img);
        Glide
                .with(helper.itemView.getContext())
                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                .load(AppFinal.BASEURL + item.getGoodsCoverUrl())
                .into(imageView);

        helper.setText(R.id.goods_name,item.getGoodsName());
        helper.setText(R.id.goods_price,item.getGoodsOriginalPrice()+"");
        helper.setText(R.id.my_order_goods_specification,item.getGoodsSpecificationName());
        helper.setText(R.id.my_order_goods_num,item.getGoodsQuantity()+"");
        helper.setText(R.id.my_order_combined_num,item.getGoodsPaymentPrice()+"");
        helper.setText(R.id.my_order_combined_goods_num,item.getGoodsQuantity()+"");

        helper.addOnClickListener(R.id.my_order_item_bottom_btn1);
    }

}
