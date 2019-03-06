package com.jl.jlapp.popu;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.GoodsMsgSpecificationAdapter;
import com.jl.jlapp.adapter.ShopCartPageGoodsSpecificationAdapter;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.eneity.ShoppingCartModel;
import com.jl.jlapp.nets.AppFinal;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/18 0018.
 * 购物车页编辑时的选择规格的弹窗
 */

public class ChooseGoodsSpecPopu extends PopupWindow {
    private View view;
    private TextView textView, okBtn, goodsSpecPrice, goodsSpecNo, addShopCartBtn, buyNowBtn, tReduce, tvAdd, noGoodsNum;
    private EditText tvBuyNum;
    private ImageView goodsSpecImg, closeBtn;
    private RecyclerView goodsSpecificationRecyclerView;
    private ShopCartPageGoodsSpecificationAdapter shopCartPageGoodsSpecificationAdapter;
    private GoodsMsgSpecificationAdapter goodsMsgSpecificationAdapter;
    String checkedGoodsSpec = "";//保存选择的商品规格
    Integer checkedGoodsSpecId = 0;//保存选择的商品规格id
    private OnClickOkBtnListener onClickOkBtnListener;
    private Integer isFromGoodsOrShopCart = -1;//从商品页面点开还是从购物车页面点开  0是商品页面，1是购物车页面
    List<GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean> goodsSpecificationDetailsBeans;
    private OnClickAddShopCartBtnListener onClickAddShopCartBtnListener;
    private OnClickBuyNowBtnListener onClickBuyNowBtnListener;
    private OnClickGoodsSpecItemListener onClickGoodsSpecItemListener;
    private Integer isPresell=0;

    public void setOnClickGoodsSpecItemListener(OnClickGoodsSpecItemListener onClickGoodsSpecItemListener) {
        this.onClickGoodsSpecItemListener = onClickGoodsSpecItemListener;
    }

    public void setOnClickBuyNowBtnListener(OnClickBuyNowBtnListener onClickBuyNowBtnListener) {
        this.onClickBuyNowBtnListener = onClickBuyNowBtnListener;
    }

    public void setOnClickAddShopCartBtnListener(OnClickAddShopCartBtnListener onClickAddShopCartBtnListener) {
        this.onClickAddShopCartBtnListener = onClickAddShopCartBtnListener;
    }

    public void setOnClickOkBtnListener(OnClickOkBtnListener onClickOkBtnListener) {
        this.onClickOkBtnListener = onClickOkBtnListener;
    }

    //从购物车页面点开
    public ChooseGoodsSpecPopu(final Context context, String defaultGoodsSpec, Integer isFromGoodsOrShopCart, ShoppingCartModel.ResultDataBean.GoodsDetailsBean goodsDetailsBean) {
        super(context);
        this.isFromGoodsOrShopCart = isFromGoodsOrShopCart;


        //传入之前用户选择的规格
        this.checkedGoodsSpec = defaultGoodsSpec;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.choose_goods_spec_popup_layout, null);
        textView = view.findViewById(R.id.shadow_part);
        okBtn = view.findViewById(R.id.choose_spec_bottom_btn);
        closeBtn = view.findViewById(R.id.close_btn);
        goodsSpecPrice = view.findViewById(R.id.goods_spec_price);
        goodsSpecNo = view.findViewById(R.id.goods_spec_no);
        goodsSpecImg = view.findViewById(R.id.goods_spec_img);

        RelativeLayout chooseSpecNumLayout = view.findViewById(R.id.choose_spec_num_layout);

        goodsSpecificationRecyclerView = view.findViewById(R.id.goods_spec_recycler_view);

