package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.GoodsDetailModel;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/19 0019.
 */

public class GoodsMsgActivityAdapter extends BaseQuickAdapter<GoodsDetailModel.ResultDataBean.GoodsActivitysBean,BaseViewHolder>{

    public GoodsMsgActivityAdapter(int layoutResId, @Nullable List<GoodsDetailModel.ResultDataBean.GoodsActivitysBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailModel.ResultDataBean.GoodsActivitysBean item) {
        TextView textView=helper.getView(R.id.shop_cart_activity_type);
        textView.setVisibility(View.VISIBLE);
        if(item.getActivityInformation().getNameX().equals("暂无优惠")){
            textView.setVisibility(View.GONE);
            helper.setText(R.id.goods_activity_content,"暂无优惠");
        }
        else{
            switch (item.getActivityInformation().getActivityType()) {
                case 0:
                    helper.setText(R.id.shop_cart_activity_type,"折扣");
                    helper.setText(R.id.goods_activity_content,"打 "+item.getActivityInformation().getDiscount()*10+" 折，最多可购买"+item.getActivityInformation().getMaxNum()+"个");
                    break;
                case 1:
                    helper.setText(R.id.shop_cart_activity_type,"团购");
                    helper.setText(R.id.goods_activity_content,"团购单价为¥"+item.getActivityInformation().getDiscount()+"，最多可购买"+item.getActivityInformation().getMaxNum()+"个");
                    break;
                case 2:
                    helper.setText(R.id.shop_cart_activity_type,"秒杀");
                    helper.setText(R.id.goods_activity_content,"秒杀单价为¥"+item.getActivityInformation().getDiscount()+"，最多可购买"+item.getActivityInformation().getMaxNum()+"个");
                    break;
                case 3:
                    helper.setText(R.id.shop_cart_activity_type,"立减");
                    helper.setText(R.id.goods_activity_content,"购买可立减¥"+item.getActivityInformation().getDiscount()+"，最多可购买"+item.getActivityInformation().getMaxNum()+"个");
                    break;
                case 4:
                    helper.setText(R.id.shop_cart_activity_type,"满减");
                    helper.setText(R.id.goods_activity_content,"该商品满¥"+item.getActivityInformation().getPrice()+"，可减¥"+item.getActivityInformation().getDiscount());
                    break;
                default:break;
            }
        }
        //helper.setText(R.id.goods_activity_content,item.getActivityInformation().getNameX());
    }
}
