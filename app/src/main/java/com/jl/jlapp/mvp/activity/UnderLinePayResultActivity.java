package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jl.jlapp.R;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnderLinePayResultActivity extends Activity  {


    @BindView(R.id.tv_succsess_num)
    TextView tvSuccessNum;
    @BindView(R.id.rb_left)
    RadioButton returnHomePage;
    @BindView(R.id.rb_right)
    RadioButton seeOrder;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.pic_1)
    ImageView imgPic1;
    @BindView(R.id.pic_2)
    ImageView imgPic2;

    int picNum = 0;
    String pic1 = "";
    String pic2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_pay_underline);
        ButterKnife.bind(this);
        Tools.finishAll();
        Intent intent = getIntent();
        String orderNo = intent.getStringExtra("orderNo");
        int orderId = intent.getIntExtra("orderId",-1);
        int page=intent.getIntExtra("page",0);
        picNum = intent.getIntExtra("picNum", -1);
        if (picNum==1){
            pic1 = intent.getStringExtra("pic1");
            if ("".equals(pic1)){
                Toast.makeText(this, "页面传值错误", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            pic1 = intent.getStringExtra("pic1");
            pic2 = intent.getStringExtra("pic2");
            if ("".equals(pic1)||"".equals(pic2)){
                Toast.makeText(this, "页面传值错误", Toast.LENGTH_SHORT).show();
            }
        }
        //从线下支付页面跳转而来
        if(orderNo!=null && orderNo != "" && orderId != -1&&page==1){
            tvResult.setText("提交成功");
            tvSuccessNum.setText("您的订单(订单号:"+orderNo+")已提交成功,正在审核。");
            if (picNum==1&&!"".equals(pic1)){

                imgPic2.setVisibility(View.GONE);
                Glide
                        .with(this)
                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                        .load(AppFinal.BASEURL + pic1)
                        .into(imgPic1);
            }
            else if(picNum==2&&!"".equals(pic1)&&!"".equals(pic2)){

                imgPic2.setVisibility(View.VISIBLE);
                Glide
                        .with(this)
                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                        .load(AppFinal.BASEURL + pic1)
                        .into(imgPic1);
                Glide
                        .with(this)
                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                        .load(AppFinal.BASEURL + pic2)
                        .into(imgPic2);
            }

            imgPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UnderLinePayResultActivity.this, PhotoViewActivity.class);
                    intent.putExtra("picUrl", pic1);
                    startActivity(intent);
                }
            });
            imgPic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UnderLinePayResultActivity.this, PhotoViewActivity.class);
                    intent.putExtra("picUrl", pic2);
                    startActivity(intent);
                }
            });
            returnHomePage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UnderLinePayResultActivity.this,BaseActivity.class);
                    intent .putExtra("shouldReplaceFragment",0);
                    startActivity(intent);
                }
            });
            seeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UnderLinePayResultActivity.this,OrderDetailActivity.class);
                    intent .putExtra("orderId",orderId);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this, "页面传值错误", Toast.LENGTH_SHORT).show();
        }



    }
}
