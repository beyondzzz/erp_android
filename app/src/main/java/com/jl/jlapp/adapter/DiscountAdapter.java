package com.jl.jlapp.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ActivitysAndCouponsByGoodsMsgModel;
import com.jl.jlapp.eneity.DiscountModel;
import com.jl.jlapp.mvp.activity.DiscountActivity;
import com.jl.jlapp.mvp.fragment.DiscountFragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by fyf on 2018/1/16.
 */

public class DiscountAdapter extends BaseQuickAdapter<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean, BaseViewHolder> {

    int type= DiscountFragment.TYPE_USEABLE;

    public void setType(int type) {
        this.type = type;
    }

    public DiscountAdapter(@LayoutRes int layoutResId, @Nullable List<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean item) {
        //helper.setText(R.id.tv_discount_time, item.getTime());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        helper.setText(R.id.tv_discount_explain, item.getUserCoupons().getCouponInformation().getName());
        helper.setText(R.id.tv_discount_money, item.getUserCoupons().getCouponInformation().getPrice()+"");
        helper.setText(R.id.tv_discount_condition, "满¥"+item.getUserCoupons().getCouponInformation().getUseLimit()+"可用");
        helper.setText(R.id.tv_discount_time_start,simpleDateFormat.format(item.getUserCoupons().getCouponInformation().getBeginValidityTime()));
        helper.setText(R.id.tv_discount_time_end,simpleDateFormat.format(item.getUserCoupons().getCouponInformation().getEndValidityTime()));

        TextView explain=helper.getView(R.id.tv_discount_explain);
        TextView timeStart=helper.getView(R.id.tv_discount_time_start);
        TextView timeStrip=helper.getView(R.id.tv_discount_time_strip);
        TextView timeEnd=helper.getView(R.id.tv_discount_time_end);
        TextView money=helper.getView(R.id.tv_discount_money);
        TextView moneyUnit=helper.getView(R.id.tv_discount_money_unit);
        TextView condition=helper.getView(R.id.tv_discount_condition);
//        TextView explanation=helper.getView(R.id.tv_coupon_explanation);
        RelativeLayout relativeLayout=helper.getView(R.id.coupon_background);


        if (type==DiscountFragment.TYPE_USEABLE){
            money.setTextColor(helper.itemView.getResources().getColor(R.color.red));
            moneyUnit.setTextColor(helper.itemView.getResources().getColor(R.color.red));
            condition.setTextColor(helper.itemView.getResources().getColor(R.color.moreText));
            explain.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
            timeStart.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
            timeStrip.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
            timeEnd.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
//            explanation.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
            relativeLayout.setBackgroundResource(R.drawable.coupe_bg);
        }else{
            money.setTextColor(helper.itemView.getResources().getColor(R.color.white));
            moneyUnit.setTextColor(helper.itemView.getResources().getColor(R.color.white));
            condition.setTextColor(helper.itemView.getResources().getColor(R.color.white));
            explain.setTextColor(helper.itemView.getResources().getColor(R.color.coupon_explanation));
            timeStart.setTextColor(helper.itemView.getResources().getColor(R.color.light_gray));
            timeStrip.setTextColor(helper.itemView.getResources().getColor(R.color.light_gray));
            timeEnd.setTextColor(helper.itemView.getResources().getColor(R.color.light_gray));
//            explanation.setTextColor(helper.itemView.getResources().getColor(R.color.light_gray));
            relativeLayout.setBackgroundResource(R.drawable.coupe_bg_disable);
        }

    }
}
