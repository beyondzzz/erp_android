package com.jl.jlapp.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.AllAvailableCouponModel;
import com.jl.jlapp.eneity.UserCouponInfosModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by fyf on 2018/1/16.
 */

public class CouponCenterAdapter extends BaseQuickAdapter<AllAvailableCouponModel.ResultDataBean, BaseViewHolder> {



    public CouponCenterAdapter(@LayoutRes int layoutResId, @Nullable List<AllAvailableCouponModel.ResultDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllAvailableCouponModel.ResultDataBean item) {
        //helper.setText(R.id.tv_discount_time, item.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        TextView explain = helper.getView(R.id.tv_discount_explain);
        TextView timeStart = helper.getView(R.id.tv_discount_time_start);
        TextView timeStrip = helper.getView(R.id.tv_discount_time_strip);
        TextView timeEnd = helper.getView(R.id.tv_discount_time_end);
        TextView money = helper.getView(R.id.tv_discount_money);
        TextView moneyUnit = helper.getView(R.id.tv_discount_money_unit);
        TextView condition = helper.getView(R.id.tv_discount_condition);
//        TextView explanation = helper.getView(R.id.tv_coupon_explanation);
        RelativeLayout relativeLayout = helper.getView(R.id.coupon_background);


        helper.setText(R.id.tv_discount_explain, item.getName());
        helper.setText(R.id.tv_discount_money, item.getPrice() + "");
        helper.setText(R.id.tv_discount_condition, "满¥" + item.getUseLimit() + "可用");
        helper.setText(R.id.tv_discount_time_start, simpleDateFormat.format(item.getBeginValidityTime()));
        helper.setText(R.id.tv_discount_time_end, simpleDateFormat.format(item.getEndValidityTime()));

        money.setTextColor(helper.itemView.getResources().getColor(R.color.red));
        moneyUnit.setTextColor(helper.itemView.getResources().getColor(R.color.red));
        condition.setTextColor(helper.itemView.getResources().getColor(R.color.moreText));
        explain.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        timeStart.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        timeStrip.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        timeEnd.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
//        explanation.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        relativeLayout.setBackgroundResource(R.drawable.coupe_bg);
        helper.addOnClickListener(R.id.tv_get_coupon);

    }
}
