package com.jl.jlapp.mvp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.CenterAdapter;
import com.jl.jlapp.eneity.Order;
import com.jl.jlapp.eneity.OrderListModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 评价中心
 * Created by fyf on 2018/1/20.
 */

public class CenterActivity extends AppCompatActivity {

    public static final String TAG = "CenterActivity";

    @BindView(R.id.rlv_enter)
    RecyclerView rlvEnter;
    @BindView(R.id.no_should_evaluation_order_rela)
    RelativeLayout noShouldEvaluationOrderRela;
    @BindView(R.id.return_btn)
    ImageView returnBtn;

    CenterAdapter adapter;

    List<Order> orderList;
    List<OrderListModel.ResultDataBean> orderListDataBeans;
    List<OrderListModel.ResultDataBean> shouldShowDataBeanList;

    int userId = 0;

    //加载框
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.actvity_center);
        ButterKnife.bind(this);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("aaaaaaaaMyOrder", "onResume");
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if (userId > 0) {
            buildProgressDialog();
            getOrderList(userId);
        } else {
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    /**
     * 加载框
     */
    public void buildProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage("加载中");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * @Description: TODO 取消加载框
     */
    public void cancelProgressDialog() {
        if (progressDialog != null)
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
    }


    private void setAdapter() {

        if(adapter==null){
            rlvEnter.setLayoutManager(new LinearLayoutManager(this));

            adapter = new CenterAdapter(R.layout.my_order_list_item, shouldShowDataBeanList);
            rlvEnter.setAdapter(adapter);
        }
        else{
            adapter.notifyDataSetChanged();
        }

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(CenterActivity.this, OrderDetailActivity.class);
                intent.putExtra("orderId", shouldShowDataBeanList.get(position).getId());
                startActivity(intent);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView tv=view.findViewById(R.id.my_order_item_bottom_btn1);
                switch (view.getId()) {
                    case R.id.my_order_item_bottom_btn1:
                        if("评价晒单".equals(tv.getText().toString().trim())){
                            Intent intent1 = new Intent(CenterActivity.this, ShouldEvaluationActivity.class);
                            intent1.putExtra("orderId", shouldShowDataBeanList.get(position).getId());
                            startActivity(intent1);

                        }
                        else if ("评价详情".equals(tv.getText().toString().trim())){
                            Intent intent2 = new Intent(CenterActivity.this, EvaluationDetailActivity.class);
                            intent2.putExtra("orderId", shouldShowDataBeanList.get(position).getId());
                            startActivity(intent2);
                        }
                        break;
                }
            }
        });


    }



    //从结果集中根据需要展示的订单状态 遍历出需要展示的订单列表
    public void getShouldShowDataListByOrderState(List<OrderListModel.ResultDataBean> orderListDataBeans) {
        if (shouldShowDataBeanList == null) {
            shouldShowDataBeanList = new ArrayList<>();
        }
        shouldShowDataBeanList.clear();
        for (int i = 0; i < orderListDataBeans.size(); i++) {
            //已完成，已换货，关闭售后
            if (orderListDataBeans.get(i).getOrderState() == 3||orderListDataBeans.get(i).getOrderState() == 10||orderListDataBeans.get(i).getOrderState() == 8) {
                shouldShowDataBeanList.add(orderListDataBeans.get(i));
            }

        }
    }

    //获取用户的订单列表
    public void getOrderList(Integer userId) {
        Net.get().getOrderList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderListModel -> {
                    cancelProgressDialog();
                    if (orderListModel.getCode() == 200) {

                        orderListDataBeans = orderListModel.getResultData();
                        if (orderListDataBeans.size() > 0) {
                            getShouldShowDataListByOrderState(orderListDataBeans);
                            if (shouldShowDataBeanList.size() > 0) {
                                setAdapter();
                            } else {
                                noShouldEvaluationOrderRela.setVisibility(View.VISIBLE);
                                rlvEnter.setVisibility(View.GONE);
                            }

                        } else {
                            noShouldEvaluationOrderRela.setVisibility(View.VISIBLE);
                            rlvEnter.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(this, orderListModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

}