        for (int i = 0; i < goodsDetailsBean.getGoodsSpecificationDetails().size(); i++) {
            if (checkedGoodsSpec.equals(goodsDetailsBean.getGoodsSpecificationDetails().get(i).getSpecifications())) {
                isPresell=goodsDetailsBean.getGoodsSpecificationDetails().get(i).getGxcGoodsState();
                goodsSpecPrice.setText(goodsDetailsBean.getGoodsSpecificationDetails().get(i).getPrice() + "");
                goodsSpecNo.setText((String) goodsDetailsBean.getGoodsSpecificationDetails().get(i).getIdentifier());
                if (goodsDetailsBean.getGoodsSpecificationDetails().get(i).getGoodsDisplayPictures().size() > 0) {
                    String picUrl = goodsDetailsBean.getGoodsSpecificationDetails().get(i).getGoodsDisplayPictures().get(0).getPicUrl();
                    Glide
                            .with(context)
                            .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                            .load(AppFinal.BASEURL + picUrl)
                            .into(goodsSpecImg);
                }
                //无图片
                else {
                    Glide
                            .with(context)
                            .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                            .load(R.drawable.img_noimg_xs)
                            .into(goodsSpecImg);
                }
            }
        }

        //从商品页面弹出时，显示选择数量
        if (isFromGoodsOrShopCart == 0) {
            chooseSpecNumLayout.setVisibility(View.VISIBLE);
        } else {
            chooseSpecNumLayout.setVisibility(View.GONE);
        }
        //给显示规格的RecyclerView设置适配器
        goodsSpecificationRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        shopCartPageGoodsSpecificationAdapter = new ShopCartPageGoodsSpecificationAdapter(R.layout.choose_goods_spec_item, goodsDetailsBean.getGoodsSpecificationDetails());
        shopCartPageGoodsSpecificationAdapter.setCheckedGoodsSpc(checkedGoodsSpec);
        goodsSpecificationRecyclerView.setAdapter(shopCartPageGoodsSpecificationAdapter);
        //item点击事件
        shopCartPageGoodsSpecificationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView textView = view.findViewById(R.id.goods_spec_detail);
                textView.setBackgroundResource(R.drawable.bg_text_green_border);
                textView.setTextColor(context.getResources().getColor(R.color.checkGreenColor));
                //若不相等，则保存选择的值
                if ("".equals(checkedGoodsSpec) || !checkedGoodsSpec.equals(textView.getText().toString().trim())) {
                    checkedGoodsSpec = textView.getText().toString().trim();
                    checkedGoodsSpecId = goodsDetailsBean.getGoodsSpecificationDetails().get(position).getId();
                    isPresell=goodsDetailsBean.getGoodsSpecificationDetails().get(position).getGxcGoodsState();
                    shopCartPageGoodsSpecificationAdapter.setCheckedGoodsSpc(checkedGoodsSpec);
                    shopCartPageGoodsSpecificationAdapter.notifyDataSetChanged();

                    for (int i = 0; i < goodsDetailsBean.getGoodsSpecificationDetails().size(); i++) {
                        if (checkedGoodsSpec.equals(goodsDetailsBean.getGoodsSpecificationDetails().get(i).getSpecifications())) {
                            goodsSpecPrice.setText(goodsDetailsBean.getGoodsSpecificationDetails().get(i).getPrice() + "");
                            goodsSpecNo.setText((String) goodsDetailsBean.getGoodsSpecificationDetails().get(i).getIdentifier());
                            if (goodsDetailsBean.getGoodsSpecificationDetails().get(i).getGoodsDisplayPictures().size() > 0) {
                                String picUrl = goodsDetailsBean.getGoodsSpecificationDetails().get(i).getGoodsDisplayPictures().get(0).getPicUrl();
                                Glide
                                        .with(context)
                                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_noimg_xs))
                                        .load(AppFinal.BASEURL + picUrl)
                                        .into(goodsSpecImg);
                            }
                            //无图片
                            else {
                                Glide
                                        .with(context)
                                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_noimg_xs))
                                        .load(R.drawable.img_noimg_xs)
                                        .into(goodsSpecImg);
                            }
                        }

                    }
                }


            }
        });

        //确定按钮的点击事件
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickOkBtnListener != null) {
                    onClickOkBtnListener.OnClickOkBtn(checkedGoodsSpec, checkedGoodsSpecId);
                }
            }
        });

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //取消popupwindow的背景
        this.setBackgroundDrawable(null);

        //阴影部分的点击事件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //关闭按钮的点击事件
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    //从商品详情页点开
    public ChooseGoodsSpecPopu(final Context context, String defaultGoodsSpec, Integer isFromGoodsOrShopCart, GoodsDetailModel.ResultDataBean goodsDetailsBean) {
        super(context);
        this.isFromGoodsOrShopCart = isFromGoodsOrShopCart;
        this.goodsSpecificationDetailsBeans = goodsDetailsBean.getGoodsSpecificationDetails();

        List<GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean> beans = new ArrayList<>();

        //传入之前用户选择的规格
        this.checkedGoodsSpec = defaultGoodsSpec;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.choose_goods_spec_popup_layout_goods_page, null);
        textView = view.findViewById(R.id.shadow_part);
        addShopCartBtn = view.findViewById(R.id.choose_spec_bottom_add_shop_cart);
        buyNowBtn = view.findViewById(R.id.choose_spec_bottom_buy_now);
        goodsSpecPrice = view.findViewById(R.id.goods_spec_price);
        goodsSpecNo = view.findViewById(R.id.goods_spec_no);
        goodsSpecImg = view.findViewById(R.id.goods_spec_img);
        tvBuyNum = view.findViewById(R.id.tv_buy_num);
        tReduce = view.findViewById(R.id.tv_reduce);
        tvAdd = view.findViewById(R.id.tv_add);
        closeBtn = view.findViewById(R.id.close_btn);
        noGoodsNum = view.findViewById(R.id.no_goods_num);

        noGoodsNum.setVisibility(View.GONE);
        tReduce.setTextColor(context.getResources().getColor(R.color.moreText));

        RelativeLayout chooseSpecNumLayout = view.findViewById(R.id.choose_spec_num_layout);

        goodsSpecificationRecyclerView = view.findViewById(R.id.goods_spec_recycler_view);


        for (int i = 0; i < goodsSpecificationDetailsBeans.size(); i++) {
            if (checkedGoodsSpec.equals(goodsSpecificationDetailsBeans.get(i).getSpecifications())) {
                isPresell=goodsSpecificationDetailsBeans.get(i).getGxcGoodsState();
                checkedGoodsSpecId = goodsSpecificationDetailsBeans.get(i).getId();
                goodsSpecPrice.setText(goodsSpecificationDetailsBeans.get(i).getPrice() + "");
                goodsSpecNo.setText(goodsSpecificationDetailsBeans.get(i).getIdentifier());
                if (goodsSpecificationDetailsBeans.get(i).getGoodsDisplayPictures().size() > 0) {
                    String picUrl = goodsSpecificationDetailsBeans.get(i).getGoodsDisplayPictures().get(0).getPicUrl();
                    Glide
                            .with(context)
                            .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                            .load(AppFinal.BASEURL + picUrl)
                            .into(goodsSpecImg);
                }
                //无图片
                else {
                    Glide
                            .with(context)
                            .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                            .load(R.drawable.img_noimg_xs)
                            .into(goodsSpecImg);
                }
            }
            //不是预售且不允许0库存出库
            if(goodsDetailsBean.getZeroStock()==0&&goodsSpecificationDetailsBeans.get(i).getGxcGoodsState()==2){
                if (goodsSpecificationDetailsBeans.get(i).getGxcGoodsStock() <= 0) {
                    beans.add(goodsSpecificationDetailsBeans.get(i));
                }
                int goodsNum = Integer.parseInt(tvBuyNum.getText().toString().trim());
                if (goodsNum >= goodsSpecificationDetailsBeans.get(i).getGxcGoodsStock()) {
                    tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                } else {
                    tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                }
            }
            else{
                tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
            }
            tReduce.setTextColor(context.getResources().getColor(R.color.moreText));
        }
        if (beans.size() > 0) {
            for (int i = 0; i < goodsSpecificationDetailsBeans.size(); i++) {
                for (int k = 0; k < beans.size(); k++) {
                    if (goodsSpecificationDetailsBeans.get(i).getId()==beans.get(k).getId()){
                        goodsSpecificationDetailsBeans.remove(i);
                        goodsSpecificationDetailsBeans.add(beans.get(k));
                        beans.remove(k);
                        i--;
                        break;
                    }
                }
            }
        }

        //从商品页面弹出时，显示选择数量
        if (isFromGoodsOrShopCart == 0) {
            chooseSpecNumLayout.setVisibility(View.VISIBLE);
        } else {
            chooseSpecNumLayout.setVisibility(View.GONE);
        }

        //给显示规格的RecyclerView设置适配器
        goodsSpecificationRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        goodsMsgSpecificationAdapter = new GoodsMsgSpecificationAdapter(R.layout.choose_goods_spec_item, goodsSpecificationDetailsBeans);
        goodsMsgSpecificationAdapter.setCheckedGoodsSpc(checkedGoodsSpec);
        goodsSpecificationRecyclerView.setAdapter(goodsMsgSpecificationAdapter);
        //item点击事件
        goodsMsgSpecificationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView textView = view.findViewById(R.id.goods_spec_detail);
                textView.setBackgroundResource(R.drawable.bg_text_green_border);
                textView.setTextColor(context.getResources().getColor(R.color.checkGreenColor));

                if (goodsSpecificationDetailsBeans.get(position).getGxcGoodsStock() <= 0) {
                    //不是预售且不允许0库存出库
                    if(goodsDetailsBean.getZeroStock()==0&&goodsSpecificationDetailsBeans.get(position).getGxcGoodsState()==2){
                        isPresell=2;
                        noGoodsNum.setVisibility(View.VISIBLE);
                        addShopCartBtn.setBackgroundResource(R.color.moreText);
                        buyNowBtn.setBackgroundResource(R.color.moreText);
                        addShopCartBtn.setEnabled(false);
                        buyNowBtn.setEnabled(false);
                        chooseSpecNumLayout.setVisibility(View.GONE);
                        //若不相等，则保存选择的值
                        if ("".equals(checkedGoodsSpec) || !checkedGoodsSpec.equals(textView.getText().toString().trim())) {
                            checkedGoodsSpec = textView.getText().toString().trim();
                            checkedGoodsSpecId = goodsSpecificationDetailsBeans.get(position).getId();
                            goodsMsgSpecificationAdapter.setCheckedGoodsSpc(checkedGoodsSpec);
                            goodsMsgSpecificationAdapter.notifyDataSetChanged();

                            for (int i = 0; i < goodsSpecificationDetailsBeans.size(); i++) {
                                if (checkedGoodsSpec.equals(goodsSpecificationDetailsBeans.get(i).getSpecifications())) {
                                    goodsSpecPrice.setText(goodsSpecificationDetailsBeans.get(i).getPrice() + "");
                                    goodsSpecNo.setText(goodsSpecificationDetailsBeans.get(i).getIdentifier());
                                    if (goodsSpecificationDetailsBeans.get(i).getGoodsDisplayPictures().size() > 0) {
                                        String picUrl = goodsSpecificationDetailsBeans.get(i).getGoodsDisplayPictures().get(0).getPicUrl();
                                        Glide
                                                .with(context)
                                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                                                .load(AppFinal.BASEURL + picUrl)
                                                .into(goodsSpecImg);
                                    }
                                    //无图片
                                    else {
                                        Glide
                                                .with(context)
                                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                                                .load(R.drawable.img_noimg_xs)
                                                .into(goodsSpecImg);
                                    }
                                }
                            }
                        }
                    }
                    else{
                        isPresell=1;
                        noGoodsNum.setVisibility(View.GONE);
                        addShopCartBtn.setBackgroundResource(R.color.checkGreenColor);
                        buyNowBtn.setBackgroundResource(R.color.orange);
                        addShopCartBtn.setEnabled(true);
                        buyNowBtn.setEnabled(true);
                        chooseSpecNumLayout.setVisibility(View.VISIBLE);

                        //若不相等，则保存选择的值
                        if ("".equals(checkedGoodsSpec) || !checkedGoodsSpec.equals(textView.getText().toString().trim())) {
                            checkedGoodsSpec = textView.getText().toString().trim();
                            checkedGoodsSpecId = goodsSpecificationDetailsBeans.get(position).getId();
                            goodsMsgSpecificationAdapter.setCheckedGoodsSpc(checkedGoodsSpec);
                            goodsMsgSpecificationAdapter.notifyDataSetChanged();

                            for (int i = 0; i < goodsSpecificationDetailsBeans.size(); i++) {
                                if (checkedGoodsSpec.equals(goodsSpecificationDetailsBeans.get(i).getSpecifications())) {
                                    goodsSpecPrice.setText(goodsSpecificationDetailsBeans.get(i).getPrice() + "");
                                    goodsSpecNo.setText(goodsSpecificationDetailsBeans.get(i).getIdentifier());
                                    if (goodsSpecificationDetailsBeans.get(i).getGoodsDisplayPictures().size() > 0) {
                                        String picUrl = goodsSpecificationDetailsBeans.get(i).getGoodsDisplayPictures().get(0).getPicUrl();
                                        Glide
                                                .with(context)
                                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                                                .load(AppFinal.BASEURL + picUrl)
                                                .into(goodsSpecImg);
                                    }
                                    //无图片
                                    else {
                                        Glide
                                                .with(context)
                                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                                                .load(R.drawable.img_noimg_xs)
                                                .into(goodsSpecImg);
                                    }
                                }

                                if (onClickGoodsSpecItemListener != null) {
                                    onClickGoodsSpecItemListener.OnClickGoodsSpecItem(checkedGoodsSpec, checkedGoodsSpecId, position);
                                }
                            }
                        }
                    }

                } else {
                    noGoodsNum.setVisibility(View.GONE);
                    addShopCartBtn.setBackgroundResource(R.color.checkGreenColor);
                    buyNowBtn.setBackgroundResource(R.color.orange);
                    addShopCartBtn.setEnabled(true);
                    buyNowBtn.setEnabled(true);
                    chooseSpecNumLayout.setVisibility(View.VISIBLE);

                    //若不相等，则保存选择的值
                    if ("".equals(checkedGoodsSpec) || !checkedGoodsSpec.equals(textView.getText().toString().trim())) {
                        isPresell=goodsSpecificationDetailsBeans.get(position).getGxcGoodsState();
                        checkedGoodsSpec = textView.getText().toString().trim();
                        checkedGoodsSpecId = goodsSpecificationDetailsBeans.get(position).getId();
                        goodsMsgSpecificationAdapter.setCheckedGoodsSpc(checkedGoodsSpec);
                        goodsMsgSpecificationAdapter.notifyDataSetChanged();

                        for (int i = 0; i < goodsSpecificationDetailsBeans.size(); i++) {
                            if (checkedGoodsSpec.equals(goodsSpecificationDetailsBeans.get(i).getSpecifications())) {
                                goodsSpecPrice.setText(goodsSpecificationDetailsBeans.get(i).getPrice() + "");
                                goodsSpecNo.setText(goodsSpecificationDetailsBeans.get(i).getIdentifier());
                                if (goodsSpecificationDetailsBeans.get(i).getGoodsDisplayPictures().size() > 0) {
                                    String picUrl = goodsSpecificationDetailsBeans.get(i).getGoodsDisplayPictures().get(0).getPicUrl();
                                    Glide
                                            .with(context)
                                            .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                                            .load(AppFinal.BASEURL + picUrl)
                                            .into(goodsSpecImg);
                                }
                                //无图片
                                else {
                                    Glide
                                            .with(context)
                                            .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                                            .load(R.drawable.img_noimg_xs)
                                            .into(goodsSpecImg);
                                }
                            }

                            if (onClickGoodsSpecItemListener != null) {
                                onClickGoodsSpecItemListener.OnClickGoodsSpecItem(checkedGoodsSpec, checkedGoodsSpecId, position);
                            }
                        }
                    }
                }

            }
        });
        if (goodsSpecificationDetailsBeans.get(0).getGxcGoodsStock() <= 0) {
            //不是预售且不允许0库存出库
            if(goodsDetailsBean.getZeroStock()==0&&goodsSpecificationDetailsBeans.get(0).getGxcGoodsState()==2){
                noGoodsNum.setVisibility(View.VISIBLE);
                addShopCartBtn.setBackgroundResource(R.color.moreText);
                buyNowBtn.setBackgroundResource(R.color.moreText);
                addShopCartBtn.setEnabled(false);
                buyNowBtn.setEnabled(false);
                chooseSpecNumLayout.setVisibility(View.GONE);

            }
            else{
                noGoodsNum.setVisibility(View.GONE);
                addShopCartBtn.setBackgroundResource(R.color.checkGreenColor);
                buyNowBtn.setBackgroundResource(R.color.orange);
                addShopCartBtn.setEnabled(true);
                buyNowBtn.setEnabled(true);
                chooseSpecNumLayout.setVisibility(View.VISIBLE);
            }

        } else {
            noGoodsNum.setVisibility(View.GONE);
            addShopCartBtn.setBackgroundResource(R.color.checkGreenColor);
            buyNowBtn.setBackgroundResource(R.color.orange);
            addShopCartBtn.setEnabled(true);
            buyNowBtn.setEnabled(true);
            chooseSpecNumLayout.setVisibility(View.VISIBLE);
        }

        //加号的点击事件
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(tvBuyNum.getText().toString().trim())) {
                    int goodsNum = Integer.parseInt(tvBuyNum.getText().toString().trim());
                    if (goodsNum >= 500) {
                        tReduce.setTextColor(context.getResources().getColor(R.color.trans_333333));
                        tvBuyNum.setText("500");
                        tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                    } else {
                        for (int i = 0; i < goodsSpecificationDetailsBeans.size(); i++) {
                            if (goodsSpecificationDetailsBeans.get(i).getId() == checkedGoodsSpecId) {
                                //不是预售且不允许0库存出库
                                if(goodsDetailsBean.getZeroStock()==0&&goodsSpecificationDetailsBeans.get(i).getGxcGoodsState()==2){
                                    if (goodsNum >= goodsSpecificationDetailsBeans.get(i).getGxcGoodsStock()) {
                                        Toast.makeText(context, "已到库存上限!", Toast.LENGTH_SHORT).show();
                                        tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                                    } else {
                                        tvBuyNum.setText((goodsNum + 1) + "");
                                        if (goodsNum + 1 == 500) {
                                            tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                                        } else {
                                            tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                                        }
                                        tReduce.setTextColor(context.getResources().getColor(R.color.trans_333333));
                                    }
                                }
                                else{
                                    tvBuyNum.setText((goodsNum + 1) + "");
                                    if (goodsNum + 1 == 500) {
                                        tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                                    } else {
                                        tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                                    }
                                    tReduce.setTextColor(context.getResources().getColor(R.color.trans_333333));
                                }

                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "请输入购买数量", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvBuyNum.setInputType(EditorInfo.TYPE_CLASS_PHONE);//设置数字键盘
        tvBuyNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        tvBuyNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!"".equals(tvBuyNum.getText().toString().trim())) {
                    if (Integer.parseInt(tvBuyNum.getText().toString().trim()) <= 0) {
                        tvBuyNum.setText("1");
                    }
                    if (Integer.parseInt(tvBuyNum.getText().toString().trim()) > 1) {
                        tReduce.setTextColor(context.getResources().getColor(R.color.trans_333333));
                    }
                    for (int i = 0; i < goodsSpecificationDetailsBeans.size(); i++) {
                        if (goodsSpecificationDetailsBeans.get(i).getId() == checkedGoodsSpecId) {
                            //不是预售且不允许0库存出库
                            if(goodsDetailsBean.getZeroStock()==0&&goodsSpecificationDetailsBeans.get(i).getGxcGoodsState()==2){
                                //数量超过库存
                                if (Integer.parseInt(tvBuyNum.getText().toString().trim()) > goodsSpecificationDetailsBeans.get(i).getGxcGoodsStock()) {
                                    Toast.makeText(context, "库存上限为" + goodsSpecificationDetailsBeans.get(i).getGxcGoodsStock(), Toast.LENGTH_SHORT).show();
                                    tvBuyNum.setText(goodsSpecificationDetailsBeans.get(i).getGxcGoodsStock() + "");
                                    tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                                }
                                //数量没有超过库存，此时判断输入的数是否超过500
                                else {
                                    if (Integer.parseInt(tvBuyNum.getText().toString().trim()) > 500) {
                                        tvBuyNum.setText("500");
                                        tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                                    } else {
                                        tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                                    }
                                }
                            }
                            else{
                                if (Integer.parseInt(tvBuyNum.getText().toString().trim()) > 500) {
                                    tvBuyNum.setText("500");
                                    tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                                } else {
                                    tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                                }
                            }
                        }
                    }
                }

            }
        });
        //减号的点击事件
        tReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(tvBuyNum.getText().toString().trim())) {
                    int goodsNum = Integer.parseInt(tvBuyNum.getText().toString().trim());
                    if (goodsNum <= 1) {
                        Toast.makeText(context, "不能再减少了!", Toast.LENGTH_SHORT).show();
                        tReduce.setTextColor(context.getResources().getColor(R.color.moreText));
                    } else {
                        tvBuyNum.setText((goodsNum - 1) + "");
                        if (goodsNum - 1 == 1) {
                            tReduce.setTextColor(context.getResources().getColor(R.color.moreText));
                        }
                        if (goodsNum - 1 < 500) {
                            for (int i = 0; i < goodsSpecificationDetailsBeans.size(); i++) {
                                if (goodsSpecificationDetailsBeans.get(i).getId() == checkedGoodsSpecId) {
                                    if (goodsNum - 1 < goodsSpecificationDetailsBeans.get(i).getGxcGoodsStock()) {
                                        tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                                    } else {
                                        tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                                    }

                                }
                            }

                        }
                    }
                } else {
                    Toast.makeText(context, "请输入购买数量", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //加入购物车按钮的点击事件
        addShopCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(tvBuyNum.getText().toString().trim())) {
                    if (onClickAddShopCartBtnListener != null) {
                        onClickAddShopCartBtnListener.OnClickAddShopCartBtn(checkedGoodsSpec, checkedGoodsSpecId, Integer.parseInt(tvBuyNum.getText().toString().trim()));
                    }
                } else {
                    Toast.makeText(context, "请输入购买数量", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //立即购买按钮的点击事件
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(tvBuyNum.getText().toString().trim())) {
                    if (onClickBuyNowBtnListener != null) {
                        onClickBuyNowBtnListener.OnClickBuyNowBtn(checkedGoodsSpec, checkedGoodsSpecId, Integer.parseInt(tvBuyNum.getText().toString().trim()),isPresell);
                    }
                } else {
                    Toast.makeText(context, "请输入购买数量", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //取消popupwindow的背景
        this.setBackgroundDrawable(null);

        //阴影部分的点击事件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //关闭按钮的点击事件
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    //模拟数据源
    private List<String> getData() {
        List<String> dataList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            dataList.add("乐肴居冰皮麻糬规格" + i);
        }

        return dataList;
    }

    //自定义回调接口
    public interface OnClickOkBtnListener {
        void OnClickOkBtn(String goodsSpec, Integer goodsSpecId);
    }

    //自定义回调接口
    public interface OnClickAddShopCartBtnListener {
        void OnClickAddShopCartBtn(String goodsSpec, Integer goodsSpecId, Integer goodsNum);
    }

    //自定义回调接口
    public interface OnClickBuyNowBtnListener {
        void OnClickBuyNowBtn(String goodsSpec, Integer goodsSpecId, Integer goodsNum,Integer isPresell);
    }

    //自定义回调接口
    public interface OnClickGoodsSpecItemListener {
        void OnClickGoodsSpecItem(String goodsSpec, Integer goodsSpecId, Integer position);
    }
}
