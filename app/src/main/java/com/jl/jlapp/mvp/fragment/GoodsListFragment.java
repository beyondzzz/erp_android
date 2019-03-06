package com.jl.jlapp.mvp.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.GoodsListAdapter;
import com.jl.jlapp.eneity.Goods;
import com.jl.jlapp.eneity.GoodsListModel;
import com.jl.jlapp.mvp.activity.GoodsDetailActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.mvp.activity.SearchGoodsListActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class GoodsListFragment extends Fragment {

    public static final String TAG = "aaaaaGoodsListFragment";

    View view;
    @BindView(R.id.show_goods_list_view)
    RecyclerView showGoodsListView;
    GoodsListAdapter goodsListAdapter;
    List<GoodsListModel.ResultDataBean> goodsListModels = new ArrayList<>();//保存接口数据

    SearchGoodsListActivity searchGoodsListActivity;

    public String userSearchWorld = "";//搜索页搜索的内容
    public String priceSort = "";//根据价格排序时 是倒序还是正序（asc：正序，desc：倒序）默认为""
    public Integer sortType = 1;//页面list排序(1:综合排序，2：销量排序，3：价格排序，4：热门分类)，默认传1
    public String isHasGoods = "true";//是否仅查看有货("true":是，"false"：否),默认传true
    public double minPrice = 0;//价格区间的最低价(不设置的话传0)，默认传0
    public double maxPrice = 0;//价格区间的最高价(不设置的话传0)，默认传0
    public String brandName = "all";//品牌名称(全部传 all)，默认传 all
    public Integer classificationId = 0;//二级分类id(全部传0)，默认传0

    //加载框
    private ProgressDialog progressDialog;
    int userId = 0;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        searchGoodsListActivity = (SearchGoodsListActivity) getActivity();

        userSearchWorld = searchGoodsListActivity.mSearchWorld;
        priceSort = searchGoodsListActivity.priceSort;
        sortType = searchGoodsListActivity.sortType;
        isHasGoods = searchGoodsListActivity.isHasGoods;
        minPrice = searchGoodsListActivity.minPrice;
        maxPrice = searchGoodsListActivity.maxPrice;
        brandName = searchGoodsListActivity.brandName;
        classificationId = searchGoodsListActivity.classificationId;
        Log.d(TAG, "sortType:" + sortType + " priceSort:" + priceSort
                + " userSearchWorld:" + userSearchWorld + " isHasGoods:" + isHasGoods
                + " minPrice:" + minPrice + " maxPrice:" + maxPrice + " brandName:" + brandName
                + " classificationId:" + classificationId);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_list, null, false);
        //控制注解
        ButterKnife.bind(this, view);
        Log.d(TAG, "userSearchWorld:" + userSearchWorld);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        //开启加载框
        buildProgressDialog();

        //获取接口数据
        getGoodsList(sortType, priceSort, userSearchWorld, isHasGoods, minPrice, maxPrice, brandName, classificationId);

    }

    public void setAdapter() {

        if (goodsListAdapter == null) {
            //设置RecyclerView的布局方式
            showGoodsListView.setLayoutManager(new LinearLayoutManager(getContext()));
            //初始化适配器
            goodsListAdapter = new GoodsListAdapter(R.layout.goods_list_item, goodsListModels);
            //设置适配器
            showGoodsListView.setAdapter(goodsListAdapter);

            goodsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    TextView goodsIdTV=view.findViewById(R.id.goods_id);
                    int goodsId=Integer.parseInt((String)goodsIdTV.getText());
                    Log.d("aaaaaaaaaaa","goodsId:"+goodsId);
                    Intent intent=new Intent(getContext(),GoodsDetailActivity.class);
                    intent.putExtra("goodsId",goodsId);
                    startActivity(intent);
                }
            });

            goodsListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (userId > 0) {
                        insertShoppingCart(userId, goodsListModels.get(position).getId(), goodsListModels.get(position).getGoodsSpecificationDetails().get(0).getId(), 1);
                    } else {
                        Toast.makeText(getContext(), "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
        } else {
            goodsListAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 加载框
     */
    public void buildProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
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

    //获取商品列表
    public void getGoodsList(int sortType, String priceSort, String searchName, String isHasGoods, Double minPrice, Double maxPrice, String brandName, int classificationId) {
        Net.get().getGoodsList(sortType, priceSort, searchName, isHasGoods, minPrice, maxPrice, brandName, classificationId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goodsListModel -> {
                    if(goodsListModel.getCode()==200){
                        goodsListModels = goodsListModel.getResultData();
                        cancelProgressDialog();
                        if (goodsListModels.size() > 0) {
                            setAdapter();
                        } else {
                            searchGoodsListActivity.replaceNoDataFrag();
                        }
                    }
                    else{
                        Toast.makeText(getContext(),"请求失败",Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(getContext(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });
    }

    //添加购物车
    public void insertShoppingCart(Integer userId, Integer goodsDetailsId, Integer goodsSpecificationDetailsId, Integer goodsNum) {
        Net.get().insertShoppingCart(userId, goodsDetailsId, goodsSpecificationDetailsId, goodsNum,0,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() != 200) {
                        Toast.makeText(getContext(), postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "购物车添加成功!", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

}
