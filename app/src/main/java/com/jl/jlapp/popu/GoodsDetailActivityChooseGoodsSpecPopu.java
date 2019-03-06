package com.jl.jlapp.popu;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/18 0018.
 * 购物车页编辑时的选择规格的弹窗
 */

public class GoodsDetailActivityChooseGoodsSpecPopu extends PopupWindow {
    private View view;
    private TextView textView, okBtn, goodsSpecPrice, goodsSpecNo, tReduce, tvAdd;
    private EditText tvBuyNum;
    private ImageView goodsSpecImg, closeBtn;
    private RecyclerView goodsSpecificationRecyclerView;
    private ShopCartPageGoodsSpecificationAdapter shopCartPageGoodsSpecificationAdapter;
    private GoodsMsgSpecificationAdapter goodsMsgSpecificationAdapter;
    String checkedGoodsSpec = "";//保存选择的商品规格
    Integer checkedGoodsSpecId = 0;//保存选择的商品规格id
    Integer goodsId=0;//保存选择的商品id
    private OnClickOkBtnListener onClickOkBtnListener;
    List<GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean> goodsSpecificationDetailsBeans;
    private Integer isPresell=0;//是否是预售商品

    public void setOnClickOkBtnListener(OnClickOkBtnListener onClickOkBtnListener) {
        this.onClickOkBtnListener = onClickOkBtnListener;
    }

    public GoodsDetailActivityChooseGoodsSpecPopu(final Context context, String defaultGoodsSpec, GoodsDetailModel.ResultDataBean goodsDetailsBeans) {
        super(context);
        this.goodsSpecificationDetailsBeans = goodsDetailsBeans.getGoodsSpecificationDetails();

        //传入之前用户选择的规格
        this.checkedGoodsSpec = defaultGoodsSpec;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.choose_goods_spec_popup_layout, null);
        textView = view.findViewById(R.id.shadow_part);
        okBtn = view.findViewById(R.id.choose_spec_bottom_btn);
        goodsSpecPrice = view.findViewById(R.id.goods_spec_price);
        goodsSpecNo = view.findViewById(R.id.goods_spec_no);
        goodsSpecImg = view.findViewById(R.id.goods_spec_img);
        tvBuyNum = view.findViewById(R.id.tv_buy_num);
        tReduce = view.findViewById(R.id.tv_reduce);
        tvAdd = view.findViewById(R.id.tv_add);
        closeBtn = view.findViewById(R.id.close_btn);

        RelativeLayout chooseSpecNumLayout = view.findViewById(R.id.choose_spec_num_layout);
        chooseSpecNumLayout.setVisibility(View.VISIBLE);

        goodsSpecificationRecyclerView = view.findViewById(R.id.goods_spec_recycler_view);


        for (int i = 0; i < goodsSpecificationDetailsBeans.size(); i++) {
            if (checkedGoodsSpec.equals(goodsSpecificationDetailsBeans.get(i).getSpecifications())) {
                isPresell=goodsSpecificationDetailsBeans.get(i).getGxcGoodsState();
                checkedGoodsSpecId = goodsSpecificationDetailsBeans.get(i).getId();
                goodsId=goodsSpecificationDetailsBeans.get(i).getGoodsId();
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
            int goodsNum = Integer.parseInt(tvBuyNum.getText().toString().trim());
            //不允许0库存出库并且不是预售
            if(goodsDetailsBeans.getZeroStock()==0&&goodsSpecificationDetailsBeans.get(i).getGxcGoodsState()==2){
                if (goodsNum >= goodsSpecificationDetailsBeans.get(i).getGxcGoodsStock()) {
                    tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                } else {
                    tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                }
            }else{
                tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
            }
            tReduce.setTextColor(context.getResources().getColor(R.color.moreText));
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

                //若不相等，则保存选择的值
                if ("".equals(checkedGoodsSpec) || !checkedGoodsSpec.equals(textView.getText().toString().trim())) {
                    isPresell=goodsSpecificationDetailsBeans.get(position).getGxcGoodsState();
                    checkedGoodsSpec = textView.getText().toString().trim();
                    checkedGoodsSpecId = goodsSpecificationDetailsBeans.get(position).getId();
                    goodsId=goodsSpecificationDetailsBeans.get(position).getGoodsId();
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
        });

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
                                //不允许0库存出库并且不是预售
                                if(goodsDetailsBeans.getZeroStock()==0&&goodsSpecificationDetailsBeans.get(i).getGxcGoodsState()==2){
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
        //直接改数字
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
                            //不允许0库存出库并且不是预售
                            if(goodsDetailsBeans.getZeroStock()==0&&goodsSpecificationDetailsBeans.get(i).getGxcGoodsState()==2){
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
                                    //不允许0库存出库并且不是预售
                                    if(goodsDetailsBeans.getZeroStock()==0&&goodsSpecificationDetailsBeans.get(i).getGxcGoodsState()==2){
                                        if (goodsNum - 1 < goodsSpecificationDetailsBeans.get(i).getGxcGoodsStock()) {
                                            tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                                        } else {
                                            tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                                        }
                                    }
                                    else{
                                        tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
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

        //确定按钮的点击事件
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickOkBtnListener != null) {
                    onClickOkBtnListener.OnClickOkBtn(goodsId, checkedGoodsSpecId,Integer.parseInt(tvBuyNum.getText().toString().trim()),isPresell);
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

    //自定义回调接口
    public interface OnClickOkBtnListener {
        void OnClickOkBtn(Integer goodsId, Integer goodsSpecId, Integer goodsNum,Integer isPresell);
    }

}
