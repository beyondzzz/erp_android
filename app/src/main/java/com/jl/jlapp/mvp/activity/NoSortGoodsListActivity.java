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
import com.jl.jlapp.adapter.GoodsListAdapter;
import com.jl.jlapp.adapter.GoodsListAdapter4;
import com.jl.jlapp.eneity.AdvertisementByIdModel;
import com.jl.jlapp.eneity.GoodsListModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-30.
 */

public class NoSortGoodsListActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.back)
    ImageView back;
    //加载框
    private ProgressDialog progressDialog;
    GoodsListAdapter4 goodsListAdapter;
    List<AdvertisementByIdModel.ResultDataBean.GoodsBean> goodsListModels = new ArrayList<>();//保存接口数据
    int advertisementInformationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_sort_goods_list_layout);

        //控制注解
        ButterKnife.bind(this);

        Intent intent =getIntent();
        advertisementInformationId = intent.getIntExtra("advertisementInformationId",-1);
        String titleStr = intent.getStringExtra("title");

        title.setText(titleStr);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //开启加载框
        buildProgressDialog();

        //获取接口数据
        if(advertisementInformationId==-1){
            Toast.makeText(this, "页面传值出错...", Toast.LENGTH_SHORT).show();
        }else{
            getGoodsList(advertisementInformationId);

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
            goodsListAdapter = new GoodsListAdapter4(R.layout.goods_list_item, goodsListModels);
            //设置适配器
            recyclerView.setAdapter(goodsListAdapter);

            goodsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    TextView goodsIdTV=view.findViewById(R.id.goods_id);
                    int goodsId=Integer.parseInt((String)goodsIdTV.getText());
                    Intent intent=new Intent(NoSortGoodsListActivity.this,GoodsDetailActivity.class);
                    intent.putExtra("goodsId",goodsId);
                    startActivity(intent);
                }
            });
        goodsListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.goods_list_add_shop_cart:
                        SharedPreferences sharedPreferences = NoSortGoodsListActivity.this.getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                        int userId =  sharedPreferences.getInt(AppFinal.USERID, 0);
                        if (userId > 0) {
                            int goodsId = goodsListModels.get(position).getId();
                            int goodsSpeId = goodsListModels.get(position).getGoodsSpecificationDetails().get(0).getId();
                            insertShoppingCart(userId,goodsId,goodsSpeId,1);
                        } else {
                            Toast.makeText(NoSortGoodsListActivity.this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NoSortGoodsListActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                        break;
                }
            }
        });


    }

    public void getGoodsList(int advertisementInformationId) {
        Net.get().getAdvertisementById(advertisementInformationId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(AdvertisementByIdModel -> {
                    if(AdvertisementByIdModel.getCode()==200){
                        goodsListModels = AdvertisementByIdModel.getResultData().getGoods();
                        cancelProgressDialog();
                        if (goodsListModels.size() > 0) {
                            setAdapter();
                        }
                    }
                    else{
                        Toast.makeText(this,""+AdvertisementByIdModel.getMsg(),Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });
    }
    //添加购物车
    public void insertShoppingCart(Integer userId, Integer goodsDetailsId, Integer goodsSpecificationDetailsId, Integer goodsNum) {
        Net.get().insertShoppingCart(userId, goodsDetailsId, goodsSpecificationDetailsId, goodsNum,0,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() != 200) {
                        Toast.makeText(NoSortGoodsListActivity.this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NoSortGoodsListActivity.this, "购物车添加成功!", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(NoSortGoodsListActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }
}
