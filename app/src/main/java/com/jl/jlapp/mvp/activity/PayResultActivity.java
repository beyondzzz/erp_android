package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PayResultActivity extends Activity  {


    @BindView(R.id.tv_succsess_num)
    TextView tvSuccessNum;
    @BindView(R.id.rb_left)
    RadioButton returnHomePage;
    @BindView(R.id.rb_right)
    RadioButton seeOrder;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_pay);
        ButterKnife.bind(this);
        Tools.finishAll();
        Intent intent = getIntent();
        String orderNo = intent.getStringExtra("orderNo");
        int orderId = intent.getIntExtra("orderId",-1);
        int page=intent.getIntExtra("page",0);

        //从线下支付页面跳转而来
        if(orderNo!=null && orderNo != "" && orderId != -1&&page==1){
            tvResult.setText("提交成功");
            tvSuccessNum.setText("您的订单(订单号:"+orderNo+")已提交成功,正在审核。");
            returnHomePage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PayResultActivity.this,BaseActivity.class);
                    intent .putExtra("shouldReplaceFragment",0);
                    startActivity(intent);
                }
            });
            seeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PayResultActivity.this,OrderDetailActivity.class);
                    intent .putExtra("orderId",orderId);
                    startActivity(intent);
                }
            });
        }
        //从线上支付页面跳转而来
       else if(orderNo!=null && orderNo != "" && orderId != -1&&page==0){
            Net.get().shoppingBackCouponToUser(orderId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(commitVatInvoiceAptitudeToCheckModel -> {
                        if (commitVatInvoiceAptitudeToCheckModel.getCode() == 200) {
                            if("返券成功".equals(commitVatInvoiceAptitudeToCheckModel.getResultData())){
                                Toast.makeText(PayResultActivity.this, commitVatInvoiceAptitudeToCheckModel.getResultData(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PayResultActivity.this, commitVatInvoiceAptitudeToCheckModel.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {
                        Toast.makeText(PayResultActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                    });
            tvSuccessNum.setText("您的订单(订单号:"+orderNo+")已支付成功并提交仓库备货");
            returnHomePage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PayResultActivity.this,BaseActivity.class);
                    intent .putExtra("shouldReplaceFragment",0);
                    startActivity(intent);
                }
            });
            seeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PayResultActivity.this,OrderDetailActivity.class);
                    intent .putExtra("orderId",orderId);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this, "页面传值错误", Toast.LENGTH_SHORT).show();
        }



    }
}
