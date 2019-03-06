package com.jl.jlapp.mvp.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.GoodsListAdapter;
import com.jl.jlapp.adapter.MyOrderListAdapter;
import com.jl.jlapp.eneity.Order;
import com.jl.jlapp.eneity.OrderListModel;
import com.jl.jlapp.mvp.activity.BaseActivity;
import com.jl.jlapp.mvp.activity.CenterActivity;
import com.jl.jlapp.mvp.activity.EvaluationActivity;
import com.jl.jlapp.mvp.activity.EvaluationDetailActivity;
import com.jl.jlapp.mvp.activity.GoodsDetailActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.mvp.activity.MyOrderListActivity;
import com.jl.jlapp.mvp.activity.OrderDetailActivity;
import com.jl.jlapp.mvp.activity.OrderTracingActivity;
import com.jl.jlapp.mvp.activity.PayOrderActivity;
import com.jl.jlapp.mvp.activity.ShouldEvaluationActivity;
import com.jl.jlapp.mvp.activity.UnderlineActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.Tools;
import com.yanzhenjie.recyclerview.swipe.SwipeAdapterWrapper;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MyOrderListFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener {
    View view;
    @BindView(R.id.my_order_recycler_view)
    RecyclerView myOrderListView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    MyOrderListAdapter myOrderListAdapter;
    ArrayList<Order> orders;

    List<OrderListModel.ResultDataBean> orderListDataBeans;

    List<OrderListModel.ResultDataBean> shouldShowDataBeanList;

    MyOrderListActivity myOrderListActivity;

    ClearHistorySearhPopu clearHistorySearhPopu;

    int orderState = -1;//默认-1为查看全部  0：待支付 2：待收货 3：已完成 6：售后中
    private static final int PAGE_SIZE = 10;//一页显示的行数

    private int TOTAL_COUNTER = 0;//总行数

    private int delayMillis = 1000;//延时1秒

    private int mCurrentCounter = 0;//当前显示的总行数

    int userId = 0;

    //加载框
    private ProgressDialog progressDialog;

    /**
     * fragment与activity产生关联是  回调这个方法
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        myOrderListActivity = (MyOrderListActivity) getActivity();
        this.orderState = myOrderListActivity.orderState;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_order_list, null, false);
        //控制注解
        ButterKnife.bind(this, view);

        //获取从activity中传递过来的数据
        /*Bundle bundle = getArguments();
        orders = bundle.getParcelableArrayList("dataList");*/
        // setAdapter();
        Log.d("aaaaaaaaMyOrder", "onCreateView");
        //mSwipeRefreshLayout.setOnRefreshListener(this);
        //下拉刷新不可用
        mSwipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("aaaaaaaaMyOrder", "onResume");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if (userId > 0) {
            buildProgressDialog();
            getOrderList(userId);
        } else {
            Toast.makeText(getContext(), "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
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

    //设置适配器
    public void setAdapter() {
        Log.d("aaaaaaaaMyOrder", "setAdapter");
        if (myOrderListAdapter == null) {
            //设置RecyclerView的布局方式
            myOrderListView.setLayoutManager(new LinearLayoutManager(getActivity()));
            //初始化适配器
            myOrderListAdapter = new MyOrderListAdapter(R.layout.my_order_list_item, shouldShowDataBeanList);
            myOrderListAdapter.setOnLoadMoreListener(this, myOrderListView);
            //myOrderListAdapter.isFirstOnly(false);
            //myOrderListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            //设置适配器
            myOrderListView.setAdapter(myOrderListAdapter);
        } else {
            myOrderListAdapter.notifyDataSetChanged();
        }
        mCurrentCounter = myOrderListAdapter.getData().size();
        Log.d("aaaaaaaaaaaaref", "getData:" + myOrderListAdapter.getData().size());

        myOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("orderId", shouldShowDataBeanList.get(position).getId());
                startActivity(intent);
            }
        });

        myOrderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView btn1Tv = view.findViewById(R.id.my_order_item_bottom_btn1);
                TextView btn2Tv = view.findViewById(R.id.my_order_item_bottom_btn2);
                switch (view.getId()) {
                    case R.id.my_order_item_bottom_btn1:
                        String text = btn1Tv.getText().toString().trim();
                        switch (text) {
                            case "支付":
                                Tools.addActivity(getActivity());
                                Intent intent = null;
                                if (shouldShowDataBeanList.get(position).getPayType() == 0) {
                                    intent = new Intent(getContext(), PayOrderActivity.class);
                                } else {
                                    intent = new Intent(getContext(), UnderlineActivity.class);
                                }
                                intent.putExtra("payMoney", shouldShowDataBeanList.get(position).getOrderPresentPrice() + shouldShowDataBeanList.get(position).getPostage());
                                intent.putExtra("orderId", shouldShowDataBeanList.get(position).getId());
                                intent.putExtra("orderNo", shouldShowDataBeanList.get(position).getOrderNo());
                                intent.putExtra("postagePayType", shouldShowDataBeanList.get(position).getPostagePayType());
                                startActivity(intent);

                                break;
                            case "订单跟踪":
                                Intent intent1 = new Intent(getContext(), OrderTracingActivity.class);
                                intent1.putExtra("orderId", shouldShowDataBeanList.get(position).getId());
                                switch (shouldShowDataBeanList.get(position).getOrderState()) {
                                    case 0://待支付
                                        intent1.putExtra("orderState", "待支付");
                                        break;
                                    case 1://待发货
                                        intent1.putExtra("orderState", "待发货");
                                        break;
                                    case 2://待收货
                                        intent1.putExtra("orderState", "待收货");
                                        break;
                                    case 3://已完成
                                        intent1.putExtra("orderState", "已完成");
                                        break;
                                    case 4://已取消
                                        intent1.putExtra("orderState", "已取消");
                                        break;
                                    case 5://已关闭
                                        intent1.putExtra("orderState", "已关闭");
                                        break;
                                    case 6://售后中
                                        intent1.putExtra("orderState", "售后中");
                                        break;
                                    case 7://已退货退款
                                        intent1.putExtra("orderState", "已退货退款");
                                        break;
                                    case 8://已换货
                                        intent1.putExtra("orderState", "已换货");
                                        break;
                                    case 9://已支付
                                        intent1.putExtra("orderState", "已支付，正在审核");
                                        break;
                                    case 10://关闭售后
                                        intent1.putExtra("orderState", "关闭售后");
                                        break;
                                }

                                startActivity(intent1);
                                break;
                            case "再次购买":
                                buyAgainClick(shouldShowDataBeanList.get(position).getId());
                                break;
                        }
                        break;
                    case R.id.my_order_item_bottom_btn2:
                        if ("评价晒单".equals(btn2Tv.getText().toString().trim())) {
                            Intent intent = new Intent(getContext(), ShouldEvaluationActivity.class);
                            intent.putExtra("orderId", shouldShowDataBeanList.get(position).getId());
                            startActivity(intent);
                        } else if ("评价详情".equals(btn2Tv.getText().toString().trim())) {
                            Intent intent2 = new Intent(getActivity(), EvaluationDetailActivity.class);
                            intent2.putExtra("orderId", shouldShowDataBeanList.get(position).getId());
                            startActivity(intent2);
                        } else if ("确认收货".equals(btn2Tv.getText().toString().trim())) {
                            clearHistorySearhPopu = new ClearHistorySearhPopu(getContext(), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clearHistorySearhPopu.dismiss();
                                    cancleOrder(userId, shouldShowDataBeanList.get(position).getId(), 3);
                                }
                            }, 5);
                            clearHistorySearhPopu.showAtLocation(getActivity().findViewById(R.id.my_order_list_page), Gravity.CENTER, 0, 0);
                        }

                        break;
                }
            }
        });

    }

    //再次购买点击事件方法体
    public void buyAgainClick(Integer orderId) {
        Net.get().buyAgain(orderId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(copyOrderShoppingCartModel -> {
                    int code = copyOrderShoppingCartModel.getCode();
                    if (code == 200) {
                        Intent intent = new Intent(getContext(), BaseActivity.class);
                        intent.putExtra("shouldReplaceFragment", 2);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "" + copyOrderShoppingCartModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }


                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });

    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            //订单详情页点击了删除订单按钮，此时列表页需要刷新
            case 0:
                int orderId = data.getIntExtra("orderId", 0);
                if (orderId > 0) {
                    for (int i = 0; i < shouldShowDataBeanList.size(); i++) {
                        if (shouldShowDataBeanList.get(i).getId() == orderId) {
                            shouldShowDataBeanList.remove(i);
                            i--;
                            break;
                        }
                    }
                    myOrderListAdapter.notifyDataSetChanged();
                }
                break;
        }
    }*/

    //从结果集中根据需要展示的订单状态 遍历出需要展示的订单列表
    public List<OrderListModel.ResultDataBean> getShouldShowDataListByOrderState2(int startNum, int num) {

        List<OrderListModel.ResultDataBean> allShowDataBeanList = new ArrayList<>();
        List<OrderListModel.ResultDataBean> allShowDataBeanList2 = new ArrayList<>();
        for (int i = 0; i < orderListDataBeans.size(); i++) {
            //全部
            if (orderState == -1) {
                allShowDataBeanList.add(orderListDataBeans.get(i));
            }
            //售后
            else if (orderState == 6) {
                if (orderListDataBeans.get(i).getOrderState() == 6 || orderListDataBeans.get(i).getOrderState() == 7
                        || orderListDataBeans.get(i).getOrderState() == 8 || orderListDataBeans.get(i).getOrderState() == 10) {
                    allShowDataBeanList.add(orderListDataBeans.get(i));
                }
            } else if (orderState == 2) {
                if ( orderListDataBeans.get(i).getOrderState() == 2) {
                    allShowDataBeanList.add(orderListDataBeans.get(i));
                }
            } else {
                if (orderListDataBeans.get(i).getOrderState() == orderState) {
                    allShowDataBeanList.add(orderListDataBeans.get(i));
                }
            }
        }

        if (allShowDataBeanList.size() > startNum && allShowDataBeanList.size() > startNum + num) {
            for (int i = startNum; i < startNum + num; i++) {
                allShowDataBeanList2.add(allShowDataBeanList.get(i));
            }

        } else if (allShowDataBeanList.size() > startNum && allShowDataBeanList.size() <= startNum + num) {
            for (int i = startNum; i < allShowDataBeanList.size(); i++) {
                allShowDataBeanList2.add(allShowDataBeanList.get(i));
            }

        }

        return allShowDataBeanList2;
    }

    //从结果集中根据需要展示的订单状态 遍历出需要展示的订单列表
    public void getShouldShowDataListByOrderState(int startNum, int num) {
        if (shouldShowDataBeanList == null) {
            shouldShowDataBeanList = new ArrayList<>();
        }
        shouldShowDataBeanList.clear();
        List<OrderListModel.ResultDataBean> allShowDataBeanList = new ArrayList<>();
        for (int i = 0; i < orderListDataBeans.size(); i++) {
            //全部
            if (orderState == -1) {
                allShowDataBeanList.add(orderListDataBeans.get(i));
            }
            //售后
            else if (orderState == 6) {
                if (orderListDataBeans.get(i).getOrderState() == 6 || orderListDataBeans.get(i).getOrderState() == 7
                        || orderListDataBeans.get(i).getOrderState() == 8 || orderListDataBeans.get(i).getOrderState() == 10) {
                    allShowDataBeanList.add(orderListDataBeans.get(i));
                }
            } else if (orderState == 2) {
                if ( orderListDataBeans.get(i).getOrderState() == 2) {
                    allShowDataBeanList.add(orderListDataBeans.get(i));
                }
            } else {
                if (orderListDataBeans.get(i).getOrderState() == orderState) {
                    allShowDataBeanList.add(orderListDataBeans.get(i));
                }
            }

        }

        if (allShowDataBeanList.size() > startNum && allShowDataBeanList.size() > startNum + num) {

            for(int i = 0; i < startNum + num; i++){
                shouldShowDataBeanList.add(allShowDataBeanList.get(i));
            }
        } else if (allShowDataBeanList.size() > startNum && allShowDataBeanList.size() <= startNum + num) {

            for(int i = 0; i < allShowDataBeanList.size(); i++){
                shouldShowDataBeanList.add(allShowDataBeanList.get(i));
            }
        }
        TOTAL_COUNTER = allShowDataBeanList.size();

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

                            getShouldShowDataListByOrderState(0,PAGE_SIZE);
                            if (shouldShowDataBeanList.size() > 0) {
                                setAdapter();
                            } else {
                                myOrderListActivity.replaceIfNoData();
                            }

                        } else {
                            myOrderListActivity.replaceIfNoData();
                        }

                    } else {
                        Toast.makeText(getContext(), orderListModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //用户操作订单
    public void cancleOrder(Integer userId, Integer orderId, Integer operation) {
        Net.get().cancleOrder(orderId, userId, operation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cancleOrderModel -> {
                    if (cancleOrderModel.getCode() == 200) {
                        Toast.makeText(getContext(), "已确认收货", Toast.LENGTH_SHORT).show();
                        getOrderList(userId);
                    } else {
                        Toast.makeText(getContext(), cancleOrderModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    public void onLoadMoreRequested() {
        //下拉刷新不可用
        mSwipeRefreshLayout.setEnabled(false);
        if (myOrderListAdapter.getData().size() < PAGE_SIZE) {
            myOrderListAdapter.loadMoreEnd(false);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                myOrderListAdapter.loadMoreEnd(false);//true is gone,false is visible
            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myOrderListAdapter.addData(getShouldShowDataListByOrderState2(myOrderListAdapter.getData().size(), PAGE_SIZE));
                        mCurrentCounter = myOrderListAdapter.getData().size();
                        myOrderListAdapter.loadMoreComplete();
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }, delayMillis);
            }

        }
    }
}
