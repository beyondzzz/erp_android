package com.jl.jlapp.mvp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.adapter.TracingListAdapter;
import com.jl.jlapp.eneity.OrderStateDetailModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.CustomLanearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-18.
 */

public class OrderTracingActivity extends AppCompatActivity{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    TracingListAdapter tracingListAdapter;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.order_state)
    TextView order_state;

    //加载框
    private ProgressDialog progressDialog;

    List<OrderStateDetailModel.ResultDataBean> dataList;
    int orderId=-1;
    int userId = -1;//获取App中存储的用户id


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);


        //开启加载框
        buildProgressDialog();

        orderId = getIntent().getIntExtra("orderId",-1);
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_tracing_activity);

        //控制注解
        ButterKnife.bind(this);



    }
    private void init(){
//        setAdapter();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String orderState = getIntent().getStringExtra("orderState");//获取订单状态
        order_state.setText(orderState);

        //加载数据
        initData();


    }
    public void setAdapter(){
        //设置RecyclerView的布局方式
        CustomLanearLayoutManager clm = new CustomLanearLayoutManager(this);
        clm.setScrollEnabled(false);
        recyclerView.setLayoutManager(clm);
        //初始化适配器
        tracingListAdapter=new TracingListAdapter(R.layout.tracing_list_item,dataList);
        //设置适配器
        recyclerView.setAdapter(tracingListAdapter);
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


    private List<OrderStateDetailModel.ResultDataBean> initData() {
//        List<OrderStateDetail> dataList = new ArrayList<>();
//        OrderStateDetail orderStateDetail;
//        for (int i=0; i<15; i++){
//            orderStateDetail = new OrderStateDetail();
//            orderStateDetail.setOrderStateDetail("状态信息"+i);
//            orderStateDetail.setAddTime("2018-01-19 11:11:11");
//            dataList.add(orderStateDetail);
//        }


        if(orderId != -1 && userId != -1){
            Net.get().getOrderStateDetail(orderId,userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(orderStateDetailModel -> {
                        //                    Log.d("aaaaaaaaaaaa","数据加载了");
                        int code = orderStateDetailModel.getCode();
                        if(code == 200){
                            dataList=orderStateDetailModel.getResultData();
                            setAdapter();
                            cancelProgressDialog();
                            //                        Toast.makeText(this,"size:"+dataList.size(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, ""+orderStateDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    },throwable -> {

                    });


        }else if(userId == -1){
//            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderTracingActivity.this,LoginActivity.class);
            startActivity(intent);
        }else if(orderId == -1){
            Toast.makeText(this, "页面传值没有接收到", Toast.LENGTH_SHORT).show();
        }
        return dataList;
    }

    }
