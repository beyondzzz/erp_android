package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.adapter.GoodsDetailPageAdapter;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.mvp.fragment.GoodsDetailFragment;
import com.jl.jlapp.mvp.fragment.GoodsEvaluationFragment;
import com.jl.jlapp.mvp.fragment.GoodsMsgFragment;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ChooseGoodsSpecPopu;
import com.jl.jlapp.popu.GoodsDetailActivityChooseGoodsSpecPopu;
import com.jl.jlapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GoodsDetailActivity extends FragmentActivity implements GoodsMsgFragment.SetOnClickLookGoodsSpec, View.OnClickListener, RadioGroup.OnCheckedChangeListener,GoodsDetailActivityChooseGoodsSpecPopu.OnClickOkBtnListener {

    @BindView(R.id.goods_detail_rela)
    RelativeLayout goodsDetailRela;
    @BindView(R.id.goods_msg_top_radiogroup)
    RadioGroup goodsMsgTopRadiogroup;
    @BindView(R.id.goods_msg_top_goods_radiobutton)
    RadioButton goodsMsgTopGoodsRadiobutton;
    @BindView(R.id.goods_msg_top_detail_radiobutton)
    RadioButton goodsMsgTopDetailRadiobutton;
    @BindView(R.id.goods_msg_top_evaluation_radiobutton)
    RadioButton goodsMsgTopEvaluationRadiobutton;
    @BindView(R.id.tv_contact_service)
    TextView tvContactservice;
    @BindView(R.id.return_img)
    ImageView returnImg;
    @BindView(R.id.tv_shop_cart)
    TextView tvShopCart;
    @BindView(R.id.tv_add_shop_cart)
    TextView tvAddShopCart;
    @BindView(R.id.tv_buy_now)
    TextView tvBuyNow;
    @BindView(R.id.shop_detail_avtivity_center_view_pager)
    ViewPager shopDetailAvtivityCenterViewPager;
    @BindView(R.id.search_goods_detail_error)
    TextView searchGoodsDetailError;

    List<RadioButton> radioButtons;
    List<Fragment> fragments;
    public GoodsDetailModel.ResultDataBean resultDataBeanMsg = null;
    public Double goodEvaluationRating = 0.0;
    public Integer goodEvalNum = 0;
    public Integer middleEvalNum = 0;
    public Integer discrepancyEvalNum = 0;
    public Integer hasPicNum = 0;
    public double compositeScores = 0;
    public Integer goodsId = 0;

    public Integer goodsSpecificationId = 0;//选中的规格id

    Integer isCheckBuyNowOrAddShopCart=0;//0表示加入购物车 1表示立即购买

    public Integer activityId;//预售活动id
    public String activityName;//预售活动名称

    //加载碎片
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    /*碎片*/
    Fragment goodsMsgFragment, goodsDetailFragment, goodsEvaluationFragment;
    GoodsDetailPageAdapter goodsDetailPageAdapter;

    GoodsDetailModel.ResultDataBean goodsDetailDataBean;

    GoodsDetailActivityChooseGoodsSpecPopu goodsDetailActivityChooseGoodsSpecPopu;
    int userId = 0;
    int fromPage=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);

        Intent intent = getIntent();
        goodsId = intent.getIntExtra("goodsId", 0);
        activityId = intent.getIntExtra("activityId",0);
        fromPage= intent.getIntExtra("isFromScreenActivity",-1);
        if (activityId>0){
            activityName=intent.getStringExtra("activityName");
        }
        else{
            activityName="";
        }

        getGoodsDetailMsgByGoodsId();

        setClickListener();


    }



    public void setClickListener() {
        goodsMsgTopRadiogroup.setOnCheckedChangeListener(this);
        returnImg.setOnClickListener(this);
        tvContactservice.setOnClickListener(this);
        tvShopCart.setOnClickListener(this);
        tvAddShopCart.setOnClickListener(this);
        tvBuyNow.setOnClickListener(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            // 监控返回键
            if(fromPage == -1){//不是开屏广告跳转过来的
                finish();
            }else{//是开屏广告跳转过来的
                //跳转至首页
                Intent intent = new Intent(GoodsDetailActivity.this, BaseActivity.class);
                finish();
                startActivity(intent);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回按钮
            case R.id.return_img:
                if(fromPage == -1){//不是开屏广告跳转过来的
                    finish();
                }else{//是开屏广告跳转过来的
                    //跳转至首页
                    Intent intent = new Intent(GoodsDetailActivity.this, BaseActivity.class);
                    finish();
                    startActivity(intent);
                }
                break;
            //联系客服按钮
            case R.id.tv_contact_service:
                if (userId > 0) {
                    Intent intent2 = new Intent(this, CustomerServiceActivity.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            //购物车按钮
            case R.id.tv_shop_cart:
                if (userId > 0) {
                    Intent intent1 = new Intent(GoodsDetailActivity.this, BaseActivity.class);
                    intent1.putExtra("shouldReplaceFragment", 2);
                    startActivity(intent1);
                } else {
                    Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            //添加购物车按钮
            case R.id.tv_add_shop_cart:
                if (userId > 0) {
                    //insertShoppingCart(userId, goodsId, goodsSpecificationId, 1);
                    isCheckBuyNowOrAddShopCart=0;
                    Log.d("aaaaaaagd","length:"+goodsDetailDataBean.getGoodsSpecificationDetails().size()+" 值："+goodsDetailDataBean.getGoodsSpecificationDetails().get(0).getSpecifications());
                    goodsDetailActivityChooseGoodsSpecPopu = new GoodsDetailActivityChooseGoodsSpecPopu(this, goodsDetailDataBean.getGoodsSpecificationDetails().get(0).getSpecifications(),  goodsDetailDataBean);
                    goodsDetailActivityChooseGoodsSpecPopu.showAtLocation(findViewById(R.id.goods_msg_page), Gravity.BOTTOM, 0, 0);
                    goodsDetailActivityChooseGoodsSpecPopu.setOnClickOkBtnListener(this);
                } else {
                    Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_buy_now:
                if (userId > 0) {
                    isCheckBuyNowOrAddShopCart=1;
                    Tools.addActivity(this);
                    goodsDetailActivityChooseGoodsSpecPopu = new GoodsDetailActivityChooseGoodsSpecPopu(this, goodsDetailDataBean.getGoodsSpecificationDetails().get(0).getSpecifications(),  goodsDetailDataBean);
                    goodsDetailActivityChooseGoodsSpecPopu.showAtLocation(findViewById(R.id.goods_msg_page), Gravity.BOTTOM, 0, 0);
                    goodsDetailActivityChooseGoodsSpecPopu.setOnClickOkBtnListener(this);
                } else {
                    Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }

    //回调方法 点击加入购物车或者立即购买选择规格底部的确认按钮
    @Override
    public void OnClickOkBtn(Integer goodsId, Integer goodsSpecId, Integer goodsNum,Integer isPresell) {
        goodsDetailActivityChooseGoodsSpecPopu.dismiss();
        if (isCheckBuyNowOrAddShopCart==0){
            insertShoppingCart(userId, goodsId, goodsSpecId, goodsNum);
        }
        else{
            Intent intent = new Intent(GoodsDetailActivity.this, OrderSubmitActivity.class);
            intent.putExtra("isFromWhichPage", 1);
            intent.putExtra("buyNum", goodsNum);
            intent.putExtra("goodsSpecId", goodsSpecId);
            intent.putExtra("goodsId", goodsId);
            intent.putExtra("isPresell", isPresell);
            if(isPresell==1){
                intent.putExtra("activityId", activityId);
            }
            startActivity(intent);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        for (int r = 0; r < radioButtons.size(); r++) {
            if (radioButtons.get(r).getId() == i) {

                radioButtons.get(r).setTextColor(getResources().getColor(R.color.checkGreenColor));
                Drawable drawableBottom = getResources().getDrawable(
                        R.drawable.line_green_s);
                radioButtons.get(r).setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableBottom);
                radioButtons.get(r).setChecked(true);

                //替换碎片
                switch (i) {
                    case R.id.goods_msg_top_goods_radiobutton:

                        shopDetailAvtivityCenterViewPager.setCurrentItem(0);
                        break;
                    case R.id.goods_msg_top_detail_radiobutton:

                        shopDetailAvtivityCenterViewPager.setCurrentItem(1);
                        break;
                    case R.id.goods_msg_top_evaluation_radiobutton:

                        shopDetailAvtivityCenterViewPager.setCurrentItem(2);
                        break;
                }
            } else {
                Drawable drawableBottom = getResources().getDrawable(
                        R.drawable.line_white_s);
                radioButtons.get(r).setChecked(false);
                radioButtons.get(r).setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableBottom);
                radioButtons.get(r).setTextColor(getResources().getColor(R.color.trans_333333));
            }
        }
    }

    private void setData() {
        radioButtons = new ArrayList<>();
        radioButtons.add(goodsMsgTopGoodsRadiobutton);
        radioButtons.add(goodsMsgTopDetailRadiobutton);
        radioButtons.add(goodsMsgTopEvaluationRadiobutton);

        if (goodsMsgFragment == null) {
            goodsMsgFragment = new GoodsMsgFragment();
        }
        if (goodsDetailFragment == null) {
            goodsDetailFragment = new GoodsDetailFragment();
        }
        if (goodsEvaluationFragment == null) {
            goodsEvaluationFragment = new GoodsEvaluationFragment();
        }


        fragments = new ArrayList<>();
        fragments.add(goodsMsgFragment);
        fragments.add(goodsDetailFragment);
        fragments.add(goodsEvaluationFragment);

        goodsDetailPageAdapter = new GoodsDetailPageAdapter(getSupportFragmentManager(), fragments);
        shopDetailAvtivityCenterViewPager.setAdapter(goodsDetailPageAdapter);
        shopDetailAvtivityCenterViewPager.setCurrentItem(0);

        Drawable drawableBottom1 = getResources().getDrawable(
                R.drawable.line_green_s);
        Drawable drawableBottom2 = getResources().getDrawable(
                R.drawable.line_white_s);
        goodsMsgTopGoodsRadiobutton.setTextColor(getResources().getColor(R.color.checkGreenColor));
        goodsMsgTopGoodsRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, drawableBottom1);
        goodsMsgTopGoodsRadiobutton.setChecked(true);
        goodsMsgTopDetailRadiobutton.setChecked(false);
        goodsMsgTopDetailRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, drawableBottom2);
        goodsMsgTopDetailRadiobutton.setTextColor(getResources().getColor(R.color.trans_333333));
        goodsMsgTopEvaluationRadiobutton.setChecked(false);
        goodsMsgTopEvaluationRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, drawableBottom2);
        goodsMsgTopEvaluationRadiobutton.setTextColor(getResources().getColor(R.color.trans_333333));

        shopDetailAvtivityCenterViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.d("aaaaaGoods", "position:" + position);
                Drawable drawableBottom1 = getResources().getDrawable(
                        R.drawable.line_green_s);
                Drawable drawableBottom2 = getResources().getDrawable(
                        R.drawable.line_white_s);
                if (position == 0) {
                    goodsMsgTopGoodsRadiobutton.setTextColor(getResources().getColor(R.color.checkGreenColor));
                    goodsMsgTopGoodsRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, drawableBottom1);
                    goodsMsgTopGoodsRadiobutton.setChecked(true);
                    goodsMsgTopDetailRadiobutton.setChecked(false);
                    goodsMsgTopDetailRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, drawableBottom2);
                    goodsMsgTopDetailRadiobutton.setTextColor(getResources().getColor(R.color.trans_333333));
                    goodsMsgTopEvaluationRadiobutton.setChecked(false);
                    goodsMsgTopEvaluationRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, drawableBottom2);
                    goodsMsgTopEvaluationRadiobutton.setTextColor(getResources().getColor(R.color.trans_333333));
                } else if (position == 1) {
                    goodsMsgTopDetailRadiobutton.setTextColor(getResources().getColor(R.color.checkGreenColor));
                    goodsMsgTopDetailRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, drawableBottom1);
                    goodsMsgTopDetailRadiobutton.setChecked(true);
                    goodsMsgTopGoodsRadiobutton.setChecked(false);
                    goodsMsgTopGoodsRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, drawableBottom2);
                    goodsMsgTopGoodsRadiobutton.setTextColor(getResources().getColor(R.color.trans_333333));
                    goodsMsgTopEvaluationRadiobutton.setChecked(false);
                    goodsMsgTopEvaluationRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, drawableBottom2);
                    goodsMsgTopEvaluationRadiobutton.setTextColor(getResources().getColor(R.color.trans_333333));
                } else if (position == 2) {
                    goodsMsgTopEvaluationRadiobutton.setTextColor(getResources().getColor(R.color.checkGreenColor));
                    goodsMsgTopEvaluationRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, drawableBottom1);
                    goodsMsgTopEvaluationRadiobutton.setChecked(true);
                    goodsMsgTopGoodsRadiobutton.setChecked(false);
                    goodsMsgTopGoodsRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, drawableBottom2);
                    goodsMsgTopGoodsRadiobutton.setTextColor(getResources().getColor(R.color.trans_333333));
                    goodsMsgTopDetailRadiobutton.setChecked(false);
                    goodsMsgTopDetailRadiobutton.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, drawableBottom2);
                    goodsMsgTopDetailRadiobutton.setTextColor(getResources().getColor(R.color.trans_333333));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //回调函数，点击商品页最下面的点击查看详情按钮，替换碎片
    @Override
    public void onClickLookGoodsSpec() {

        for (int i = 0; i < radioButtons.size(); i++) {
            if (i == 1) {
                radioButtons.get(i).setTextColor(getResources().getColor(R.color.checkGreenColor));
                Drawable drawableBottom = getResources().getDrawable(
                        R.drawable.line_green_s);
                radioButtons.get(i).setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableBottom);
                radioButtons.get(i).setChecked(true);
            } else {
                Drawable drawableBottom = getResources().getDrawable(
                        R.drawable.line_white_s);
                radioButtons.get(i).setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableBottom);
                radioButtons.get(i).setTextColor(getResources().getColor(R.color.trans_333333));
                radioButtons.get(i).setChecked(false);
            }
        }

        shopDetailAvtivityCenterViewPager.setCurrentItem(1);
    }

    public void onClickLookEvaluation() {

        for (int i = 0; i < radioButtons.size(); i++) {
            if (i == 2) {
                radioButtons.get(i).setTextColor(getResources().getColor(R.color.checkGreenColor));
                Drawable drawableBottom = getResources().getDrawable(
                        R.drawable.line_green_s);
                radioButtons.get(i).setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableBottom);
                radioButtons.get(i).setChecked(true);
            } else {
                Drawable drawableBottom = getResources().getDrawable(
                        R.drawable.line_white_s);
                radioButtons.get(i).setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableBottom);
                radioButtons.get(i).setTextColor(getResources().getColor(R.color.trans_333333));
                radioButtons.get(i).setChecked(false);
            }
        }


        shopDetailAvtivityCenterViewPager.setCurrentItem(2);

    }

    //判断是否有无库存的商品，如果无，则移除
    private void getHasStrockGoods(){
        List<GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean> beans = new ArrayList<>();
        for (int i = 0; i < goodsDetailDataBean.getGoodsSpecificationDetails().size(); i++) {
            if (goodsDetailDataBean.getZeroStock()==0&&goodsDetailDataBean.getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2) {
                if (goodsDetailDataBean.getGoodsSpecificationDetails().get(i).getGxcGoodsStock() <= 0) {
                    goodsDetailDataBean.getGoodsSpecificationDetails().remove(i);
                    i--;
                }
            }
        }

    }

    //获取商品详情信息
    public void getGoodsDetailMsgByGoodsId() {

        Net.get().getGoodsDetailMsgByGoodsId(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goodsDetailModel -> {
                    //  Log.d("aaaaaaaaa", "goodsDetailModelSize:" + goodsDetailModel.getResultData().getGoodsSpecificationDetails().size());
                    if (goodsDetailModel.getCode() == 200) {
                        goodsDetailDataBean = goodsDetailModel.getResultData();

                        if (goodsDetailDataBean != null) {
                            getHasStrockGoods();

                        } else {
                            Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
                        }
                        setData();
                    } else {
                        goodsDetailRela.setVisibility(View.GONE);
                        searchGoodsDetailError.setVisibility(View.VISIBLE);
                        //Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


    //添加购物车
    public void insertShoppingCart(Integer userId, Integer goodsDetailsId, Integer goodsSpecificationDetailsId, Integer goodsNum) {
        Net.get().insertShoppingCart(userId, goodsDetailsId, goodsSpecificationDetailsId, goodsNum,activityId,activityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() != 200) {
                        Toast.makeText(this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "添加成功!", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }



}
