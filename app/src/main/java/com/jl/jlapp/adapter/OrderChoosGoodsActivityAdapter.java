package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ActivitysAndCouponsByGoodsMsgModel;
import com.jl.jlapp.mvp.activity.OrderSubmitActivity;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/22 0022.
 */

public class OrderChoosGoodsActivityAdapter extends BaseQuickAdapter<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.ActivitysBean, BaseViewHolder> {
    int chooseId=0;

    public void setChooseId(int chooseId) {
        this.chooseId = chooseId;
    }

    public OrderChoosGoodsActivityAdapter(int layoutResId, @Nullable List<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.ActivitysBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.ActivitysBean item) {
        ImageView imageView = helper.getView(R.id.is_has_choose);
        imageView.setVisibility(View.GONE);
        if (item.getActivityInformation().getId() == chooseId) {
            imageView.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.activity_information_id,item.getActivityInformation().getId()+"");
        String goodsName="";
        for(int i=0;i< OrderSubmitActivity.shouldBuyShoppingCartList.size();i++){
            if(item.getGoodsSpeIdList().size()>0){
                for(int g=0;g<item.getGoodsSpeIdList().size();g++){
                    if(OrderSubmitActivity.shouldBuyShoppingCartList.get(i).getGoodsSpecificationDetailsId()==item.getGoodsSpeIdList().get(g)){
                        if(g==0){
                            goodsName+=OrderSubmitActivity.shouldBuyShoppingCartList.get(i).getGoodsName()+OrderSubmitActivity.shouldBuyShoppingCartList.get(i).getGoodsSpecificationDetailsName();
                        }
                        else{
                            goodsName+="和"+OrderSubmitActivity.shouldBuyShoppingCartList.get(i).getGoodsName()+OrderSubmitActivity.shouldBuyShoppingCartList.get(i).getGoodsSpecificationDetailsName();
                        }

                    }
                }
            }

        }
        switch (item.getActivityInformation().getActivityType()) {
            case 0:
                helper.setText(R.id.shop_cart_activity_type, "折扣");
                helper.setText(R.id.activity_name, goodsName+"打 " + item.getActivityInformation().getDiscount() * 10 + " 折，最多可购买" + item.getActivityInformation().getMaxNum() + "个");
                break;
            case 1:
                helper.setText(R.id.shop_cart_activity_type, "团购");
                helper.setText(R.id.activity_name, goodsName+"团购单价为¥" + item.getActivityInformation().getDiscount() + "，最多可购买" + item.getActivityInformation().getMaxNum() + "个");
                break;
            case 2:
                helper.setText(R.id.shop_cart_activity_type, "秒杀");
                helper.setText(R.id.activity_name, goodsName+"秒杀单价为¥" + item.getActivityInformation().getDiscount() + "，最多可购买" + item.getActivityInformation().getMaxNum() + "个");
                break;
            case 3:
                helper.setText(R.id.shop_cart_activity_type, "立减");
                helper.setText(R.id.activity_name, goodsName+"购买可立减¥" + item.getActivityInformation().getDiscount() + "，最多可购买" + item.getActivityInformation().getMaxNum() + "个");
                break;
            case 4:
                helper.setText(R.id.shop_cart_activity_type, "满减");
                helper.setText(R.id.activity_name, goodsName+"满¥" + item.getActivityInformation().getPrice() + "，可减¥" + item.getActivityInformation().getDiscount());
                break;
            default:
                break;
        }
    }
}
