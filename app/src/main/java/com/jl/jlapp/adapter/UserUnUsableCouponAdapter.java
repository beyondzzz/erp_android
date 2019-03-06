package com.jl.jlapp.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.UserCouponInfosModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by fyf on 2018/1/16.
 */

public class UserUnUsableCouponAdapter extends BaseQuickAdapter<UserCouponInfosModel.ResultDataBean.DisabledCouponBean, BaseViewHolder> {


    public UserUnUsableCouponAdapter(@LayoutRes int layoutResId, @Nullable List<UserCouponInfosModel.ResultDataBean.DisabledCouponBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCouponInfosModel.ResultDataBean.DisabledCouponBean item) {
        //helper.setText(R.id.tv_discount_time, item.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        TextView explain = helper.getView(R.id.tv_discount_explain);
        TextView timeStart = helper.getView(R.id.tv_discount_time_start);
        TextView timeStrip = helper.getView(R.id.tv_discount_time_strip);
        TextView timeEnd = helper.getView(R.id.tv_discount_time_end);
        TextView money = helper.getView(R.id.tv_discount_money);
        TextView moneyUnit = helper.getView(R.id.tv_discount_money_unit);
        TextView condition = helper.getView(R.id.tv_discount_condition);
        TextView tvGetCoupon=helper.getView(R.id.tv_get_coupon);
//        TextView explanation = helper.getView(R.id.tv_coupon_explanation);
        RelativeLayout relativeLayout = helper.getView(R.id.coupon_background);


        helper.setText(R.id.tv_discount_explain, item.getCouponInformation().getName());
        helper.setText(R.id.tv_discount_money, item.getCouponInformation().getPrice() + "");
        helper.setText(R.id.tv_discount_condition, "满¥" + item.getCouponInformation().getUseLimit() + "可用");
        helper.setText(R.id.tv_discount_time_start, simpleDateFormat.format(item.getCouponInformation().getBeginValidityTime()));
        helper.setText(R.id.tv_discount_time_end, simpleDateFormat.format(item.getCouponInformation().getEndValidityTime()));
        helper.setText(R.id.tv_get_coupon,item.getStateMsg());

        tvGetCoupon.setTextColor(helper.itemView.getResources().getColor(R.color.white));
        tvGetCoupon.setBackgroundResource(R.drawable.bg_coupon_white_border);
        money.setTextColor(helper.itemView.getResources().getColor(R.color.white));
        moneyUnit.setTextColor(helper.itemView.getResources().getColor(R.color.white));
        condition.setTextColor(helper.itemView.getResources().getColor(R.color.white));
        explain.setTextColor(helper.itemView.getResources().getColor(R.color.coupon_explanation));
        timeStart.setTextColor(helper.itemView.getResources().getColor(R.color.light_gray));
        timeStrip.setTextColor(helper.itemView.getResources().getColor(R.color.light_gray));
        timeEnd.setTextColor(helper.itemView.getResources().getColor(R.color.light_gray));
//        explanation.setTextColor(helper.itemView.getResources().getColor(R.color.light_gray));
        relativeLayout.setBackgroundResource(R.drawable.coupe_bg_disable);


    }
}
