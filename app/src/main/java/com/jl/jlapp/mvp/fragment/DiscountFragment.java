package com.jl.jlapp.mvp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.DiscountAdapter;
import com.jl.jlapp.eneity.ActivitysAndCouponsByGoodsMsgModel;
import com.jl.jlapp.eneity.DiscountModel;
import com.jl.jlapp.eneity.ParamForGetActivitysAndCouponsByGoodsMsg;
import com.jl.jlapp.eneity.UserCouponInfosModel;
import com.jl.jlapp.mvp.activity.DiscountActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.mvp.activity.OrderSubmitActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fyf on 2018/1/16.
 */

public class DiscountFragment extends LazyLoadFragment {

    public static final int TYPE_USEABLE = 1;
    public static final int TYPE_UNUSEABLE = 2;
    private static final String EXTRA_TYPE = "extra_type";
    @BindView(R.id.lrv_discount_list)
    RecyclerView lrvDiscountList;
    @BindView(R.id.no_coupon_show)
    RelativeLayout noCouponShow;
    Unbinder unbinder;
    private DiscountAdapter usadleDiscountAdapter, unUsadleDiscountAdapter;
    private List<DiscountModel> modelList;

    int userId=0;

    List<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean> usableCouponsBeanList;//根据订单获取到的用户所能使用的优惠券
    ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean couponsBean;
    ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean userCouponsBean;
    ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean.CouponInformationBean couponInformationBean;
    List<UserCouponInfosModel.ResultDataBean.UsableCouponBean> usableCouponBeans = new ArrayList<>();//该用户所有能使用的优惠券

    List<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean> shouldShowUsableCouponsBeanList = new ArrayList<>();
    List<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean> shouldShowUnUsableCouponsBeanList = new ArrayList<>();

    public setOnClickCouponItem setOnClickCouponItem;

    public OnGetCouponMsg onGetCouponMsg;

    public void setOnGetCouponMsg(OnGetCouponMsg onGetCouponMsg) {
        this.onGetCouponMsg = onGetCouponMsg;
    }

    public void setSetOnClickCouponItem(DiscountFragment.setOnClickCouponItem setOnClickCouponItem) {
        this.setOnClickCouponItem = setOnClickCouponItem;
    }

    int type = TYPE_USEABLE;

    public static DiscountFragment newInstance(int type,int userId) {
        DiscountFragment fragment = new DiscountFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        bundle.putInt("userId",userId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_discount;
    }

    @Override
    public void initViews(View view) {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(EXTRA_TYPE)) {
            throw new IllegalArgumentException("NoticeListFragment must be created by type args");
        }
        type = args.getInt(EXTRA_TYPE, TYPE_USEABLE);
        userId=args.getInt("userId",0);
        unbinder = ButterKnife.bind(this, view);

      /*  usadleDiscountAdapter = new DiscountAdapter(R.layout.item_discount, shouldShowUsableCouponsBeanList);
        usadleDiscountAdapter.setType(type);
        lrvDiscountList.setAdapter(usadleDiscountAdapter);*/
    }

    @Override
    public void loadData() {
        if(userId>0){
            getCouponInfoByUserId(userId);
            usableCouponsBeanList = OrderSubmitActivity.shouldShowCouponsBeanList;
        }
        else{
            Toast.makeText(getContext(),"您还未登录，请先登录。",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getContext(),LoginActivity.class);
            startActivity(intent);
        }


    }

