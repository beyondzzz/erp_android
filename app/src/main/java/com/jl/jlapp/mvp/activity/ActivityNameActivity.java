package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.ActivityNameCouponListAdapter;
import com.jl.jlapp.adapter.ActivityNameGoodsListAdapter;
import com.jl.jlapp.eneity.ActivityInformationByIdModel;
import com.jl.jlapp.eneity.AdvertisementByIdModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.CustomGridLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActivityNameActivity extends Activity{
    @BindView(R.id.activity_img)
    ImageView activityImg;
    @BindView(R.id.activity_introduce)
    TextView activityIntroduce;
    @BindView(R.id.activity_start_time)
    TextView activityStartTime;
    @BindView(R.id.activity_end_time)
    TextView activityEndTime;
    @BindView(R.id.activity_coupon_recycle_view)
    RecyclerView activityCouponRecycleView;
    @BindView(R.id.activity_goods_recycle_view)
    RecyclerView activityGoodsRecycleView;

    ActivityNameCouponListAdapter activityNameCouponListAdapter;
    ActivityNameGoodsListAdapter activityNameGoodsListAdapter;
    SharedPreferences sharedPreferences;
    List<String> strings;
    int activityInformationId = 0;
    int activityType = -1 ;
    String activityName = "";
    int userId = 0;
    List<ActivityInformationByIdModel.ResultDataBean.CouponInformationBean> couponInformationBeanList = new ArrayList<>();
    List<ActivityInformationByIdModel.ResultDataBean.GoodsBean> goodsBeanList = new ArrayList<>();
    int fromPage=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        Intent intent = getIntent();
        activityInformationId = intent.getIntExtra("activityInformationId",0);
        fromPage= intent.getIntExtra("isFromScreenActivity",-1);
        if(activityInformationId>0){
            getNetData();
        }else{
            Toast.makeText(ActivityNameActivity.this, "页面传值没有收到", Toast.LENGTH_SHORT).show();
        }


        setActivityNameCouponListAdapter();
        setActivityNameGoodsListAdapter();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            // 监控返回键
            if(fromPage == -1){//不是开屏广告跳转过来的
                finish();
            }else{//是开屏广告跳转过来的
                //跳转至首页
                Intent intent = new Intent(ActivityNameActivity.this, BaseActivity.class);
                finish();
                startActivity(intent);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @OnClick({R.id.icon_return})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.icon_return:
                // 监控返回键
                if(fromPage == -1){//不是开屏广告跳转过来的
                    finish();
                }else{//是开屏广告跳转过来的
                    //跳转至首页
                    Intent intent = new Intent(ActivityNameActivity.this, BaseActivity.class);
                    finish();
                    startActivity(intent);
                }
                break;
        }
    }

    private void setActivityNameCouponListAdapter(){

        activityCouponRecycleView.setLayoutManager(new CustomGridLayoutManager(this,3,false));
        activityNameCouponListAdapter=new ActivityNameCouponListAdapter(R.layout.activity_name_coupon_item_layout,couponInformationBeanList);
        activityCouponRecycleView.setAdapter(activityNameCouponListAdapter);
        activityNameCouponListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int couponId = couponInformationBeanList.get(position).getId();
                userId = sharedPreferences.getInt(AppFinal.USERID, 0);
                if(userId==0){//未登录
                    Toast.makeText(ActivityNameActivity.this,"您还未登录，请先登录。",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ActivityNameActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
//                Toast.makeText(ActivityNameActivity.this, ""+couponId, Toast.LENGTH_SHORT).show();
                Net.get().userGetCoupon(couponId,userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(commitVatInvoiceAptitudeToCheckModel -> {
                            if (commitVatInvoiceAptitudeToCheckModel.getCode() == 200) {
                                Toast.makeText(ActivityNameActivity.this, commitVatInvoiceAptitudeToCheckModel.getResultData(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityNameActivity.this, commitVatInvoiceAptitudeToCheckModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> {
                            Toast.makeText(ActivityNameActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    private void setActivityNameGoodsListAdapter(){

        activityGoodsRecycleView.setLayoutManager(new CustomGridLayoutManager(this,4,false));
        activityNameGoodsListAdapter=new ActivityNameGoodsListAdapter(R.layout.activity_name_goods_item_layout,goodsBeanList);
        activityGoodsRecycleView.setAdapter(activityNameGoodsListAdapter);
        activityNameGoodsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int goodsId = goodsBeanList.get(position).getId();

                Intent intent = new Intent(ActivityNameActivity.this, GoodsDetailActivity.class);
                if(activityType == 5){
                    intent.putExtra("activityId",activityInformationId);
                    intent.putExtra("activityName",activityName);
                }
                intent.putExtra("goodsId",goodsId);
                startActivity(intent);
            }
        });
    }



    private void getNetData(){
        Net.get().getActivityInformationById(activityInformationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activityInformationByIdModel -> {
                    if (activityInformationByIdModel.getCode() == 200) {
                        couponInformationBeanList =  activityInformationByIdModel.getResultData().getCouponInformation();
                        goodsBeanList =  activityInformationByIdModel.getResultData().getGoods();

                        activityType = activityInformationByIdModel.getResultData().getActivityInformation().getActivityType();
                        activityName = activityInformationByIdModel.getResultData().getActivityInformation().getName();

                        String picUrl = activityInformationByIdModel.getResultData().getActivityInformation().getShowPicUrl();
                        //如果无图片
                        if(picUrl==null || picUrl == ""){

                            Glide
                                    .with(ActivityNameActivity.this)
                                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_banner_top).error(R.drawable.img_noimg_banner_top))
                                    .load(R.drawable.img_noimg_banner_top)
                                    .into(activityImg);
                        }
                        //有图片
                        else{
                            Glide
                                    .with(ActivityNameActivity.this)
                                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_lose_banner_top).error(R.drawable.img_lose_banner_top))
                                    .load(AppFinal.BASEURL + picUrl)
                                    .into(activityImg);
                        }

                        activityIntroduce.setText(activityInformationByIdModel.getResultData().getActivityInformation().getIntroduction());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String starDate = sdf.format(activityInformationByIdModel.getResultData().getActivityInformation().getBeginValidityTime());
                        String endDate = sdf.format(activityInformationByIdModel.getResultData().getActivityInformation().getEndValidityTime());

                        activityStartTime.setText(starDate);
                        activityEndTime.setText(endDate);

                        setActivityNameCouponListAdapter();
                        setActivityNameGoodsListAdapter();
                    } else {
                        Toast.makeText(ActivityNameActivity.this, activityInformationByIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(ActivityNameActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });


    }




}
