package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.OrderMessageListAdapter;
import com.jl.jlapp.eneity.OrderListModel;
import com.jl.jlapp.eneity.OrderMessageListModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderMessageListActivity extends Activity {

    @BindView(R.id.order_message_recycler_view)
    RecyclerView orderMessageRecyclerView;
    @BindView(R.id.no_message_linear)
    LinearLayout noMessageLinear;

    OrderMessageListAdapter orderMessageListAdapter;
    List<OrderMessageListModel.ResultDataBean> resultDataBeanList=new ArrayList<>();
    List<OrderMessageListModel.ResultDataBean> shouldShowResultDataBeanList=new ArrayList<>();
    //加载框
    private ProgressDialog progressDialog;

    int userId = -1;//获取App中存储的用户id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_message);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        //开启加载框
        buildProgressDialog();
        if(userId>0){
            getOrderStateDetailList(userId);
        }
        else{
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @OnClick({R.id.icon_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_return:
                finish();
                break;
        }
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (orderMessageListAdapter == null) {

            orderMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            orderMessageListAdapter = new OrderMessageListAdapter(R.layout.order_message_list_item_layout, shouldShowResultDataBeanList);
            orderMessageRecyclerView.setAdapter(orderMessageListAdapter);
        } else {
            orderMessageListAdapter.notifyDataSetChanged();
        }

        orderMessageListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转页面
                Intent intent=new Intent(OrderMessageListActivity.this,OrderDetailActivity.class);
                intent.putExtra("orderId",shouldShowResultDataBeanList.get(position).getOrderId());
                startActivity(intent);
            }
        });

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

    /**
     * 模拟数据源
     */
    /*private void getData() {
        resultDataBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            resultDataBean = new OrderListModel.ResultDataBean();
            resultDataBeanList.add(resultDataBean);
        }
    }*/

    private void getShouldShowList(List<OrderMessageListModel.ResultDataBean> resultDataBeanList){
        shouldShowResultDataBeanList.clear();
        for (int i = 0; i < resultDataBeanList.size(); i++) {
            shouldShowResultDataBeanList.add(resultDataBeanList.get(i));
        }
    }

    //获取用户的订单状态消息列表
    public void getOrderStateDetailList(Integer userId) {
        Net.get().getOrderStateDetailList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderMessageListModel -> {
                    cancelProgressDialog();
                    resultDataBeanList.clear();
                    if (orderMessageListModel.getCode() == 200) {
                        resultDataBeanList = orderMessageListModel.getResultData();
                        getShouldShowList(resultDataBeanList);
                        if (resultDataBeanList.size() > 0) {
                            noMessageLinear.setVisibility(View.GONE);
                            orderMessageRecyclerView.setVisibility(View.VISIBLE);
                            setAdapter();
                        } else {
                            noMessageLinear.setVisibility(View.VISIBLE);
                            orderMessageRecyclerView.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(this, orderMessageListModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


}