    private void initDatas(int type) {
        switch (type) {
            case TYPE_USEABLE:
                setUsableData();
                //回调函数  修改DiscountActivity找那个显示的优惠券个数
                if(onGetCouponMsg!=null){
                    setUnusableCoupons();
                    onGetCouponMsg.onGetCouponMsg(shouldShowUsableCouponsBeanList.size(),shouldShowUnUsableCouponsBeanList.size());
                }
                if(shouldShowUsableCouponsBeanList.size()>0){
                    lrvDiscountList.setVisibility(View.VISIBLE);
                    noCouponShow.setVisibility(View.GONE);
                    if (usadleDiscountAdapter == null) {
                        lrvDiscountList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        usadleDiscountAdapter = new DiscountAdapter(R.layout.item_discount, shouldShowUsableCouponsBeanList);
                        usadleDiscountAdapter.setType(type);
                        lrvDiscountList.setAdapter(usadleDiscountAdapter);

                        usadleDiscountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                TextView priceTv = view.findViewById(R.id.tv_discount_money);
                                TextView discountMsgTv = view.findViewById(R.id.tv_discount_condition);
                                int couponId = shouldShowUsableCouponsBeanList.get(position).getUserCoupons().getId();
                                double price = Double.parseDouble(priceTv.getText().toString().trim());
                                String countMsg = discountMsgTv.getText().toString().trim();
                                if (setOnClickCouponItem != null) {
                                    setOnClickCouponItem.onClickCouponItem(couponId, price, countMsg);
                                }
                            }
                        });
                    } else {
                        usadleDiscountAdapter.setType(type);
                        usadleDiscountAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    lrvDiscountList.setVisibility(View.GONE);
                    noCouponShow.setVisibility(View.VISIBLE);
                }


