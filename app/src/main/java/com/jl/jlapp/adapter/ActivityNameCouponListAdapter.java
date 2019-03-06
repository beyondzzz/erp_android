package com.jl.jlapp.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ActivityInformationByIdModel;

import java.util.Date;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class ActivityNameCouponListAdapter extends BaseQuickAdapter<ActivityInformationByIdModel.ResultDataBean.CouponInformationBean,BaseViewHolder> {

    public ActivityNameCouponListAdapter(@LayoutRes int layoutResId, @Nullable List<ActivityInformationByIdModel.ResultDataBean.CouponInformationBean> data) {

        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper,  ActivityInformationByIdModel.ResultDataBean.CouponInformationBean item) {
        helper.setText(R.id.coupon_price,item.getPrice()+"");
        helper.setText(R.id.coupon_use_limit,item.getUseLimit()+"");


        long endTime = item.getEndTime();//领取结束时间
        long nowDate = new Date().getTime();//当前时间
        long cutDownTime =nowDate - endTime;//计算当前时间和从服务器获取的领取结束时间的时间差
        if(cutDownTime>0){//当前时间大于领取结束时间  即：已过期
            helper.getView(R.id.coupon_background).setBackgroundResource(R.drawable.activity_name_coupon_background_gray);
            helper.setText(R.id.get_coupon,"已失效");

        }else{
            helper.addOnClickListener(R.id.get_coupon);
        }



    }

}
