package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.EvalDetailAdapter;
import com.jl.jlapp.adapter.GoodsListAdapter2;
import com.jl.jlapp.eneity.OrderDetail;
import com.jl.jlapp.eneity.OrderEvaluationDetailModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.CustomLanearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EvaluationDetailActivity extends Activity implements EvalDetailAdapter.OnClickGoodsPicItemListener{

    @BindView(R.id.evaluation_detail_recycler_view)
    RecyclerView evaluationDetailRecyclerView;
    @BindView(R.id.back)
    ImageView back;

    EvalDetailAdapter evalDetailAdapter;

    OrderEvaluationDetailModel.ResultDataBean resultDataBean;
    List<OrderEvaluationDetailModel.ResultDataBean.OrderDetailsBean> orderDetailsBeans;
    int userId=0;
    int orderId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_detail);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if(userId>0){
            Intent intent=getIntent();
            orderId=intent.getIntExtra("orderId",0);
            if(orderId>0){
                getEvaluationDetailByUserIdAndOrderId(userId,orderId);
            }
            else{
                Toast.makeText(this, "抱歉，数据出错。", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setAdapter(){
        if(evalDetailAdapter==null){
            evaluationDetailRecyclerView.setLayoutManager(new CustomLanearLayoutManager(this,false));
            evalDetailAdapter=new EvalDetailAdapter(R.layout.evaluation_submit_success_item,orderDetailsBeans);
            evalDetailAdapter.setOnClickGoodsPicItemListener(this);
            evaluationDetailRecyclerView.setAdapter(evalDetailAdapter);
        }
        else{
            evalDetailAdapter.notifyDataSetChanged();
        }

    }

    public void getEvaluationDetailByUserIdAndOrderId(Integer userId,Integer orderId) {
        Net.get().getEvaluationDetailByUserIdAndOrderId(orderId,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderEvaluationDetailModel -> {
                    if (orderEvaluationDetailModel.getCode() == 200) {
                        resultDataBean=new OrderEvaluationDetailModel.ResultDataBean();
                        resultDataBean=orderEvaluationDetailModel.getResultData();
                        orderDetailsBeans=new ArrayList<>();
                        if(resultDataBean.getOrderDetails().size()>0){
                            orderDetailsBeans=resultDataBean.getOrderDetails();

                        }
                        else{
                            Toast.makeText(this, "抱歉，数据出错。", Toast.LENGTH_SHORT).show();
                        }
                        setAdapter();

                    } else {
                        Toast.makeText(this, orderEvaluationDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    public void OnClickGoodsPicItem(String goodsPicUrl) {

        Intent intent = new Intent(EvaluationDetailActivity.this,PhotoViewActivity.class);
        intent.putExtra("picUrl",goodsPicUrl);
        startActivity(intent);
    }
}
