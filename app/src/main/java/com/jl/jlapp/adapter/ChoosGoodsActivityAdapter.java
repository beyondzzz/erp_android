package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ShipAddressModel;
import com.jl.jlapp.eneity.ShoppingCartModel;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/22 0022.
 */

public class ChoosGoodsActivityAdapter extends BaseQuickAdapter<ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX,BaseViewHolder> {


    public ChoosGoodsActivityAdapter(int layoutResId, @Nullable List<ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX item) {
        switch (item.getActivityInformation().getActivityType()) {
            case 0:
                helper.setText(R.id.shop_cart_activity_type,"折扣");
                helper.setText(R.id.activity_name,"打 "+item.getActivityInformation().getDiscount()*10+" 折，最多可购买"+item.getActivityInformation().getMaxNum()+"个");
                break;
            case 1:
                helper.setText(R.id.shop_cart_activity_type,"团购");
                helper.setText(R.id.activity_name,"团购单价为¥"+item.getActivityInformation().getDiscount()+"，最多可购买"+item.getActivityInformation().getMaxNum()+"个");
                break;
            case 2:
                helper.setText(R.id.shop_cart_activity_type,"秒杀");
                helper.setText(R.id.activity_name,"秒杀单价为¥"+item.getActivityInformation().getDiscount()+"，最多可购买"+item.getActivityInformation().getMaxNum()+"个");
                break;
            case 3:
                helper.setText(R.id.shop_cart_activity_type,"立减");
                helper.setText(R.id.activity_name,"购买可立减¥"+item.getActivityInformation().getDiscount()+"，最多可购买"+item.getActivityInformation().getMaxNum()+"个");
                break;
            case 4:
                helper.setText(R.id.shop_cart_activity_type,"满减");
                helper.setText(R.id.activity_name,"该商品满¥"+item.getActivityInformation().getPrice()+"，可减¥"+item.getActivityInformation().getDiscount());
                break;
            default:break;
        }
    }
}
