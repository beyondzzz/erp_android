package com.jl.jlapp.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
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

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class MyOrderListAdapter extends BaseQuickAdapter<OrderListModel.ResultDataBean,BaseViewHolder> {

    public MyOrderListAdapter(@LayoutRes int layoutResId, @Nullable List<OrderListModel.ResultDataBean> data) {

        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, OrderListModel.ResultDataBean item) {
        TextView myOrderItemBottomBtn2=helper.getView(R.id.my_order_item_bottom_btn2);
        myOrderItemBottomBtn2.setVisibility(View.GONE);
        helper.setText(R.id.my_order_no,item.getOrderNo());

        //订单列表中每个订单下的按钮
        switch (item.getOrderState()){
            case 0:
                helper.setText(R.id.my_order_state,R.string.for_the_payment);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.pay_money);
                break;
            case 1:
                helper.setText(R.id.my_order_state,R.string.to_send_the_goods);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.order_tracking);
                break;
            case 2:
                helper.setText(R.id.my_order_state,R.string.for_the_goods);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.order_tracking);
                helper.setText(R.id.my_order_item_bottom_btn2,"确认收货");
                myOrderItemBottomBtn2.setVisibility(View.VISIBLE);

                break;
            case 3:
                helper.setText(R.id.my_order_state,R.string.has_been_completed);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.once_again_to_buy);
                if(item.getIsHasEvaluation()==0){
                    helper.setText(R.id.my_order_item_bottom_btn2, R.string.evaluation_of_printing);
                }
                else if(item.getIsHasEvaluation()==1){
                    helper.setText(R.id.my_order_item_bottom_btn2, R.string.evaluation_detail);
                }
                //helper.setText(R.id.my_order_item_bottom_btn2,R.string.evaluation_of_printing);
                myOrderItemBottomBtn2.setVisibility(View.VISIBLE);
                break;
            case 4:
                helper.setText(R.id.my_order_state,R.string.has_been_cancelled);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.once_again_to_buy);
                break;
            case 5:
                helper.setText(R.id.my_order_state,R.string.has_closed);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.once_again_to_buy);
                break;
            case 6:
                helper.setText(R.id.my_order_state,R.string.in_the_after_sale);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.order_tracking);
                break;
            case 7:
                helper.setText(R.id.my_order_state,R.string.has_closed);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.once_again_to_buy);
                if(item.getIsHasEvaluation()==0){
                    helper.setText(R.id.my_order_item_bottom_btn2, R.string.evaluation_of_printing);
                }
                else if(item.getIsHasEvaluation()==1){
                    helper.setText(R.id.my_order_item_bottom_btn2, R.string.evaluation_detail);
                }
                break;
            case 8:
                helper.setText(R.id.my_order_state,R.string.has_closed);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.once_again_to_buy);
                if(item.getIsHasEvaluation()==0){
                    helper.setText(R.id.my_order_item_bottom_btn2, R.string.evaluation_of_printing);
                }
                else if(item.getIsHasEvaluation()==1){
                    helper.setText(R.id.my_order_item_bottom_btn2, R.string.evaluation_detail);
                }
                break;
            case 9:
                helper.setText(R.id.my_order_state,"审核中");
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.order_tracking);
                break;
            case 10:
                helper.setText(R.id.my_order_state,R.string.has_closed);
                helper.setText(R.id.my_order_item_bottom_btn1,R.string.once_again_to_buy);
                if(item.getIsHasEvaluation()==0){
                    helper.setText(R.id.my_order_item_bottom_btn2, R.string.evaluation_of_printing);
                }
                else if(item.getIsHasEvaluation()==1){
                    helper.setText(R.id.my_order_item_bottom_btn2, R.string.evaluation_detail);
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

        helper.setText(R.id.goods_name,item.getOrderDetails().get(0).getGoodsName());
        helper.setText(R.id.goods_price,item.getOrderDetails().get(0).getGoodsOriginalPrice()+"");
        helper.setText(R.id.my_order_goods_specification,item.getOrderDetails().get(0).getGoodsSpecificationName());
        helper.setText(R.id.my_order_goods_num,item.getOrderDetails().get(0).getGoodsQuantity()+"");
        helper.setText(R.id.my_order_freight,item.getPostage()+"");
        DecimalFormat df = new DecimalFormat(".00");//保留两位小数
        double price = item.getOrderPresentPrice()+item.getPostage();
        if(price>1){
            helper.setText(R.id.my_order_combined_num,df.format(price)+"");
        }else{
            helper.setText(R.id.my_order_combined_num,Double.parseDouble(df.format(price))+"");
        }


        int orderNum=0;
        for(int i=0;i<item.getOrderDetails().size();i++){
            orderNum+=item.getOrderDetails().get(i).getGoodsQuantity();
        }
        helper.setText(R.id.my_order_combined_goods_num,orderNum+"");

        helper.addOnClickListener(R.id.my_order_item_bottom_btn1);
        helper.addOnClickListener(R.id.my_order_item_bottom_btn2);
    }

}
