package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.mvp.fragment.DiscountFragment;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.popu.InvoiceAndCouponPromptPopu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fyf on 2018/1/16.
 */

public class DiscountActivity extends AppCompatActivity implements DiscountFragment.setOnClickCouponItem,DiscountFragment.OnGetCouponMsg{

    @BindView(R.id.tv_usable)
    TextView tvUsable;
    @BindView(R.id.tv_unUsable)
    TextView tvUnUsable;
    @BindView(R.id.lay_frame)
    FrameLayout layFrame;

    private DiscountFragment useAble;
    private DiscountFragment unUseAble;
    private InvoiceAndCouponPromptPopu invoiceAndCouponPromptPopu;

    int userId=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if(userId>0) {
            initView();
        }else{
            Toast.makeText(this,"您还未登录，请先登录。",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initView() {
        useAble = DiscountFragment.newInstance(DiscountFragment.TYPE_USEABLE,userId);
        unUseAble = DiscountFragment.newInstance(DiscountFragment.TYPE_UNUSEABLE,userId);
        useAble.setSetOnClickCouponItem(this);
        useAble.setOnGetCouponMsg(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_frame, useAble)
                .add(R.id.lay_frame, unUseAble)
                .show(useAble)
                .hide(unUseAble)
                .commitAllowingStateLoss();

        Drawable drawableBottom = getResources().getDrawable(
                R.drawable.splite_green);
        tvUsable.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, drawableBottom);
        tvUnUsable.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

        tvUsable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().show(useAble).hide(unUseAble).commitAllowingStateLoss();
                tvUsable.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableBottom);
                tvUnUsable.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }
        });
        tvUnUsable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().show(unUseAble).hide(useAble).commitAllowingStateLoss();
                tvUsable.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, null);
                tvUnUsable.setCompoundDrawablesWithIntrinsicBounds(null,null,null,drawableBottom);
            }
        });

    }

    @OnClick({R.id.return_btn,R.id.cupon_use_message})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cupon_use_message:
                invoiceAndCouponPromptPopu = new InvoiceAndCouponPromptPopu(this, 1);
                invoiceAndCouponPromptPopu.showAtLocation(findViewById(R.id.coupon_page), Gravity.CENTER, 0, 0);
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

    @Override
    public void onClickCouponItem(int couponId, double price, String countMsg) {
        Log.d("aaaaaonClickCouponItem","couponId:"+couponId+" price:"+price+" countMsg:"+countMsg);
        Intent intent=new Intent();
        intent.putExtra("couponId",couponId);
        intent.putExtra("price",price);
        intent.putExtra("countMsg",countMsg);

        setResult(3,intent);
        finish();
    }

    @Override
    public void onGetCouponMsg(int usableCouponNum, int unUsableCouonNum) {
        tvUsable.setText("可用优惠券("+usableCouponNum+")");
        tvUnUsable.setText("不可用优惠券("+unUsableCouonNum+")");
    }
}
