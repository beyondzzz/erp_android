package com.jl.jlapp.mvp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.DiscountAdapter;
import com.jl.jlapp.adapter.UserUnUsableCouponAdapter;
import com.jl.jlapp.adapter.UserUsableCouponAdapter;
import com.jl.jlapp.eneity.ActivitysAndCouponsByGoodsMsgModel;
import com.jl.jlapp.eneity.DiscountModel;
import com.jl.jlapp.eneity.UserCouponInfosModel;
import com.jl.jlapp.mvp.activity.CouponHasGoodsListActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.mvp.activity.OrderSubmitActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fyf on 2018/1/16.
 */

public class UserCouponFragment extends LazyLoadFragment implements BaseQuickAdapter.RequestLoadMoreListener{

    public static final int TYPE_USEABLE = 1;
    public static final int TYPE_UNUSEABLE = 2;
    private static final String EXTRA_TYPE = "extra_type";
    @BindView(R.id.lrv_discount_list)
    RecyclerView lrvDiscountList;
    @BindView(R.id.no_coupon_show)
    RelativeLayout noCouponShow;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    Unbinder unbinder;

    private UserUsableCouponAdapter userUsableCouponAdapter;
    private UserUnUsableCouponAdapter userUnUsableCouponAdapter;

    private static final int PAGE_SIZE = 10;//一页显示的行数

    private int TOTAL_COUNTER_USABLED = 0;//总行数
    private int TOTAL_COUNTER_UNABLED = 0;//总行数

    private int delayMillis = 1000;//延时1秒

    private int mCurrentCounter = 0;//当前显示的总行数

    int userId=0;

    List<UserCouponInfosModel.ResultDataBean.UsableCouponBean> usableCouponsBeanList = new ArrayList<>();
    List<UserCouponInfosModel.ResultDataBean.DisabledCouponBean> unUsableCouponsBeanList = new ArrayList<>();

    List<UserCouponInfosModel.ResultDataBean.UsableCouponBean> shouldShowUsableCouponsBeanList = new ArrayList<>();
    List<UserCouponInfosModel.ResultDataBean.DisabledCouponBean> shouldShowUnUsableCouponsBeanList = new ArrayList<>();

    public setOnClickCouponItem setOnClickCouponItem;

    public OnGetCouponMsg onGetCouponMsg;

    public void setOnGetCouponMsg(OnGetCouponMsg onGetCouponMsg) {
        this.onGetCouponMsg = onGetCouponMsg;
    }

    public void setSetOnClickCouponItem(UserCouponFragment.setOnClickCouponItem setOnClickCouponItem) {
        this.setOnClickCouponItem = setOnClickCouponItem;
    }

    //加载框
    private ProgressDialog progressDialog;

    int type = TYPE_USEABLE;