                break;
            case TYPE_UNUSEABLE:
                setUnusableCoupons();
                if(shouldShowUnUsableCouponsBeanList.size()>0){
                    lrvDiscountList.setVisibility(View.VISIBLE);
                    noCouponShow.setVisibility(View.GONE);

                    if (unUsadleDiscountAdapter == null) {
                        lrvDiscountList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        unUsadleDiscountAdapter = new DiscountAdapter(R.layout.item_discount, shouldShowUnUsableCouponsBeanList);
                        unUsadleDiscountAdapter.setType(type);
                        lrvDiscountList.setAdapter(unUsadleDiscountAdapter);
                    } else {
                        unUsadleDiscountAdapter.setType(type);
                        unUsadleDiscountAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    lrvDiscountList.setVisibility(View.GONE);
                    noCouponShow.setVisibility(View.VISIBLE);
                }


                break;
        }

    }

    private void setUsableData() {
        shouldShowUsableCouponsBeanList.clear();
        for (int i = 0; i < usableCouponsBeanList.size(); i++) {
            couponsBean = new ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean();
            userCouponsBean = new ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean();
            userCouponsBean.setId(usableCouponsBeanList.get(i).getUserCoupons().getId());
            userCouponsBean.setUserId(usableCouponsBeanList.get(i).getUserCoupons().getUserId());
            userCouponsBean.setCouponInformationId(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformationId());
            userCouponsBean.setStatus(usableCouponsBeanList.get(i).getUserCoupons().getStatus());
            userCouponsBean.setGetTime(usableCouponsBeanList.get(i).getUserCoupons().getGetTime());
            couponInformationBean = new ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean.CouponInformationBean();
            couponInformationBean.setId(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getId());
            couponInformationBean.setIdentifier(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getIdentifier());
            couponInformationBean.setName(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getName());
            couponInformationBean.setPrice(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getPrice());
            couponInformationBean.setTotal(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getTotal());
            couponInformationBean.setUseLimit(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getUseLimit());
            couponInformationBean.setCount(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getCount());
            couponInformationBean.setState(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getState());
            couponInformationBean.setRules(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getRules());
            couponInformationBean.setBeginValidityTime(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getBeginValidityTime());
            couponInformationBean.setEndValidityTime(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getEndValidityTime());
            couponInformationBean.setBeginTime(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getBeginTime());
            couponInformationBean.setEndTime(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getEndTime());
            couponInformationBean.setOperatorIdentifier(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getOperatorIdentifier());
            couponInformationBean.setOperatorTime(usableCouponsBeanList.get(i).getUserCoupons().getCouponInformation().getOperatorTime());
            userCouponsBean.setCouponInformation(couponInformationBean);
            couponsBean.setUserCoupons(userCouponsBean);
            couponsBean.setAfterDiscountMoney(usableCouponsBeanList.get(i).getAfterDiscountMoney());
            shouldShowUsableCouponsBeanList.add(couponsBean);
        }
    }

    public void setUnusableCoupons() {
        shouldShowUnUsableCouponsBeanList.clear();
        if (usableCouponsBeanList.size() > 0) {
            for (int i = 0; i < usableCouponsBeanList.size(); i++) {
                int s = 0;
                for (s = 0; s < usableCouponBeans.size(); s++) {
                    if (usableCouponsBeanList.get(i).getUserCoupons().getId() == usableCouponBeans.get(s).getId()) {
                        usableCouponBeans.remove(s);
                        s--;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < usableCouponBeans.size(); i++) {
            couponsBean = new ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean();
            userCouponsBean = new ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean();
            userCouponsBean.setId(usableCouponBeans.get(i).getId());
            userCouponsBean.setUserId(usableCouponBeans.get(i).getUserId());
            userCouponsBean.setCouponInformationId(usableCouponBeans.get(i).getCouponInformationId());
            userCouponsBean.setStatus(usableCouponBeans.get(i).getStatus());
            userCouponsBean.setGetTime(usableCouponBeans.get(i).getGetTime());
            couponInformationBean = new ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean.CouponInformationBean();
            couponInformationBean.setId(usableCouponBeans.get(i).getCouponInformation().getId());
            couponInformationBean.setIdentifier(usableCouponBeans.get(i).getCouponInformation().getIdentifier());
            couponInformationBean.setName(usableCouponBeans.get(i).getCouponInformation().getName());
            couponInformationBean.setPrice(usableCouponBeans.get(i).getCouponInformation().getPrice());
            couponInformationBean.setTotal(usableCouponBeans.get(i).getCouponInformation().getTotal());
            couponInformationBean.setUseLimit(usableCouponBeans.get(i).getCouponInformation().getUseLimit());
            couponInformationBean.setCount(usableCouponBeans.get(i).getCouponInformation().getCount());
            couponInformationBean.setState(usableCouponBeans.get(i).getCouponInformation().getState());
            couponInformationBean.setRules(usableCouponBeans.get(i).getCouponInformation().getRules());
            couponInformationBean.setBeginValidityTime(usableCouponBeans.get(i).getCouponInformation().getBeginValidityTime());
            couponInformationBean.setEndValidityTime(usableCouponBeans.get(i).getCouponInformation().getEndValidityTime());
            couponInformationBean.setBeginTime(usableCouponBeans.get(i).getCouponInformation().getBeginTime());
            couponInformationBean.setEndTime(usableCouponBeans.get(i).getCouponInformation().getEndTime());
            couponInformationBean.setOperatorIdentifier(usableCouponBeans.get(i).getCouponInformation().getOperatorIdentifier());
            couponInformationBean.setOperatorTime(usableCouponBeans.get(i).getCouponInformation().getOperatorTime());
            userCouponsBean.setCouponInformation(couponInformationBean);
            couponsBean.setUserCoupons(userCouponsBean);
            shouldShowUnUsableCouponsBeanList.add(couponsBean);
        }

    }

    public void getCouponInfoByUserId(int userId) {
        Net.get().getCouponInfoByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userCouponInfos -> {
                    if (userCouponInfos.getCode() == 200) {
                        usableCouponBeans = userCouponInfos.getResultData().getUsableCoupon();
                        initDatas(type);
                    } else {
                        Toast.makeText(getContext(), userCouponInfos.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    public interface setOnClickCouponItem {
        void onClickCouponItem(int couponId, double price, String countMsg);
    }

    public interface OnGetCouponMsg {
        void onGetCouponMsg(int usableCouponNum,int unUsableCouonNum);
    }

}
