package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.ActivityNameCouponListAdapter;
import com.jl.jlapp.adapter.CouponCenterAdapter;
import com.jl.jlapp.adapter.UserUsableCouponAdapter;
import com.jl.jlapp.eneity.AllAvailableCouponModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.CustomGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-03-02.
 */

public class CouponCenterActivity  extends AppCompatActivity {

    @BindView(R.id.lrv_discount_list)
    RecyclerView lrvDiscountList;
    @BindView(R.id.no_coupon_show)
    RelativeLayout noCouponShow;
    int userId = 0;
    CouponCenterAdapter couponCenterAdapter;
    List<AllAvailableCouponModel.ResultDataBean> resultDataBeanList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_coupon_center);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        getNetData();

    }


    @OnClick({R.id.return_btn})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.return_btn:
                finish();
                break;
        }
    }
    private void getNetData(){

        Net.get().getAllAvailableCoupon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allAvailableCouponModel -> {
                    if (allAvailableCouponModel.getCode() == 200) {
                        resultDataBeanList = allAvailableCouponModel.getResultData();
                        if(resultDataBeanList.size()>0){
                            lrvDiscountList.setVisibility(View.VISIBLE);
                            noCouponShow.setVisibility(View.GONE);
                            setActivityNameCouponListAdapter();

                        }else{
                            lrvDiscountList.setVisibility(View.GONE);
                            noCouponShow.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(CouponCenterActivity.this, allAvailableCouponModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(CouponCenterActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });


    }

    private void setActivityNameCouponListAdapter(){

        lrvDiscountList.setLayoutManager(new LinearLayoutManager(CouponCenterActivity.this));
        couponCenterAdapter = new CouponCenterAdapter(R.layout.item_coupon, resultDataBeanList);
        lrvDiscountList.setAdapter(couponCenterAdapter);

        couponCenterAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    int couponId = resultDataBeanList.get(position).getId();
                    //                Toast.makeText(ActivityNameActivity.this, ""+couponId, Toast.LENGTH_SHORT).show();
                    Net.get().userGetCoupon(couponId,userId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(commitVatInvoiceAptitudeToCheckModel -> {
                                if (commitVatInvoiceAptitudeToCheckModel.getCode() == 200) {
                                    Toast.makeText(CouponCenterActivity.this, commitVatInvoiceAptitudeToCheckModel.getResultData(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CouponCenterActivity.this, commitVatInvoiceAptitudeToCheckModel.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }, throwable -> {
                                Toast.makeText(CouponCenterActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                            });
                }
        });
    }
}
