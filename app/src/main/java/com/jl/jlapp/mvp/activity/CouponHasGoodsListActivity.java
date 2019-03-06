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
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.CouponGoodsListAdapter;
import com.jl.jlapp.adapter.GoodsListAdapter;
import com.jl.jlapp.eneity.CouponMsgModel;
import com.jl.jlapp.eneity.GoodsListModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-30.
 */

public class CouponHasGoodsListActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.back)
    ImageView back;
    //加载框
    private ProgressDialog progressDialog;
    CouponGoodsListAdapter goodsListAdapter;
    List<CouponMsgModel.ResultDataBean.GoodsBean> resultDataBeans = new ArrayList<>();//保存接口数据
    int couponId = 0;
    int userId = 0;
    int activityId=0;//预售活动id
    String activityName="";//预售活动名称
    int whichOperation=0;//哪种操作  1是进详情，2是加入购物车

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_sort_goods_list_layout);

        //控制注解
        ButterKnife.bind(this);
        Tools.addActivity(this);

        Intent intent = getIntent();
        couponId = intent.getIntExtra("couponId", -1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //开启加载框
        buildProgressDialog();

        //获取接口数据
        if (couponId == -1) {
            Toast.makeText(this, "页面传值出错...", Toast.LENGTH_SHORT).show();
        } else {
            getGoodsList(couponId);
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

        //设置RecyclerView的布局方式
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //初始化适配器
        goodsListAdapter = new CouponGoodsListAdapter(R.layout.goods_list_item, resultDataBeans);
        //设置适配器
        recyclerView.setAdapter(goodsListAdapter);

        goodsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                whichOperation=1;
                TextView goodsIdTV = view.findViewById(R.id.goods_id);
                int goodsId = Integer.parseInt((String) goodsIdTV.getText());
                //该商品不是预售
                if(resultDataBeans.get(position).getGoodsSpecificationDetails().get(0).getGxcGoodsState()==2){
                    Intent intent = new Intent(CouponHasGoodsListActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goodsId);
                    startActivity(intent);
                }
                else{
                    //查出该商品参与的活动，获取结束时间离现在最近的一个
                    getPreSellActivityInformationByGoodsDetailsId(position,goodsId);
                }

            }
        });

        goodsListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                whichOperation=2;
                if (userId > 0) {
                    //该商品不是预售
                    if(resultDataBeans.get(position).getGoodsSpecificationDetails().get(0).getGxcGoodsState()==2){
                        activityId=0;
                        activityName="";
                        insertShoppingCart(userId, resultDataBeans.get(position).getId(), resultDataBeans.get(position).getGoodsSpecificationDetails().get(0).getId(), 1,activityId,activityName);
                    }
                    //该商品是预售，则要查出该商品参与的活动，获取结束时间离现在最近的一个
                    else{
                        //查出该商品参与的活动，获取结束时间离现在最近的一个
                        getPreSellActivityInformationByGoodsDetailsId(position,0);

                    }

                } else {
                    Toast.makeText(CouponHasGoodsListActivity.this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CouponHasGoodsListActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    public void getGoodsList(int couponId) {
        Net.get().getMsgByCouponId(couponId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(couponMsgModel -> {
                    cancelProgressDialog();
                    if (couponMsgModel.getCode() == 200) {
                        resultDataBeans = couponMsgModel.getResultData().getGoods();

                        if (couponMsgModel.getResultData().getFlag() == 1) {
                            setAdapter();
                        } else {
                            Tools.finishAll();
                            Intent intent = new Intent(CouponHasGoodsListActivity.this, BaseActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(this, "" + couponMsgModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //添加购物车
    public void insertShoppingCart(Integer userId, Integer goodsDetailsId, Integer goodsSpecificationDetailsId, Integer goodsNum, Integer activityId, String activityName) {
        Net.get().insertShoppingCart(userId, goodsDetailsId, goodsSpecificationDetailsId, goodsNum,activityId,activityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() != 200) {
                        Toast.makeText(this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "购物车添加成功!", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //根据商品id获取正在上线的且结束时间最早的预售活动信息
    public void getPreSellActivityInformationByGoodsDetailsId(Integer position,Integer goodsId) {
        Net.get().getPreSellActivityInformationByGoodsDetailsId(resultDataBeans.get(position).getId(),couponId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(presellMsg -> {
                    if (presellMsg.getCode() == 200) {
                        if(presellMsg.getResultData()!=null){
                            if(whichOperation==1){
                                Intent intent = new Intent(CouponHasGoodsListActivity.this, GoodsDetailActivity.class);

                                intent.putExtra("goodsId", goodsId);
                                intent.putExtra("activityId", presellMsg.getResultData().getId());
                                intent.putExtra("activityName", presellMsg.getResultData().getName());
                                startActivity(intent);
                            } else if (whichOperation==2) {
                                activityId=presellMsg.getResultData().getId();
                                activityName=presellMsg.getResultData().getName();
                                insertShoppingCart(userId, resultDataBeans.get(position).getId(), resultDataBeans.get(position).getGoodsSpecificationDetails().get(0).getId(), 1,activityId,activityName);
                            }

                        }
                        else{
                            Toast.makeText(this, "预售活动已结束", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, presellMsg.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }
}
