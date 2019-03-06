package com.jl.jlapp.mvp.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.CliassifyAdapter;
import com.jl.jlapp.adapter.CliassifyDetailsAdapter;
import com.jl.jlapp.eneity.ClassificationModel;
import com.jl.jlapp.eneity.CliassifyDetailsModel;
import com.jl.jlapp.eneity.ClissifyModel;
import com.jl.jlapp.eneity.EffectAdvertisementByTypeModel;
import com.jl.jlapp.eneity.News_ListModel;
import com.jl.jlapp.mvp.activity.ActivityNameActivity;
import com.jl.jlapp.mvp.activity.CenterActivity;
import com.jl.jlapp.mvp.activity.ChooseAddressActivity;
import com.jl.jlapp.mvp.activity.DiscountActivity;
import com.jl.jlapp.mvp.activity.GoodsDetailActivity;
import com.jl.jlapp.mvp.activity.MainActivity;
import com.jl.jlapp.mvp.activity.NoSortGoodsListForNewsActivity;
import com.jl.jlapp.mvp.activity.OrderSubmitActivity;
import com.jl.jlapp.mvp.activity.OrderWriteActivity;
import com.jl.jlapp.mvp.activity.SearchGoodsListActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ClassifityFragment extends Fragment implements View.OnClickListener{

    View view;
    @BindView(R.id.rlv_left)
    RecyclerView rlvLeft;
    @BindView(R.id.rlv_right)
    RecyclerView rlvRight;
    @BindView(R.id.no_two_classifity)
    TextView noTwoClassifity;
    @BindView(R.id.classifity_advertise)
    ImageView classifityAdvertise;
    List<ClassificationModel.ResultDataBean> typeDatas;
    List<ClassificationModel.ResultDataBean.TwoClassificationsBean> detailsModels=new ArrayList<>();
    CliassifyAdapter cliassifyAdapter;
    CliassifyDetailsAdapter detailsAdapter;
    List<EffectAdvertisementByTypeModel.ResultDataBean> advertisementListByType2 ;//保存分类广告接口数据


    public static int index = 0;

    //加载框
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search, null, false);
        //控制注解
        ButterKnife.bind(this, view);
        buildProgressDialog();
        getClassificationMsg();
        getAdvertisementGoodsListByType2();
        //initView();
        //initDatas();

        return view;
    }

    //模拟左边的数据
   /* private void initDatas() {
        ClissifyModel model;
        for (int i = 0; i < 10; i++) {
            model = new ClissifyModel();
            model.setTitle("默认分类");
            typeDatas.add(model);
        }
    }*/
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

    //模拟改变右边的数据
    private void rightDatas(int pos) {
        detailsModels.clear();
        List<ClassificationModel.ResultDataBean.TwoClassificationsBean> twoClassificationsBeans = typeDatas.get(pos).getTwoClassifications();
        for (int i = 0; i < twoClassificationsBeans.size(); i++) {
            detailsModels.add(twoClassificationsBeans.get(i));
        }
        if (detailsModels.size()>0) {
            noTwoClassifity.setVisibility(View.GONE);
            rlvRight.setVisibility(View.VISIBLE);
            setClassifityDetailAdapter();
        }
        else{
            noTwoClassifity.setVisibility(View.VISIBLE);
            rlvRight.setVisibility(View.GONE);
        }
        /*if (pos == 0) {

            CliassifyDetailsModel model;
            detailsModels.clear();
            for (int i = 0; i < 10; i++) {
                model = new CliassifyDetailsModel();
                model.setTitle_food("包子");
                model.setPics(R.drawable.img_steamed);
                detailsModels.add(model);
            }
        } else if (pos == 1) {
            CliassifyDetailsModel detailsModel;
            detailsModels.clear();
            for (int i = 0; i < 10; i++) {
                detailsModel = new CliassifyDetailsModel();
                detailsModel.setTitle_food("煎饼果子");
                detailsModel.setPics(R.mipmap.ic_launcher);
                detailsModels.add(detailsModel);
            }
        } else if (pos == 2) {
            startActivity(new Intent(getActivity(), OrderWriteActivity.class));
        } else if (pos == 3) {
            startActivity(new Intent(getActivity(), ChooseAddressActivity.class));
        } else if (pos == 4) {
            startActivity(new Intent(getActivity(), OrderSubmitActivity.class));
        } else if (pos == 5) {
            startActivity(new Intent(getActivity(), DiscountActivity.class));
        } else if (pos == 7) {
            startActivity(new Intent(getActivity(), CenterActivity.class));
        } else {
            Toast.makeText(getActivity(), "开发中", Toast.LENGTH_SHORT).show();
        }*/

    }

    //设置的内容
    private void initView() {
        //设置左边RecyclerView的布局方式并关联适配器
        /*if (cliassifyAdapter == null) {*/
            rlvLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
            cliassifyAdapter = new CliassifyAdapter(R.layout.item_rlv_classify_left, typeDatas);
            rlvLeft.setAdapter(cliassifyAdapter);
       /* } else {*/
           // cliassifyAdapter.notifyDataSetChanged();
       /* }*/

        //点击事件
        cliassifyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                index = position;
                cliassifyAdapter.notifyDataSetChanged();
                rightDatas(position);
            }
        });
        rightDatas(0);

    }

    public void setClassifityDetailAdapter() {
        //设置右RecyclerView的布局方式并关联适配器
      /* if (detailsAdapter == null) {*/
            rlvRight.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            detailsAdapter = new CliassifyDetailsAdapter(R.layout.item_rlv_right, detailsModels);
            rlvRight.setAdapter(detailsAdapter);
       /* } else {*/
        //    detailsAdapter.notifyDataSetChanged();
        /*}*/


        detailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            //    index = position;
                //detailsAdapter.notifyDataSetChanged();
                Intent intent =new Intent(getActivity(), SearchGoodsListActivity.class);
                intent.putExtra("searchMsg","");
                intent.putExtra("classificationId",detailsModels.get(position).getId());
                startActivity(intent);
            }
        });
    }

    //通过接口获取一二级分类的信息
    public void getClassificationMsg() {
        typeDatas = new ArrayList<>();
        Net.get().getClassificationMsg().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(classificationModel -> {
                    cancelProgressDialog();
                    if (classificationModel.getCode() == 200) {
                        if(classificationModel.getResultData().size()<=0){
                            Toast.makeText(getContext(),"暂无分类信息",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            typeDatas = classificationModel.getResultData();
                            index=0;
                            initView();
                        }


                    } else {
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * 获取正在生效的分类广告
     */
    public void getAdvertisementGoodsListByType2() {

        Net.get().getEffectAdvertisementByType(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(EffectAdvertisementByTypeModel -> {
                    //                    Log.d("aaaaaaaaaaaa", "getAdvertisementGoodsListByType4数据加载了");
                    int code = EffectAdvertisementByTypeModel.getCode();
                    if (code == 200) {
                        advertisementListByType2 = EffectAdvertisementByTypeModel.getResultData();
                        if (advertisementListByType2.size() > 0) {
                            classifityAdvertise.setVisibility(View.VISIBLE);
                            Glide
                                    .with(getActivity())
                                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_lose_ll).error(R.drawable.img_lose_ll))
                                    .load(AppFinal.BASEURL+advertisementListByType2.get(0).getPicUrl())
                                    .into(classifityAdvertise);
                        } else {
                            classifityAdvertise.setVisibility(View.GONE);
                            //Toast.makeText(getActivity(), "没有正在生效的热门分类广告", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "" + EffectAdvertisementByTypeModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });

    }

    @OnClick({R.id.classifity_advertise})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.classifity_advertise:
                int id = advertisementListByType2.get(0).getId();
                //                Toast.makeText(getActivity(), "id:" + id, Toast.LENGTH_SHORT).show();
                Net.get().getAdvertisementById(id).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(AdvertisementByIdModel -> {
                            //                            Log.d("aaaaaaaaaaaa", "banner点击获取数据加载了");
                            int code1 = AdvertisementByIdModel.getCode();
                            if (code1 == 200) {
                                int flag = AdvertisementByIdModel.getResultData().getFlag();
                                switch (flag){
                                    case 0://商品详情页面    goods代表商品详情表信息
                                        if(AdvertisementByIdModel.getResultData().getGoods().size()>0){
                                            int goodsId = AdvertisementByIdModel.getResultData().getGoods().get(0).getId();
                                            Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                                            intent.putExtra("goodsId",goodsId);
                                            startActivity(intent);
                                        }

                                        break;
                                    case 1://活动页面   goods代表商品详情表列
                                        int activityInformationId = AdvertisementByIdModel.getResultData().getActivityInformation().getId();
                                        Intent intent=new Intent(getActivity(),ActivityNameActivity.class);
                                        intent.putExtra("activityInformationId",activityInformationId);
                                        startActivity(intent);
                                        //                                       Toast.makeText(getActivity(), "活动页面正在开发中..." , Toast.LENGTH_SHORT).show();
                                        break;

                                }
                            } else {
                                Toast.makeText(getActivity(), "" + AdvertisementByIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                        }, throwable -> {
                            Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                        });
                break;
        }
    }
}
