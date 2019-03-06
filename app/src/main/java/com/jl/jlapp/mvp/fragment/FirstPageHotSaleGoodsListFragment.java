package com.jl.jlapp.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.adapter.GoodsListAdapter2;
import com.jl.jlapp.eneity.AdvertisementByIdModel;
import com.jl.jlapp.mvp.activity.GoodsDetailActivity;
import com.jl.jlapp.utils.CustomGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by THINK on 2018-01-17.
 */

public class FirstPageHotSaleGoodsListFragment extends Fragment {
    View view;
    @BindView(R.id.show_goods_list_view)
    RecyclerView showGoodsListView;
    GoodsListAdapter2 goodsListAdapter;

    int page;
    List<AdvertisementByIdModel.ResultDataBean.GoodsBean> goodsList;

    Fragment_FirstPages fragment_firstPages = new Fragment_FirstPages();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.first_page_hot_goods_fragment, null, false);
        //控制注解
        ButterKnife.bind(this,view);

        //展示的是第几页
        page = getArguments().getInt("page");
        //获取商品列表
//         goodsList =  ( List<AdvertisementByIdModel.ResultDataBean.GoodsBean>)getArguments().getSerializable("data");
        goodsList = fragment_firstPages.promotionGoodsList;
//        Log.d("bbb", "page:"+page+"    name:"+goodsList.get(0).getName());
//        getData();
        setAdapter();

        return view;
    }


    public void setAdapter(){
        //设置RecyclerView的布局方式
        showGoodsListView.setLayoutManager(new CustomGridLayoutManager(getActivity(),3,false));
//        //初始化适配器
//        goodsListAdapter=new GoodsListAdapter3(R.layout.goods_list_item2,getDataByPage(page));
//        //设置适配器
//        showGoodsListView.setAdapter(goodsListAdapter);



        //初始化适配器
        goodsListAdapter=new GoodsListAdapter2(getActivity(),getDataByPage(page));
        //设置适配器
        showGoodsListView.setAdapter(goodsListAdapter);

        goodsListAdapter.setOnItemClickListener(new GoodsListAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AdvertisementByIdModel.ResultDataBean.GoodsBean data) {

//                Toast.makeText(getActivity(),"goodsName:"+data.getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("goodsId",data.getId());
                startActivity(intent);
            }
        });
    }

    private  List<AdvertisementByIdModel.ResultDataBean.GoodsBean> getDataByPage(int page){
        List<AdvertisementByIdModel.ResultDataBean.GoodsBean> pageData = new ArrayList<>();
        switch (page){
            case 1:

                for (int i=0;i<3;i++){
                    if(i<goodsList.size()){
                        AdvertisementByIdModel.ResultDataBean.GoodsBean item = goodsList.get(i);
                        pageData.add(item);
                    }

                }
                break;
            case 2:
                for (int i=3;i<6;i++){
                    if(i<goodsList.size()){
                        AdvertisementByIdModel.ResultDataBean.GoodsBean item = goodsList.get(i);
                        pageData.add(item);
                    }

                }
                break;
            case 3:
                for (int i=6;i<9;i++){
                    if(i<goodsList.size()){
                        AdvertisementByIdModel.ResultDataBean.GoodsBean item = goodsList.get(i);
                        pageData.add(item);
                    }
                }
                break;
            default:
                break;
        }


        return pageData;

    }
}