    public static UserCouponFragment newInstance(int type, int userId) {
        UserCouponFragment fragment = new UserCouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        bundle.putInt("userId",userId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_discount;
    }

    @Override
    public void initViews(View view) {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(EXTRA_TYPE)) {
            throw new IllegalArgumentException("NoticeListFragment must be created by type args");
        }
        type = args.getInt(EXTRA_TYPE, TYPE_USEABLE);
        userId=args.getInt("userId",0);
        unbinder = ButterKnife.bind(this, view);
        //下拉刷新不可用
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void loadData() {
        if(userId>0){
            buildProgressDialog();
            getCouponInfoByUserId(userId);
        }
        else{
            Toast.makeText(getContext(),"您还未登录，请先登录。",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getContext(),LoginActivity.class);
            startActivity(intent);
        }


    }

    private void initDatas(int type) {
        switch (type) {
            case TYPE_USEABLE:
                //回调函数  修改DiscountActivity找那个显示的优惠券个数
                if(onGetCouponMsg!=null){
                    onGetCouponMsg.onGetCouponMsg(TOTAL_COUNTER_USABLED,TOTAL_COUNTER_UNABLED);
                }
                if(shouldShowUsableCouponsBeanList.size()>0){
                    lrvDiscountList.setVisibility(View.VISIBLE);
                    noCouponShow.setVisibility(View.GONE);
                    if (userUsableCouponAdapter == null) {
                        Log.d("aaaaaaaaaaaaa","新建 shouldShowUsableCouponsBeanList："+shouldShowUsableCouponsBeanList.size());
                        lrvDiscountList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        userUsableCouponAdapter = new UserUsableCouponAdapter(R.layout.item_coupon, shouldShowUsableCouponsBeanList);
                        userUsableCouponAdapter.setOnLoadMoreListener(this, lrvDiscountList);
                        lrvDiscountList.setAdapter(userUsableCouponAdapter);
                        mCurrentCounter=userUsableCouponAdapter.getData().size();
                        userUsableCouponAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                TextView priceTv = view.findViewById(R.id.tv_discount_money);
                                TextView discountMsgTv = view.findViewById(R.id.tv_discount_condition);
                                int couponId = shouldShowUsableCouponsBeanList.get(position).getId();
                                double price = Double.parseDouble(priceTv.getText().toString().trim());
                                String countMsg = discountMsgTv.getText().toString().trim();
                                if (setOnClickCouponItem != null) {
                                    setOnClickCouponItem.onClickCouponItem(couponId, price, countMsg);
                                }
                            }
                        });
                        userUsableCouponAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent=new Intent(getContext(), CouponHasGoodsListActivity.class);
                                intent.putExtra("couponId",shouldShowUsableCouponsBeanList.get(position).getCouponInformationId());
                                startActivity(intent);
                            }
                        });
                    } else {
                        Log.d("aaaaaaaaaaaaa","刷新 shouldShowUsableCouponsBeanList："+shouldShowUsableCouponsBeanList.size());
                        userUsableCouponAdapter.notifyDataSetChanged();
                        mCurrentCounter=userUsableCouponAdapter.getData().size();
                    }
                }
                else{
                    lrvDiscountList.setVisibility(View.GONE);
                    noCouponShow.setVisibility(View.VISIBLE);
                }


                break;
            case TYPE_UNUSEABLE:
                if(shouldShowUnUsableCouponsBeanList.size()>0){
                    lrvDiscountList.setVisibility(View.VISIBLE);
                    noCouponShow.setVisibility(View.GONE);

                    if (userUnUsableCouponAdapter == null) {
                        Log.d("aaaaaaaaaaaaa","新建 shouldShowUnUsableCouponsBeanList："+shouldShowUnUsableCouponsBeanList.size());
                        lrvDiscountList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        userUnUsableCouponAdapter = new UserUnUsableCouponAdapter(R.layout.item_coupon, shouldShowUnUsableCouponsBeanList);
                        userUnUsableCouponAdapter.setOnLoadMoreListener(this, lrvDiscountList);
                        lrvDiscountList.setAdapter(userUnUsableCouponAdapter);
                    } else {
                        Log.d("aaaaaaaaaaaaa","刷新 shouldShowUnUsableCouponsBeanList："+shouldShowUnUsableCouponsBeanList.size());
                        userUnUsableCouponAdapter.notifyDataSetChanged();
                    }
                    mCurrentCounter=userUnUsableCouponAdapter.getData().size();
                }
                else{
                    lrvDiscountList.setVisibility(View.GONE);
                    noCouponShow.setVisibility(View.VISIBLE);
                }


                break;
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
        progressDialog.setMessage("清除中");
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


    private List<UserCouponInfosModel.ResultDataBean.UsableCouponBean> setShouldShowUsableCouponsBeanList2(int startNum, int num){

        List<UserCouponInfosModel.ResultDataBean.UsableCouponBean> usableCouponBeans=new ArrayList<>();
        if (usableCouponsBeanList.size()>startNum&&usableCouponsBeanList.size()>startNum+num){
            for (int i = startNum; i <startNum+num ; i++) {
                usableCouponBeans.add(usableCouponsBeanList.get(i));
            }

        }else if (usableCouponsBeanList.size() > startNum && usableCouponsBeanList.size() <= startNum + num) {
            for (int i = startNum; i < usableCouponsBeanList.size(); i++) {
                usableCouponBeans.add(usableCouponsBeanList.get(i));
            }

        }
        return usableCouponBeans;

    }

    private void setShouldShowUsableCouponsBeanList(int startNum, int num){
        shouldShowUsableCouponsBeanList.clear();

        if (usableCouponsBeanList.size()>startNum&&usableCouponsBeanList.size()>startNum+num){
            for (int i = 0; i <startNum+num ; i++) {
                shouldShowUsableCouponsBeanList.add(usableCouponsBeanList.get(i));
            }
        }else if (usableCouponsBeanList.size() > startNum && usableCouponsBeanList.size() <= startNum + num) {

            for (int i = 0; i <usableCouponsBeanList.size() ; i++) {
                shouldShowUsableCouponsBeanList.add(usableCouponsBeanList.get(i));
            }
        }
        TOTAL_COUNTER_USABLED=usableCouponsBeanList.size();
    }

    private List<UserCouponInfosModel.ResultDataBean.DisabledCouponBean> setShouldShowUnUsableCouponsBeanList2(int startNum, int num){

        List<UserCouponInfosModel.ResultDataBean.DisabledCouponBean> disabledCouponBeans=new ArrayList<>();
        if (unUsableCouponsBeanList.size()>startNum&&unUsableCouponsBeanList.size()>startNum+num){
            for (int i = startNum; i <startNum+num ; i++) {
                disabledCouponBeans.add(unUsableCouponsBeanList.get(i));
            }

        }else if (unUsableCouponsBeanList.size() > startNum && unUsableCouponsBeanList.size() <= startNum + num) {
            for (int i = startNum; i < unUsableCouponsBeanList.size(); i++) {
                disabledCouponBeans.add(unUsableCouponsBeanList.get(i));
            }

        }
        return disabledCouponBeans;
    }
    private void setShouldShowUnUsableCouponsBeanList(int startNum, int num){
        shouldShowUnUsableCouponsBeanList.clear();

        if (unUsableCouponsBeanList.size()>startNum&&unUsableCouponsBeanList.size()>startNum+num){

            for (int i = 0; i <startNum+num ; i++) {
                shouldShowUnUsableCouponsBeanList.add(unUsableCouponsBeanList.get(i));
            }
        }else if (unUsableCouponsBeanList.size() > startNum && unUsableCouponsBeanList.size() <= startNum + num) {

            for (int i = 0; i <unUsableCouponsBeanList.size() ; i++) {
                shouldShowUnUsableCouponsBeanList.add(unUsableCouponsBeanList.get(i));
            }
        }
        TOTAL_COUNTER_UNABLED=unUsableCouponsBeanList.size();

    }

    public void getCouponInfoByUserId(int userId) {
        Net.get().getCouponInfoByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userCouponInfos -> {
                    cancelProgressDialog();
                    usableCouponsBeanList.clear();
                    unUsableCouponsBeanList.clear();
                    if (userCouponInfos.getCode() == 200) {
                        Log.d("userCouponInfossize","usableCouponsBeanListSize:"+userCouponInfos.getResultData().getUsableCoupon().size());
                        Log.d("userCouponInfossize","usableCouponsBeanListSize:"+userCouponInfos.getResultData().getUsableCoupon().size());
                        usableCouponsBeanList = userCouponInfos.getResultData().getUsableCoupon();
                        unUsableCouponsBeanList=userCouponInfos.getResultData().getDisabledCoupon();
                        setShouldShowUsableCouponsBeanList(0,PAGE_SIZE);
                        setShouldShowUnUsableCouponsBeanList(0,PAGE_SIZE);
                        initDatas(type);
                    } else {
                        Toast.makeText(getContext(), userCouponInfos.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onLoadMoreRequested() {
        //下拉刷新不可用
        mSwipeRefreshLayout.setEnabled(false);
        switch (type) {
            case TYPE_USEABLE:
                if (userUsableCouponAdapter.getData().size() < PAGE_SIZE) {
                    userUsableCouponAdapter.loadMoreEnd(false);
                } else {
                    if (mCurrentCounter >= TOTAL_COUNTER_USABLED) {
                        userUsableCouponAdapter.loadMoreEnd(false);//true is gone,false is visible
                    } else {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<UserCouponInfosModel.ResultDataBean.UsableCouponBean> usableCouponBeans=new ArrayList<>();
                                usableCouponBeans=setShouldShowUsableCouponsBeanList2(userUsableCouponAdapter.getData().size(), PAGE_SIZE);
                                userUsableCouponAdapter.addData(usableCouponBeans);
                                mCurrentCounter = userUsableCouponAdapter.getData().size();
                                userUsableCouponAdapter.loadMoreComplete();
                                mSwipeRefreshLayout.setEnabled(true);
                            }
                        }, delayMillis);
                    }

                }
                break;
            case TYPE_UNUSEABLE:
                if (userUnUsableCouponAdapter.getData().size() < PAGE_SIZE) {
                    userUnUsableCouponAdapter.loadMoreEnd(false);
                } else {
                    if (mCurrentCounter >= TOTAL_COUNTER_UNABLED) {
                        userUnUsableCouponAdapter.loadMoreEnd(false);//true is gone,false is visible
                    } else {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                userUnUsableCouponAdapter.addData(setShouldShowUnUsableCouponsBeanList2(userUnUsableCouponAdapter.getData().size(), PAGE_SIZE));
                                mCurrentCounter = userUnUsableCouponAdapter.getData().size();
                                userUnUsableCouponAdapter.loadMoreComplete();
                                mSwipeRefreshLayout.setEnabled(true);
                            }
                        }, delayMillis);
                    }

                }
                break;
        }

    }

    public interface setOnClickCouponItem {
        void onClickCouponItem(int couponId, double price, String countMsg);
    }

    public interface OnGetCouponMsg {
        void onGetCouponMsg(int usableCouponNum, int unUsableCouonNum);
    }

}
