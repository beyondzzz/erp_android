package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.Order;
import com.jl.jlapp.eneity.OrderListModel;
import com.jl.jlapp.nets.AppFinal;

import java.util.List;

/**
 * Created by fyf on 2018/1/20.
 */

public class CenterAdapter extends BaseQuickAdapter<OrderListModel.ResultDataBean, BaseViewHolder> {


    public CenterAdapter(int layoutResId, @Nullable List<OrderListModel.ResultDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListModel.ResultDataBean item) {
        TextView myOrderItemBottomBtn2 = helper.getView(R.id.my_order_item_bottom_btn2);
        myOrderItemBottomBtn2.setVisibility(View.GONE);
        helper.setText(R.id.my_order_no, item.getOrderNo());

        //订单列表中每个订单下的按钮
        switch (item.getOrderState()) {

            case 3:
                helper.setText(R.id.my_order_state, R.string.has_been_completed);
                if(item.getIsHasEvaluation()==0){
                    helper.setText(R.id.my_order_item_bottom_btn1, R.string.evaluation_of_printing);
                }
                else if(item.getIsHasEvaluation()==1){
                    helper.setText(R.id.my_order_item_bottom_btn1, R.string.evaluation_detail);
                }
                break;

            case 8:
                helper.setText(R.id.my_order_state, R.string.has_been_completed);
                if(item.getIsHasEvaluation()==0){
                    helper.setText(R.id.my_order_item_bottom_btn1, R.string.evaluation_of_printing);
                }
                else if(item.getIsHasEvaluation()==1){
                    helper.setText(R.id.my_order_item_bottom_btn1, R.string.evaluation_detail);
                }
                break;

            case 10:
                helper.setText(R.id.my_order_state, R.string.has_been_completed);
                if(item.getIsHasEvaluation()==0){
                    helper.setText(R.id.my_order_item_bottom_btn1, R.string.evaluation_of_printing);
                }
                else if(item.getIsHasEvaluation()==1){
                    helper.setText(R.id.my_order_item_bottom_btn1, R.string.evaluation_detail);
                }
                break;
        }

        // helper.setImageResource(R.id.goods_img,item.get);

        ImageView imageView = helper.getView(R.id.goods_img);
        Glide
                .with(helper.itemView.getContext())
                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                .load(AppFinal.BASEURL + item.getOrderDetails().get(0).getGoodsCoverUrl())
                .into(imageView);

        helper.setText(R.id.goods_name, item.getOrderDetails().get(0).getGoodsName());
        helper.setText(R.id.goods_price, item.getOrderDetails().get(0).getGoodsOriginalPrice() + "");
        helper.setText(R.id.my_order_goods_specification, item.getOrderDetails().get(0).getGoodsSpecificationName());
        helper.setText(R.id.my_order_goods_num, item.getOrderDetails().get(0).getGoodsQuantity() + "");
        helper.setText(R.id.my_order_freight, item.getPostage() + "");
        helper.setText(R.id.my_order_combined_num, item.getOrderPresentPrice() + item.getPostage() + "");

        int orderNum = 0;
        for (int i = 0; i < item.getOrderDetails().size(); i++) {
            orderNum += item.getOrderDetails().get(i).getGoodsQuantity();
        }
        helper.setText(R.id.my_order_combined_goods_num, orderNum + "");

        helper.addOnClickListener(R.id.my_order_item_bottom_btn1);
    }
}
