package com.jl.jlapp.mvp.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.ShopCartListAdapter;
import com.jl.jlapp.eneity.Order;
import com.jl.jlapp.eneity.ShoppingCartModel;
import com.jl.jlapp.mvp.activity.BaseActivity;
import com.jl.jlapp.mvp.activity.GoodsDetailActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.mvp.activity.MessageCenterActivity;
import com.jl.jlapp.mvp.activity.OrderSubmitActivity;
import com.jl.jlapp.mvp.activity.OrderWriteActivity;
import com.jl.jlapp.mvp.activity.SearchGoodsListActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ChooseGoodsActivityPopu;
import com.jl.jlapp.popu.ChooseGoodsSpecPopu;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.popu.ModifyNumPopu;
import com.jl.jlapp.utils.Tools;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ShopCartFragment extends Fragment implements View.OnClickListener, ChooseGoodsSpecPopu.OnClickOkBtnListener, ModifyNumPopu.OnClickOkBtn {
    private static String TAG = "ShopCartFragmentaa";
    View view;
    @BindView(R.id.shop_cart_list_recycler_view)
    RecyclerView shopCartListRecyclerView;
    ShopCartListAdapter shopCartListAdapter;
    @BindView(R.id.shop_cart_list_check_all)
    CheckBox shopCartListCheckAll;
    @BindView(R.id.shop_cart_edit_btn)
    TextView shopCartEditBtn;
    @BindView(R.id.shop_cart_edit_footer_delete_btn)
    TextView shopCartEditFooterDeleteBtn;
    @BindView(R.id.shop_cart_list_footer)
    RelativeLayout shopCartListFooter;
    @BindView(R.id.shop_cart_content_has_goods)
    LinearLayout shopCartContentHasGoods;
    @BindView(R.id.shop_cart_content_no_goods)
    RelativeLayout shopCartContentNoGoods;
    @BindView(R.id.shop_cart_total_price)
    TextView shopCartTotalPrice;
    @BindView(R.id.shop_cart_footer)
    RelativeLayout shopCartFooter;
    @BindView(R.id.choose_goods_num)
    TextView chooseGoodsNum;
    @BindView(R.id.shop_cart_foot_up_btn)
    LinearLayout shopCartFootUpBtn;
    @BindView(R.id.shop_cart_message)
    ImageView shopCartMessage;
    @BindView(R.id.shop_cart_return_home_btn)
    TextView shopCartReturnHomeBtn;
    @BindView(R.id.shop_cart_go_ms_btn)
    TextView shopCartGoMsBtn;

    TextView buyNumTextView;

    List<ShoppingCartModel.ResultDataBean> shoppingCartList;
    List<ShoppingCartModel.ResultDataBean> checkedGoodsList = new ArrayList<>();//选中的数据
    List<ShoppingCartModel.ResultDataBean> noGoodsList;
    List<ShoppingCartModel.ResultDataBean> changeShopCartGoodsMsgList = new ArrayList<>();//保存进行了修改的购物车商品信息，方便之后进行更改的操作
    ChooseGoodsSpecPopu chooseGoodsSpecPopu;
    ChooseGoodsActivityPopu chooseGoodsActivityPopu;
    List<ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX> goodsHasActivity = new ArrayList();
    int chooseGoodsSpecPosition = -1;
    boolean isChooseEditBtn = false;
    //加载框
    private ProgressDialog progressDialog;

    //删除提示框
    ClearHistorySearhPopu clearHistorySearhPopu;

    //修改数量框
    ModifyNumPopu modifyNumPopu;

    int forNum = 0;//记录点击完成时 调用后台修改方法的次数，以便完成时进行刷新数据。

    int userId = 0;
    DecimalFormat df;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop_cart, null, false);
        //控制注解
        ButterKnife.bind(this, view);
        df = new DecimalFormat(".00");//保留两位小数
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        checkedGoodsList.clear();
        shopCartListCheckAll.setChecked(false);
        if (userId > 0) {
            shopCartFooter.setVisibility(View.GONE);
            shopCartContentNoGoods.setVisibility(View.GONE);
            shopCartContentHasGoods.setVisibility(View.GONE);
            getMessageNum(userId);
            buildProgressDialog();

            countGoodsAllPrice();
            getShoppingCartByUserId(userId);
        } else {
            shopCartMessage.setImageResource(R.drawable.icon_message_green_l);
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

    public void setDataToControl() {
        //购物车有数据
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            shopCartContentNoGoods.setVisibility(View.GONE);
            shopCartContentHasGoods.setVisibility(View.VISIBLE);
            shopCartFooter.setVisibility(View.VISIBLE);
            setListener();

            initializeAdapter();
        }
        //购物车无数据
        else {
            shopCartMessage.setOnClickListener(this);
            shopCartReturnHomeBtn.setOnClickListener(this);
            shopCartGoMsBtn.setOnClickListener(this);

            shopCartFooter.setVisibility(View.GONE);
            shopCartContentNoGoods.setVisibility(View.VISIBLE);
            shopCartContentHasGoods.setVisibility(View.GONE);
        }

        if(checkedGoodsList.size()<=0){
            shopCartFootUpBtn.setBackgroundResource(R.color.moreText);
            shopCartFootUpBtn.setEnabled(false);
        }
        else{
            shopCartFootUpBtn.setBackgroundResource(R.color.checkGreenColor);
            shopCartFootUpBtn.setEnabled(true);
        }
    }

    public void setListener() {
        shopCartListCheckAll.setOnClickListener(this);
        shopCartEditBtn.setOnClickListener(this);
        shopCartEditFooterDeleteBtn.setOnClickListener(this);
        shopCartFootUpBtn.setOnClickListener(this);
        shopCartMessage.setOnClickListener(this);
        shopCartReturnHomeBtn.setOnClickListener(this);
        shopCartGoMsBtn.setOnClickListener(this);
    }

    //传入对象，调用pop中的方法
    public void setChooseGoodsSpecPopuListener() {
        chooseGoodsSpecPopu.setOnClickOkBtnListener(this);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //全选按钮
            case R.id.shop_cart_list_check_all:
                checkedGoodsList.clear();
                if (shopCartListCheckAll.isChecked()) {
                    for (int i = 0; i < shoppingCartList.size(); i++) {
                        if (shoppingCartList.get(i).getGoodsDetails() != null) {
                            if (shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                                for (int s = 0; s < shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size(); s++) {
                                    if (shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(s).getId() == shoppingCartList.get(i).getGoodsSpecificationDetailsId()) {
                                       if(shoppingCartList.get(i).getState()==0){
                                           //不是预售并且不允许0库存出库
                                           if(shoppingCartList.get(i).getGoodsDetails().getZeroStock()==0&&shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(s).getGxcGoodsState()==2){
                                               if (shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(s).getGxcGoodsStock() > 0) {
                                                   checkedGoodsList.add(shoppingCartList.get(i));
                                               }
                                           }
                                           else{
                                               checkedGoodsList.add(shoppingCartList.get(i));
                                           }
                                       }
                                    }
                                }
                            }
                        }
                    }
                    shopCartListAdapter.setCheckAll(true);
                    shopCartListAdapter.setCheckedGoodsList(checkedGoodsList);
                    shopCartListAdapter.notifyDataSetChanged();
                } else {
                    shopCartListAdapter.setCheckAll(false);
                    shopCartListAdapter.setCheckedGoodsList(checkedGoodsList);
                    shopCartListAdapter.notifyDataSetChanged();
                }
                countGoodsAllPrice();
                Log.d(TAG, "checkedGoodsList.size:" + checkedGoodsList.size());
                break;
            //编辑按钮
            case R.id.shop_cart_edit_btn:

                if (shopCartEditBtn.getText().toString().equals("编辑")) {
                    isChooseEditBtn = true;
                    changeShopCartGoodsMsgList.clear();
                    shopCartEditFooterDeleteBtn.setVisibility(View.VISIBLE);
                    shopCartListFooter.setVisibility(View.GONE);
                    checkedGoodsList.clear();
                    shopCartListCheckAll.setChecked(false);
                    shopCartEditBtn.setText("完成");

                    shopCartListAdapter.setCheckedGoodsList(checkedGoodsList);
                    shopCartListAdapter.setCheckEditBtn(true);
                    shopCartListAdapter.setCheckAll(false);
                    shopCartListAdapter.notifyDataSetChanged();
                } else {

                    isChooseEditBtn = false;
                    shopCartEditFooterDeleteBtn.setVisibility(View.GONE);
                    shopCartListFooter.setVisibility(View.VISIBLE);
                    checkedGoodsList.clear();
                    shopCartListCheckAll.setChecked(false);
                    shopCartEditBtn.setText("编辑");
                    shopCartListAdapter.setCheckedGoodsList(checkedGoodsList);
                    shopCartListAdapter.setCheckEditBtn(false);
                    shopCartListAdapter.setCheckAll(false);
                    shopCartListAdapter.notifyDataSetChanged();
                    //重新计算价格
                    countGoodsAllPrice();

                    //***********对数据进行更新操作
                    Log.d(TAG, "changeShopCartGoodsMsgList.size:" + changeShopCartGoodsMsgList.size());
                    for (int i = 0; i < changeShopCartGoodsMsgList.size(); i++) {
                        Log.d(TAG, "spec:" + changeShopCartGoodsMsgList.get(i).getGoodsSpecificationDetailsName() + ", num:" + changeShopCartGoodsMsgList.get(i).getGoodsNum());
                        editShoppingCartGoodsById_updateGoodsSpec(changeShopCartGoodsMsgList.get(i).getId(), userId, -1, changeShopCartGoodsMsgList.get(i).getGoodsSpecificationDetailsId(), changeShopCartGoodsMsgList.get(i).getGoodsNum());
                    }


                }
                break;
            //编辑页面底部的删除按钮
            case R.id.shop_cart_edit_footer_delete_btn:

                if (checkedGoodsList.size() > 0) {
                    for (int i = 0; i < checkedGoodsList.size(); i++) {
                        for (int c = 0; c < changeShopCartGoodsMsgList.size(); c++) {
                            if (checkedGoodsList.get(i).getId() == changeShopCartGoodsMsgList.get(c).getId()) {
                                changeShopCartGoodsMsgList.remove(c);
                                c--;
                                break;
                            }
                        }
                    }
                    clearHistorySearhPopu = new ClearHistorySearhPopu(getContext(), itemclick, 1);
                    clearHistorySearhPopu.showAtLocation(getActivity().findViewById(R.id.shop_cart_list_page), Gravity.CENTER, 0, 0);
                } else {
                    Toast.makeText(getContext(), "你还没有选择商品!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.shop_cart_foot_up_btn:
                Tools.addActivity(getActivity());
                if (checkedGoodsList.size() <= 0) {
                    Toast.makeText(getContext(), "你还没有选择需要结算的商品!", Toast.LENGTH_SHORT).show();
                }else {
                    if(checkOrderSubmit()==1){//预售商品与其他商品不能同时下单
                        Toast.makeText(getContext(), "预售商品与非预售商品不能同时下单", Toast.LENGTH_SHORT).show();
                    }else if(checkOrderSubmit()==2){//商品均为预售商品
                        if(!isSamePreSellActivity()){//商品参与的预售活动是不同的
                            Toast.makeText(getContext(), "参与不同预售活动的商品不能同时下单", Toast.LENGTH_SHORT).show();
                        }else{//商品参与的预售活动是相同的
                            int[] shopCartIds = new int[checkedGoodsList.size()];
                            for (int i = 0; i < checkedGoodsList.size(); i++) {
                                shopCartIds[i] = checkedGoodsList.get(i).getId();
                            }
                            Intent intent = new Intent(getActivity(), OrderSubmitActivity.class);
                            intent.putExtra("shopCartIds", shopCartIds);
                            intent.putExtra("isFromWhichPage", 0);
                            intent.putExtra("isPresell",1);//是否是预售商品 1：是 ，2：不是
                            intent.putExtra("activityId",checkedGoodsList.get(0).getActivityId());//预售商品参与的预售活动id
                            startActivity(intent);
                        }
                    }else{//商品均为非预售商品
                        int[] shopCartIds = new int[checkedGoodsList.size()];
                        for (int i = 0; i < checkedGoodsList.size(); i++) {
                            shopCartIds[i] = checkedGoodsList.get(i).getId();
                        }
                        Intent intent = new Intent(getActivity(), OrderSubmitActivity.class);
                        intent.putExtra("shopCartIds", shopCartIds);
                        intent.putExtra("isFromWhichPage", 0);
                        intent.putExtra("isPresell",2);//是否是预售商品 1：是 ，2：不是
                        startActivity(intent);
                    }
                }
                break;
            case R.id.shop_cart_message:
                Intent intent = new Intent(getContext(), MessageCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.shop_cart_return_home_btn:
                intent = new Intent(getActivity(), BaseActivity.class);
                startActivity(intent);
                break;
            case R.id.shop_cart_go_ms_btn:
                if(Fragment_FirstPages.flashSaleGoodsId <=0){
                    Toast.makeText(getActivity(), "暂时还没有限时抢购商品哦，等会儿再来看看吧~" , Toast.LENGTH_SHORT).show();
                }else{
                    intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("goodsId",Fragment_FirstPages.flashSaleGoodsId);
                    //                Toast.makeText(getActivity(), "" + flashSaleGoodsId, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                break;

        }
    }

    public View.OnClickListener itemclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearHistorySearhPopu.dismiss();

            //---------------删除操作-----------
            int[] ids = new int[checkedGoodsList.size()];
            for (int i = 0; i < checkedGoodsList.size(); i++) {
                ids[i] = checkedGoodsList.get(i).getId();
            }

            deleteBatchShoppingCartById(ids, userId);

            Net.get().getShoppingCart(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(shopingCartModel -> {
                        if (shopingCartModel.getCode() == 200) {
                            shoppingCartList = shopingCartModel.getResultData();

                            //购物车有数据
                            if (shoppingCartList != null && shoppingCartList.size() > 0) {
                                //给RecyclerView设置布局方向
                                shopCartListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                //初始化适配器
                                shopCartListAdapter = new ShopCartListAdapter(R.layout.shop_cart_list_item, shoppingCartList);

                                shopCartListCheckAll.setChecked(false);
                                checkedGoodsList.clear();
                                shopCartListAdapter.setCheckedGoodsList(checkedGoodsList);
                                shopCartListAdapter.setCheckEditBtn(true);
                                shopCartListAdapter.setCheckAll(false);

                                //给RecyclerView添加适配器
                                shopCartListRecyclerView.setAdapter(shopCartListAdapter);

                                setAdapterListener(shopCartListAdapter);
                            }
                            //购物车无数据
                            else {
                                shopCartContentNoGoods.setVisibility(View.VISIBLE);
                                shopCartContentHasGoods.setVisibility(View.GONE);
                                shopCartFooter.setVisibility(View.GONE);
                            }

                        } else {
                            Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {
                        Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                    });
            // getShoppingCartByUserId(userId);
        }
    };


    //判断全选按钮是否可以点击
    public void decideCheckAllIsHasChoose() {
        //判断页面里的商品是否还有可以选择的
        int i = 0;
        for (i = 0; i < shoppingCartList.size(); i++) {
            if (shoppingCartList.get(i).getGoodsDetails() != null) {
                if (shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                    int s = 0;
                    for (s = 0; s < shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size(); s++) {
                        if (shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(s).getId() == shoppingCartList.get(i).getGoodsSpecificationDetailsId()) {
                          if(shoppingCartList.get(i).getState()==0){
                              //库存大于0或者有商品时允许0库存出库或者有商品是预售商品
                              if (shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(s).getGxcGoodsStock() > 0|| shoppingCartList.get(i).getGoodsDetails().getZeroStock()==1 || shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(s).getGxcGoodsState()==1){
                                  break;
                              }
                          }
                        }
                    }
                    //说明有有货的商品
                    if (s < shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size()) {
                        break;
                    }
                }
            }
        }
        //说明都是无货的
        if (i == shoppingCartList.size()) {
            Log.d(TAG, "checkbox不能点击了，因为都是无货的");
            shopCartListCheckAll.setClickable(false);
            shopCartListCheckAll.setEnabled(false);
            shopCartListCheckAll.setButtonDrawable(getResources().getDrawable(R.drawable.icon_checkbox_disable));
        }
    }

    public void initializeAdapter() {
        //给RecyclerView设置布局方向
        shopCartListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //初始化适配器
        shopCartListAdapter = new ShopCartListAdapter(R.layout.shop_cart_list_item, shoppingCartList);
        //给RecyclerView添加适配器
        shopCartListRecyclerView.setAdapter(shopCartListAdapter);

        setAdapterListener(shopCartListAdapter);
    }


    public void setAdapterListener(ShopCartListAdapter shopCartListAdapter) {

        shopCartListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                chooseGoodsSpecPosition = position;
                CheckBox itemCheckBox = view.findViewById(R.id.shop_cart_list_item_checkbox);

                switch (view.getId()) {

                    case R.id.shop_cart_list_item_checkbox:
                        if (!itemCheckBox.isChecked()) {
                            Log.d(TAG, "itemCheckBox.isChecked():false");
                            shopCartListCheckAll.setChecked(false);

                            for (int i = 0; i < checkedGoodsList.size(); i++) {

                                if (checkedGoodsList.get(i).getId() == shoppingCartList.get(position).getId()) {
                                    checkedGoodsList.remove(i);
                                    break;
                                }
                            }
                        } else {
                            Log.d(TAG, "itemCheckBox.isChecked():true");
                            checkedGoodsList.add(shoppingCartList.get(position));
                            if (checkedGoodsList.size() == (shoppingCartList.size() - noGoodsList.size())) {
                                // Log.d(TAG, "truetruetruetruetruetrue");
                                shopCartListCheckAll.setChecked(true);
                            } else {
                                shopCartListCheckAll.setChecked(false);
                            }
                        }
                        Log.d(TAG, "checkedGoodsList.size():" + checkedGoodsList.size());
                        countGoodsAllPrice();
                        shopCartListAdapter.setCheckAll(shopCartListCheckAll.isChecked());
                        shopCartListAdapter.setCheckedGoodsList(checkedGoodsList);
                        shopCartListAdapter.notifyDataSetChanged();
                        break;
                    //点击了减号
                    case R.id.tv_reduce:
                        //Toast.makeText(getContext(), "bbb", Toast.LENGTH_SHORT).show();
                        //shopCartListAdapter.setCheckAddOrReduce(0);
                        //shopCartListAdapter.setCheckedChooseGoodsSpecPopIndex(position);
                        TextView tvReduce = view.findViewById(R.id.tv_reduce);
                        ShoppingCartModel.ResultDataBean changeDataBean = shoppingCartList.get(chooseGoodsSpecPosition);
                        if (changeDataBean.getGoodsDetails() != null) {
                            if (changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                                for (int i = 0; i < changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                                    if (changeDataBean.getGoodsSpecificationDetailsId() == changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId()) {
                                        if (changeDataBean.getGoodsNum() <= 1) {
                                            Toast.makeText(getContext(), "不能再减少了!", Toast.LENGTH_SHORT).show();
                                            tvReduce.setTextColor(getContext().getResources().getColor(R.color.moreText));
                                        } else {
                                            changeDataBean.setGoodsNum(changeDataBean.getGoodsNum() - 1);

                                            //没有处在编辑页面下，则直接对数据库进行修改
                                            if (!isChooseEditBtn) {
                                                editShoppingCartGoodsById(changeDataBean.getId(), userId, 0, changeDataBean.getGoodsSpecificationDetailsId(), changeDataBean.getGoodsNum());
                                            }
                                            //处在编辑页面下，把用户进行修改的数据存储起来
                                            else {
                                                //判断有没有重复的信息
                                                int k = 0;
                                                for (k = 0; k < changeShopCartGoodsMsgList.size(); k++) {
                                                    if (changeShopCartGoodsMsgList.get(k).getId() == changeDataBean.getId()) {
                                                        break;
                                                    }
                                                }
                                                if (k == changeShopCartGoodsMsgList.size()) {
                                                    changeShopCartGoodsMsgList.add(changeDataBean);
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        shopCartListAdapter.notifyDataSetChanged();
                        countGoodsAllPrice();
                        break;
                    //点击了加号
                    case R.id.tv_add:
                        TextView tvAdd = view.findViewById(R.id.tv_add);
                        ShoppingCartModel.ResultDataBean changeDataBean2 = shoppingCartList.get(chooseGoodsSpecPosition);
                        if (changeDataBean2.getGoodsDetails() != null) {
                            if (changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                                for (int i = 0; i < changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                                    if (changeDataBean2.getGoodsSpecificationDetailsId() == changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId()) {
                                        if (changeDataBean2.getGoodsNum() >= changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock()) {
                                           if(changeDataBean2.getGoodsDetails().getZeroStock()==0&&changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2){
                                               Toast.makeText(getContext(), "已到库存上限!", Toast.LENGTH_SHORT).show();
                                               tvAdd.setTextColor(getContext().getResources().getColor(R.color.moreText));
                                           }
                                           else{
                                               if (changeDataBean2.getGoodsNum() >= 500) {
                                                   changeDataBean2.setGoodsNum(500);
                                                   tvAdd.setTextColor(getContext().getResources().getColor(R.color.moreText));
                                               } else {
                                                   changeDataBean2.setGoodsNum(changeDataBean2.getGoodsNum() + 1);
                                                   if (changeDataBean2.getGoodsNum()==500){
                                                       tvAdd.setTextColor(getContext().getResources().getColor(R.color.moreText));
                                                   }
                                                   else{
                                                       tvAdd.setTextColor(getContext().getResources().getColor(R.color.trans_333333));
                                                   }

                                                   //没有处在编辑页面下，则直接对数据库进行修改
                                                   if (!isChooseEditBtn) {
                                                       editShoppingCartGoodsById(changeDataBean2.getId(), userId, 1, changeDataBean2.getGoodsSpecificationDetailsId(), changeDataBean2.getGoodsNum());

                                                   }
                                                   //处在编辑页面下，把用户进行修改的数据存储起来
                                                   else {
                                                       //判断有没有重复的信息
                                                       int k = 0;
                                                       for (k = 0; k < changeShopCartGoodsMsgList.size(); k++) {
                                                           if (changeShopCartGoodsMsgList.get(k).getId() == changeDataBean2.getId()) {
                                                               break;
                                                           }
                                                       }
                                                       if (k == changeShopCartGoodsMsgList.size()) {
                                                           changeShopCartGoodsMsgList.add(changeDataBean2);
                                                       }
                                                   }
                                               }
                                           }

                                        } else {
                                            if (changeDataBean2.getGoodsNum() >= 500) {
                                                changeDataBean2.setGoodsNum(500);
                                                tvAdd.setTextColor(getContext().getResources().getColor(R.color.moreText));
                                            } else {
                                                changeDataBean2.setGoodsNum(changeDataBean2.getGoodsNum() + 1);
                                                if (changeDataBean2.getGoodsNum()==500){
                                                    tvAdd.setTextColor(getContext().getResources().getColor(R.color.moreText));
                                                }
                                                else{
                                                    tvAdd.setTextColor(getContext().getResources().getColor(R.color.trans_333333));
                                                }

                                                //没有处在编辑页面下，则直接对数据库进行修改
                                                if (!isChooseEditBtn) {
                                                    editShoppingCartGoodsById(changeDataBean2.getId(), userId, 1, changeDataBean2.getGoodsSpecificationDetailsId(), changeDataBean2.getGoodsNum());

                                                }
                                                //处在编辑页面下，把用户进行修改的数据存储起来
                                                else {
                                                    //判断有没有重复的信息
                                                    int k = 0;
                                                    for (k = 0; k < changeShopCartGoodsMsgList.size(); k++) {
                                                        if (changeShopCartGoodsMsgList.get(k).getId() == changeDataBean2.getId()) {
                                                            break;
                                                        }
                                                    }
                                                    if (k == changeShopCartGoodsMsgList.size()) {
                                                        changeShopCartGoodsMsgList.add(changeDataBean2);
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        shopCartListAdapter.notifyDataSetChanged();
                        countGoodsAllPrice();
                        break;
                    //点击数量，直接修改
                    case R.id.shop_cart_goods_buy_num:
                        buyNumTextView = view.findViewById(R.id.shop_cart_goods_buy_num);
                        ShoppingCartModel.ResultDataBean changeDataBean3 = shoppingCartList.get(chooseGoodsSpecPosition);
                        if (changeDataBean3.getGoodsDetails() != null) {
                            if (changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                                for (int i = 0; i < changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                                    if (changeDataBean3.getGoodsSpecificationDetailsId() == changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId()) {
                                        modifyNumPopu = new ModifyNumPopu(getActivity(), changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock(), buyNumTextView.getText().toString().trim(),changeDataBean3.getGoodsDetails().getZeroStock(),changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState());
                                        modifyNumPopu.setOnClickOkBtn(ShopCartFragment.this);
                                        modifyNumPopu.showAtLocation(getActivity().findViewById(R.id.shop_cart_list_page), Gravity.CENTER, 0, 0);
                                    }
                                }
                            }
                        }
                        break;
                    //编辑时，点击了每一行后面的删除按钮
                    case R.id.shop_cart_list_item_delete:
                        //-------执行后台的删除操作------

                        deleteShoppingCartById(shoppingCartList.get(position).getId(), userId);
                        shoppingCartList.remove(position);
                        if(shoppingCartList.size()<=0){
                            shopCartContentNoGoods.setVisibility(View.VISIBLE);
                            shopCartContentHasGoods.setVisibility(View.GONE);
                            shopCartFooter.setVisibility(View.GONE);
                        }
                        else{
                            shopCartListAdapter.setCheckEditBtn(true);
                            shopCartListAdapter.setCheckAll(shopCartListCheckAll.isChecked());
                            shopCartListAdapter.setCheckedGoodsList(checkedGoodsList);
                            shopCartListAdapter.notifyDataSetChanged();
                        }

                        break;
                    //点击选择规格
                    case R.id.shop_cart_choose_goods_spec_btn:
                        TextView tv = view.findViewById(R.id.shop_cart_edit_goods_spec);
                        if (shoppingCartList.get(position) != null) {
                            if (shoppingCartList.get(position).getGoodsDetails() != null) {
                                //弹出选择规格的pop
                                chooseGoodsSpecPopu = new ChooseGoodsSpecPopu(getActivity(), tv.getText().toString().trim(), 1, shoppingCartList.get(position).getGoodsDetails());
                                chooseGoodsSpecPopu.showAtLocation(getActivity().findViewById(R.id.shop_cart_list_page), Gravity.BOTTOM, 0, 0);
                                setChooseGoodsSpecPopuListener();
                            } else {
                                Toast.makeText(getContext(), "获取信息出错，购物车无该商品。", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "获取信息出错，购物车无该商品。", Toast.LENGTH_LONG).show();
                        }

                        break;
                    case R.id.goods_activity_more_info:
                        //获取商品参加的活动信息
                        goodsHasActivity.clear();
                        ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX activitysBeanX;
                        ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX.ActivityInformationBeanX activityInformationBeanX;
                        if (shoppingCartList.get(position) != null) {
                            if (shoppingCartList.get(position).getGoodsDetails() != null) {
                                if (shoppingCartList.get(position).getGoodsDetails().getGoodsActivitys().size() > 0) {
                                    for (int i = 0; i < shoppingCartList.get(position).getGoodsDetails().getGoodsActivitys().size(); i++) {
                                        goodsHasActivity.add(shoppingCartList.get(position).getGoodsDetails().getGoodsActivitys().get(i));
                                    }
                                }
                                if (shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                                    for (int i = 0; i < shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                                        if (shoppingCartList.get(position).getGoodsSpecificationDetailsId() == shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().get(i).getId()) {
                                            if (shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().get(i).getGoodsActivitys().size() > 0) {
                                                for (int a = 0; a < shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().get(i).getGoodsActivitys().size(); a++) {
                                                    ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean.GoodsActivitysBean goodsActivitysBean = shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().get(i).getGoodsActivitys().get(a);
                                                    activitysBeanX = new ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX();
                                                    activitysBeanX.setId(goodsActivitysBean.getId());
                                                    activitysBeanX.setGoodsId(goodsActivitysBean.getGoodsId());
                                                    activitysBeanX.setGoodsSpecificationDetails(goodsActivitysBean.getGoodsSpecificationDetails());
                                                    activitysBeanX.setState(goodsActivitysBean.getState());
                                                    activityInformationBeanX = new ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX.ActivityInformationBeanX();
                                                    activityInformationBeanX.setId(goodsActivitysBean.getActivityInformation().getId());
                                                    activityInformationBeanX.setIdentifier(goodsActivitysBean.getActivityInformation().getIdentifier());
                                                    activityInformationBeanX.setName(goodsActivitysBean.getActivityInformation().getName());
                                                    activityInformationBeanX.setActivityType(goodsActivitysBean.getActivityInformation().getActivityType());
                                                    activityInformationBeanX.setPrice(goodsActivitysBean.getActivityInformation().getPrice());
                                                    activityInformationBeanX.setDiscount(goodsActivitysBean.getActivityInformation().getDiscount());
                                                    activityInformationBeanX.setMaxNum(goodsActivitysBean.getActivityInformation().getMaxNum());
                                                    activityInformationBeanX.setBeginValidityTime(goodsActivitysBean.getActivityInformation().getBeginValidityTime());
                                                    activityInformationBeanX.setEndValidityTime(goodsActivitysBean.getActivityInformation().getEndValidityTime());
                                                    activitysBeanX.setActivityInformation(activityInformationBeanX);

                                                    goodsHasActivity.add(activitysBeanX);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (goodsHasActivity.size() > 1) {
                            chooseGoodsActivityPopu = new ChooseGoodsActivityPopu(getContext(), goodsHasActivity);
                            chooseGoodsActivityPopu.showAtLocation(getActivity().findViewById(R.id.shop_cart_list_page), Gravity.BOTTOM, 0, 0);
                        }

                        break;
                }


            }
        });
        shopCartListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (shoppingCartList.get(position).getGoodsDetails() != null && shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                    for (int i = 0; i < shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                        if (shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().get(i).getId() == shoppingCartList.get(position).getGoodsSpecificationDetailsId()) {
                            //购物车商品有效
                            if (shoppingCartList.get(position).getState()==0){
                                //不是预售且不允许0库存出库
                                if(shoppingCartList.get(position).getGoodsDetails().getZeroStock()==0&&shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2){
                                    if(shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock() > 0){
                                        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                                        intent.putExtra("goodsId", shoppingCartList.get(position).getGoodsDetailsId());
                                        startActivity(intent);
                                    }
                                }
                                //预售商品
                                else if(shoppingCartList.get(position).getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==1){
                                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                                    intent.putExtra("goodsId", shoppingCartList.get(position).getGoodsDetailsId());
                                    intent.putExtra("activityId",shoppingCartList.get(position).getActivityId());
                                    intent.putExtra("activityName",shoppingCartList.get(position).getActivityName());
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                                    intent.putExtra("goodsId", shoppingCartList.get(position).getGoodsDetailsId());
                                    startActivity(intent);
                                }

                            }
                        }
                    }
                }

            }
        });

    }

    //直接修改数量的底部确定按钮的点击事件
    @Override
    public void OnClickOkBtn(String num) {
        buyNumTextView.setText(num);
        modifyNumPopu.dismiss();
        ShoppingCartModel.ResultDataBean changeDataBean2 = shoppingCartList.get(chooseGoodsSpecPosition);
        if (changeDataBean2.getGoodsDetails() != null) {
            if (changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                for (int i = 0; i < changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                    if (changeDataBean2.getGoodsSpecificationDetailsId() == changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId()) {

                        changeDataBean2.setGoodsNum(Integer.parseInt(num));

                        //没有处在编辑页面下，则直接对数据库进行修改
                        if (!isChooseEditBtn) {
                            editShoppingCartGoodsById(changeDataBean2.getId(), userId, 2, changeDataBean2.getGoodsSpecificationDetailsId(), changeDataBean2.getGoodsNum());

                        }
                        //处在编辑页面下，把用户进行修改的数据存储起来
                        else {
                            //判断有没有重复的信息
                            int k = 0;
                            for (k = 0; k < changeShopCartGoodsMsgList.size(); k++) {
                                if (changeShopCartGoodsMsgList.get(k).getId() == changeDataBean2.getId()) {
                                    break;
                                }
                            }
                            if (k == changeShopCartGoodsMsgList.size()) {
                                changeShopCartGoodsMsgList.add(changeDataBean2);
                            }
                        }

                    }
                }
            }
        }
        shopCartListAdapter.notifyDataSetChanged();
        countGoodsAllPrice();
    }

    //删除购物车里的信息(单个)
    public void deleteShoppingCartById(int id, Integer userId) {
        Net.get().deleteShoppingCartById(userId, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() != 200) {
                        Toast.makeText(getContext(), postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //删除购物车里的信息(批量)
    public void deleteBatchShoppingCartById(int[] ids, Integer userId) {
        Net.get().deleteBatchShoppingCartById(userId, ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() != 200) {
                        Toast.makeText(getContext(), postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //购物车的编辑事件
    public void editShoppingCartGoodsById_updateGoodsSpec(Integer id, Integer userId, Integer operation, Integer goodsSpecificationDetailsId, Integer goodsNum) {
        Net.get().editShoppingCartGoodsById(id, userId, operation, goodsSpecificationDetailsId, goodsNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() != 200) {
                        Toast.makeText(getContext(), postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        forNum++;
                        if (forNum == changeShopCartGoodsMsgList.size()) {
                            //重新获取数据
                            getShoppingCartByUserId(userId);
                            forNum = 0;
                        }
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //(在购物车中+/-数量)
    public void editShoppingCartGoodsById(Integer id, Integer userId, Integer operation, Integer goodsSpecificationDetailsId, Integer goodsNum) {
        Net.get().editShoppingCartGoodsById(id, userId, operation, goodsSpecificationDetailsId, goodsNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() != 200) {
                        Toast.makeText(getContext(), postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //获取用户的购物车信息
    public void getShoppingCartByUserId(Integer userId) {
        Net.get().getShoppingCart(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shopingCartModel -> {
                    cancelProgressDialog();
                    if (shopingCartModel.getCode() == 200) {
                        shoppingCartList = shopingCartModel.getResultData();
                        decideCheckAllIsHasChoose();
                        decideIsHasGoodsStock();
                        setDataToControl();
                    } else {
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    public void decideIsHasGoodsStock() {
        //判断是否有货，若无货/已失效，则要放在列表的最后
        noGoodsList = new ArrayList<>();

        for (int i = 0; i < shoppingCartList.size(); i++) {
            if (shoppingCartList.get(i).getGoodsDetails() != null) {

                if (shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                    for (int s = 0; s < shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size(); s++) {
                        if (shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(s).getId() == shoppingCartList.get(i).getGoodsSpecificationDetailsId()) {

                            //该商品有效
                            if (shoppingCartList.get(i).getState()==0){
                                //不是预售并且不允许0库存出库
                                if (shoppingCartList.get(i).getGoodsDetails().getZeroStock()==0&&shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(s).getGxcGoodsState()==2){
                                    //该商品库存不足
                                    if (shoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(s).getGxcGoodsStock() <= 0) {
                                        ShoppingCartModel.ResultDataBean dataBean = shoppingCartList.get(i);
                                        shoppingCartList.remove(i);
                                        noGoodsList.add(dataBean);
                                        i--;
                                        break;
                                    }
                                }

                            }
                            //该商品无效
                            else{
                                ShoppingCartModel.ResultDataBean dataBean = shoppingCartList.get(i);
                                shoppingCartList.remove(i);
                                noGoodsList.add(dataBean);
                                i--;
                                break;
                            }
                        }
                    }
                }
            }
            else{
                ShoppingCartModel.ResultDataBean dataBean = shoppingCartList.get(i);
                shoppingCartList.remove(i);
                noGoodsList.add(dataBean);
                i--;
                break;
            }
        }
        for (int i = 0; i < noGoodsList.size(); i++) {
            shoppingCartList.add(noGoodsList.get(i));
        }

    }


    public void countGoodsAllPrice() {
        double allGoodsPrice = 0;
        for (int i = 0; i < checkedGoodsList.size(); i++) {
            allGoodsPrice += (checkedGoodsList.get(i).getGoodsUnitlPrice() * checkedGoodsList.get(i).getGoodsNum());
        }
        if(checkedGoodsList.size()<=0){
            shopCartFootUpBtn.setBackgroundResource(R.color.moreText);
            shopCartFootUpBtn.setEnabled(false);
        }
        else{
            shopCartFootUpBtn.setBackgroundResource(R.color.checkGreenColor);
            shopCartFootUpBtn.setEnabled(true);
        }

        chooseGoodsNum.setText(checkedGoodsList.size() + "");
        if(allGoodsPrice>1){
            shopCartTotalPrice.setText(df.format(allGoodsPrice)+"");
        }else{
            shopCartTotalPrice.setText(Double.parseDouble(df.format(allGoodsPrice))+"");
        }


    }


    //模拟数据源
   /* public void getData() {
        orderList = new ArrayList<>();
        Order order;
        for (int i = 0; i < 10; i++) {
            order = new Order();
            order.setOrderNo("HD345678912345678" + i);
            order.setGoodsName("乐肴居冰皮麻糬（芒果慕斯味) 50克*10个*12袋/" + i + "箱");
            order.setState(i);
            order.setPrice(280 + i);
            order.setGoodsSpecification(i + "箱");
            order.setGoodsNum(i);
            order.setCombinedGoodsNum(i);
            order.setCombinedNum((280.0 + i) * i + i);
            order.setPostage(i + 0.0);
            order.setGoodsStock(i);
            if (i % 2 == 0) {
                order.setPicUrl(R.drawable.img_dessert);
            } else {
                order.setPicUrl(R.drawable.img_steamed);
            }
            if (i == 2) {
                order.setIsHasActivity(1);
            } else {
                order.setIsHasActivity(0);
            }
            orderList.add(order);
        }

    }*/


    //回调方法，从pop中获取选择的规格
    @Override
    public void OnClickOkBtn(String goodsSpec, Integer goodsSpecId) {

        chooseGoodsSpecPopu.dismiss();

        shopCartListAdapter.setCheckEditBtn(true);
        shopCartListAdapter.setCheckAll(shopCartListCheckAll.isChecked());
        shopCartListAdapter.setCheckedGoodsList(checkedGoodsList);
        shopCartListAdapter.setCheckedChooseGoodsSpecPopIndex(chooseGoodsSpecPosition);
        shopCartListAdapter.setCheckedGoodsSpec(goodsSpec);
        shopCartListAdapter.notifyDataSetChanged();
        ShoppingCartModel.ResultDataBean changeDataBean = shoppingCartList.get(chooseGoodsSpecPosition);
        if (changeDataBean.getGoodsDetails() != null) {
            if (changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                for (int i = 0; i < changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                    if (changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().get(i).getSpecifications().equals(goodsSpec)) {
                        ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean goodsSpecificationDetailsBean = changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().get(i);
                        changeDataBean.setGoodsSpecificationDetailsId(goodsSpecificationDetailsBean.getId());
                        changeDataBean.setGoodsSpecificationDetailsName(goodsSpec);
                        changeDataBean.setGoodsUnitlPrice(goodsSpecificationDetailsBean.getPrice());
                        if (goodsSpecificationDetailsBean.getGoodsDisplayPictures().size() > 0) {
                            changeDataBean.setGoodsPicUrl(goodsSpecificationDetailsBean.getGoodsDisplayPictures().get(0).getPicUrl());
                        } else {
                            changeDataBean.setGoodsPicUrl("");
                        }
                    }
                }
            }
        }
        //判断有没有重复的信息
        int k = 0;
        for (k = 0; k < changeShopCartGoodsMsgList.size(); k++) {
            if (changeShopCartGoodsMsgList.get(k).getId() == changeDataBean.getId()) {
                break;
            }
        }
        if (k == changeShopCartGoodsMsgList.size()) {
            changeShopCartGoodsMsgList.add(changeDataBean);
        }
    }

    //根据用户id获取消息未读数
    public void getMessageNum(Integer userId) {
        Net.get().getMessageNum(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageNoReadNumModel -> {
                    if (messageNoReadNumModel.getCode() == 200) {
                        if (messageNoReadNumModel.getResultData().getActivityNum() > 0 || messageNoReadNumModel.getResultData().getOrderNum() > 0 || messageNoReadNumModel.getResultData().getUserMessageNum() > 0) {
                            shopCartMessage.setImageResource(R.drawable.icon_message_green_many);
                        } else {
                            shopCartMessage.setImageResource(R.drawable.icon_message_green_l);
                        }
                    } else {
                        Toast.makeText(getContext(), messageNoReadNumModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * 判断所选商品是否可以同时下单（预售商品和非预售商品）
     * @return  1 预售商品与其他商品不能同时下单
     *          2 商品均为预售商品
     *          3 商品均为非预售商品
     */
    private int checkOrderSubmit(){
        int isPreSell, beforePreSell = -1;
        int result = -1;
        for (int i = 0; i < checkedGoodsList.size(); i++) {
            isPreSell = checkedGoodsList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(0).getGxcGoodsState();
            if (i==0){//获取第一个商品的是否是预售字段  赋值给BeforePreSell
                beforePreSell = isPreSell;
            }else{
                if(isPreSell != beforePreSell){//预售商品与其他商品不能同时下单
                    result = 1;//预售商品与其他商品不能同时下单
                    break;
                }
            }
        }
        if(result == -1){//商品均为预售商品   或   商品均为非预售商品
            if(beforePreSell == 1){//商品均为预售商品
                result = 2;
            }else{//商品均为非预售商品
                result = 3;
            }
        }
        return result;
    }
    /**
     * 判断所选商品是否可以同时下单（预售商品是否参与同一个预售活动）
     * @return  false 不可以
     *          true  可以
     */
    private boolean isSamePreSellActivity(){
        int activityId, beforeActivityId = -1;
        for (int i = 0; i < checkedGoodsList.size(); i++) {
            activityId = checkedGoodsList.get(i).getActivityId();
            if (i==0){//获取第一个商品参与的预售活动id  beforeActivityId
                beforeActivityId = activityId;
            }else{
                if(activityId != beforeActivityId){//参与不同预售活动的商品不能同时下单
                    return false;
                }
            }
        }
        return true;
    }
}
