package com.jl.jlapp.mvp.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.OrderDetailsAdapter;
import com.jl.jlapp.eneity.Evaluate;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.utils.CustomLanearLayoutManager;
import com.jl.jlapp.utils.Tools;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UnderlineDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CHOOSE = 233;

    @BindView(R.id.rlv_order_details)
    RecyclerView rlvOrderDetails;
    @BindView(R.id.pic_1)
    ImageView imgPic1;
    @BindView(R.id.pic_2)
    ImageView imgPic2;

    List<Evaluate> models;

    double payMoney = -1;
    String orderNo = "";
    double postageMoney = -1;
    String payeeName = "";
    String payeeAccount = "";
    String payeeAccountDepositBank = "";
    int postagePayType = -1;
    String remitterName = "";
    String remitterAccount = "";
    int picNum = 0;
    String pic1 = "";
    String pic2 = "";

    int userId = 0;//获取App中存储的用户id

    //加载框
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_underline_detail);
        ButterKnife.bind(this);
        Tools.finishAll();

        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);

        Intent intent = getIntent();
        payMoney = intent.getDoubleExtra("payMoney", -1);
        orderNo = intent.getStringExtra("orderNo");
        postageMoney = intent.getDoubleExtra("postageMoney", -1);
        payeeName = intent.getStringExtra("payeeName");
        payeeAccount = intent.getStringExtra("payeeAccount");
        payeeAccountDepositBank = intent.getStringExtra("payeeAccountDepositBank");
        postagePayType = intent.getIntExtra("postagePayType", -1);
        remitterName = intent.getStringExtra("remitterName");
        remitterAccount = intent.getStringExtra("remitterAccount");
        picNum = intent.getIntExtra("picNum", -1);


        if (payMoney != -1 && postageMoney != -1 && (orderNo != null && !"".equals(orderNo)) && (payeeName != null && !"".equals(payeeName))
                && (payeeAccountDepositBank != null && !"".equals(payeeAccountDepositBank))
                && (payeeAccount != null && !"".equals(payeeAccount) && postagePayType != -1)
                && (remitterName != null && !"".equals(remitterName)) && (remitterAccount != null && !"".equals(remitterAccount))
                && picNum >= 1) {

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


            if (payMoney >= 50000) {
                setData();
                setAdapter();

            } else {
                Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "页面传值错误", Toast.LENGTH_SHORT).show();
        }


    }

    public void setData() {

        models = new ArrayList<>();
        String title[] = {"订单号：", "总支付金额：", "收款人：", "开户行：", "账号：","汇款人姓名：","汇款人账号："};
        String s[] = {orderNo, "¥" + payMoney, payeeName, payeeAccountDepositBank, payeeAccount,remitterName,remitterAccount};
        for (int i = 0; i < title.length; i++) {
            Evaluate model = new Evaluate();
            model.setTitle(title[i]);
            model.setScroe(s[i]);
            models.add(model);
        }

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
                Intent intent = new Intent(UnderlineDetailActivity.this, PhotoViewActivity.class);
                intent.putExtra("picUrl", pic1);
                startActivity(intent);
            }
        });
        imgPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnderlineDetailActivity.this, PhotoViewActivity.class);
                intent.putExtra("picUrl", pic2);
                startActivity(intent);
            }
        });


    }

    public void setAdapter() {
        CustomLanearLayoutManager layoutManager = new CustomLanearLayoutManager(this);
        layoutManager.setScrollEnabled(false);
        rlvOrderDetails.setLayoutManager(layoutManager);

        OrderDetailsAdapter detailsAdapter = new OrderDetailsAdapter(R.layout.item_order_details, models);
        rlvOrderDetails.setAdapter(detailsAdapter);
    }


    @OnClick({R.id.btn_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_return:
                finish();
                break;
        }
    }
}
