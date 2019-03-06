package com.jl.jlapp.mvp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.ShouldEvaluationOrderListAdapter;
import com.jl.jlapp.eneity.OrderDetail;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.Tools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 柳亚婷 on 2018/2/9 0009.
 */

public class ShouldEvaluationActivity extends AppCompatActivity {

    @BindView(R.id.edit_evaluation_content_recycler_view)
    RecyclerView editEvaluationContentRecyclerView;
    @BindView(R.id.back)
    ImageView back;

    ShouldEvaluationOrderListAdapter shouldEvaluationOrderListAdapter;

    OrderDetail.ResultDataBean resultDataBean;
    List<OrderDetail.ResultDataBean.OrderDetailsBean> orderDetailsBeans;

    int orderId = 0;

    int userId = 0;

    //加载框
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_evaluation);
        ButterKnife.bind(this);

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if (userId > 0) {
            buildProgressDialog();
            Intent intent = getIntent();
            orderId = intent.getIntExtra("orderId", 0);

            getOrderDetailNoEvaluation(userId, orderId);
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

    public void setAdapter() {
        editEvaluationContentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shouldEvaluationOrderListAdapter = new ShouldEvaluationOrderListAdapter(R.layout.should_evaluation_list_item, orderDetailsBeans);
        editEvaluationContentRecyclerView.setAdapter(shouldEvaluationOrderListAdapter);

        shouldEvaluationOrderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.my_order_item_bottom_btn1:
                        Tools.addActivity(ShouldEvaluationActivity.this);
                        Intent intent = new Intent(ShouldEvaluationActivity.this, EditEvaluationActivity.class);
                        intent.putExtra("orderId",orderId);
                        intent.putExtra("orderDetailId", orderDetailsBeans.get(position).getId());
                        intent.putExtra("orderDetailPic", orderDetailsBeans.get(position).getGoodsCoverUrl());
                        intent.putExtra("orderDetailNum", orderDetailsBeans.size());
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    public void getOrderDetailNoEvaluation(Integer userId, Integer orderId) {
        Net.get().getOrderDetailNoEvaluation(orderId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetail -> {
                    cancelProgressDialog();
                    if (orderDetail.getCode() == 200) {
                        resultDataBean = orderDetail.getResultData();
                        if (resultDataBean.getOrderDetails().size() > 0) {
                            orderDetailsBeans = resultDataBean.getOrderDetails();
                            if(orderDetailsBeans.size()==1){
                                Tools.addActivity(this);
                            }
                            setAdapter();
                        } else {
                            Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, orderDetail.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }
}
