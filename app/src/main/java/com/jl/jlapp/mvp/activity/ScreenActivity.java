package com.jl.jlapp.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.EffectAdvertisementByTypeModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-03-07.
 */

public class ScreenActivity  extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.page_img)
    ImageView img;
    List<EffectAdvertisementByTypeModel.ResultDataBean> advertisementListByType0 ;//保存开屏广告列表接口数据
    int screenAdId = 0;
    Runnable runnable;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        //控制注解
        ButterKnife.bind(this);
        init();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(ScreenActivity.this, BaseActivity.class);
                startActivity(intent);
                finish();
            }
        };




    }
    private void init(){
        Net.get().getEffectAdvertisementByType(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((EffectAdvertisementByTypeModel EffectAdvertisementByTypeModel) -> {
                    //                    Log.d("aaaaaaaaaaaa", "getAdvertisementGoodsListByType3数据加载了");
                    int code = EffectAdvertisementByTypeModel.getCode();
                    if (code == 200) {
                        advertisementListByType0 = EffectAdvertisementByTypeModel.getResultData();
                        if (advertisementListByType0.size() > 0) {
                            EffectAdvertisementByTypeModel.ResultDataBean item = advertisementListByType0.get(0);
                            screenAdId = item.getId();
                            String picUrl = item.getPicUrl();
                            //如果有图片
                            if (picUrl != null && picUrl != "") {
                                Glide
                                        .with(ScreenActivity.this)
                                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.screen).error(R.drawable.screen))
                                        .load(AppFinal.BASEURL + picUrl)
                                        .into(img);

                                setClickEvent();

                            }
                            //无图片
                            else {
                                Glide
                                        .with(ScreenActivity.this)
                                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.screen).error(R.drawable.screen))
                                        .load(R.drawable.screen)
                                        .into(img);
                            }
                        }
                        handler.postDelayed(runnable, 3000);
                    }else {
                        Toast.makeText(ScreenActivity.this, "" + EffectAdvertisementByTypeModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    //                    Log.d("bbbbbbbbbb", "getAdvertisementGoodsListByType3");
                    Toast.makeText(ScreenActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });

    }

    private void setClickEvent(){
        img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.page_img:
                handler.removeCallbacks(runnable);
                Net.get().getAdvertisementById(screenAdId).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(AdvertisementByIdModel -> {
                            int code1 = AdvertisementByIdModel.getCode();
                            if (code1 == 200) {
                                int flag = AdvertisementByIdModel.getResultData().getFlag();
                                switch (flag){
                                    case 0://商品详情页面    goods代表商品详情表信息
                                        if(AdvertisementByIdModel.getResultData().getGoods().size()>0){
                                            int goodsId = AdvertisementByIdModel.getResultData().getGoods().get(0).getId();
                                            Intent intent1 = new Intent(ScreenActivity.this, GoodsDetailActivity.class);
                                            intent1.putExtra("goodsId",goodsId);
                                            intent1.putExtra("isFromScreenActivity",1);
                                            startActivity(intent1);
                                            finish();
                                        }

                                        break;

                                    case 1://活动页面
                                        int activityInformationId = AdvertisementByIdModel.getResultData().getActivityInformation().getId();
                                        Intent intent=new Intent(ScreenActivity.this,ActivityNameActivity.class);
                                        intent.putExtra("activityInformationId",activityInformationId);
                                        intent.putExtra("isFromScreenActivity",1);
                                        startActivity(intent);

                                        finish();

                                        break;

                                }
                            } else {
                                Toast.makeText(ScreenActivity.this, "" + AdvertisementByIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                        }, throwable -> {
                            Toast.makeText(ScreenActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                        });

                break;
        }
    }
}
