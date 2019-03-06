package com.jl.jlapp.adapter;


import android.graphics.Color;
import android.graphics.Paint;
import android.nfc.Tag;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ProcessUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.Order;
import com.jl.jlapp.eneity.ShoppingCartModel;
import com.jl.jlapp.nets.AppFinal;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class ShopCartListAdapter extends BaseQuickAdapter<ShoppingCartModel.ResultDataBean, BaseViewHolder> {

    public boolean isCheckEditBtn = false;//是否点击了编辑按钮
    public boolean checkAll = false;//是否点击了全选按钮
    public List<ShoppingCartModel.ResultDataBean> checkedGoodsList = new ArrayList<>();//解决复用问题，记录都有哪些被选中
    public Integer checkedChooseGoodsSpecPopIndex = -1;//是否点击了选择商品规格
    public String checkedGoodsSpec = ""; //选中的商品规格
    public Integer checkAddOrReduce = -1;//0为减  1为加
    List<ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX> goodsHasActivity;


    public void setCheckAddOrReduce(Integer checkAddOrReduce) {
        this.checkAddOrReduce = checkAddOrReduce;
    }

    public void setCheckedGoodsSpec(String checkedGoodsSpec) {
        this.checkedGoodsSpec = checkedGoodsSpec;
    }

    public void setCheckedChooseGoodsSpecPopIndex(Integer checkedChooseGoodsSpecPopIndex) {
        this.checkedChooseGoodsSpecPopIndex = checkedChooseGoodsSpecPopIndex;
    }

    public void setCheckedGoodsList(List<ShoppingCartModel.ResultDataBean> checkedGoodsList) {
        this.checkedGoodsList = checkedGoodsList;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }

    public void setCheckEditBtn(boolean checkEditBtn) {
        this.isCheckEditBtn = checkEditBtn;
    }

    //构造函数
    public ShopCartListAdapter(@LayoutRes int layoutResId, @Nullable List<ShoppingCartModel.ResultDataBean> data) {

        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCartModel.ResultDataBean item) {
        TextView shopCartListItemDelete = helper.getView(R.id.shop_cart_list_item_delete);
        TextView shopCartListItemNoGoods = helper.getView(R.id.shop_cart_list_item_no_goods);
        TextView shopCartListItemGoodsName = helper.getView(R.id.shop_cart_list_item_goods_name);
        TextView shopCartListItemGoodsSpecifiction = helper.getView(R.id.shop_cart_list_item_goods_specifiction);
        TextView shopCartListItemGoodsMoneyUnit = helper.getView(R.id.shop_cart_list_item_goods_money_unit);
        TextView shopCartListItemGoodsMoney = helper.getView(R.id.shop_cart_list_item_goods_money);
        TextView shopCartListItemLeavingsStock = helper.getView(R.id.shop_cart_list_item_leavings_stock);
        RelativeLayout shopCartActivityItem = helper.getView(R.id.shop_cart_activity_item);
        RelativeLayout shopCartListItemChangeNum = helper.getView(R.id.shop_cart_list_item_change_num);
        CheckBox checkBox = helper.getView(R.id.shop_cart_list_item_checkbox);
        RelativeLayout shopCartChooseGoodsSpecBtn = helper.getView(R.id.shop_cart_choose_goods_spec_btn);
        TextView shopCartEditGoodsSpec = helper.getView(R.id.shop_cart_edit_goods_spec);
        TextView shopCartGoodsBuyNum = helper.getView(R.id.shop_cart_goods_buy_num);
        TextView goodsSpecStrock = helper.getView(R.id.goods_spec_strock);
        TextView shopCartActivityType = helper.getView(R.id.shop_cart_activity_type);
        TextView goodsActivityContent = helper.getView(R.id.goods_activity_content);
        TextView goodsActivityMoreInfo = helper.getView(R.id.goods_activity_more_info);
        TextView shopCartListItemGoodsOldMoney = helper.getView(R.id.shop_cart_list_item_goods_old_money);
        TextView tvReduce=helper.getView(R.id.tv_reduce);
        TextView tvAdd=helper.getView(R.id.tv_add);
        TextView shopCartListItemUndercarriage=helper.getView(R.id.shop_cart_list_item_undercarriage);

        int goodsActivityType_zk = -1;//活动类型-折扣
        int goodsActivityType_tg = -1;//活动类型-团购
        int goodsActivityType_ms = -1;//活动类型-秒杀
        int goodsActivityType_lj = -1;//活动类型-立减
        int goodsActivityType_mj = -1;//活动类型-满减
        goodsHasActivity = new ArrayList();

        //解决复用问题
        shopCartListItemDelete.setVisibility(View.GONE);
        shopCartActivityItem.setVisibility(View.GONE);
        shopCartListItemNoGoods.setVisibility(View.GONE);
        shopCartListItemUndercarriage.setVisibility(View.GONE);
        shopCartListItemChangeNum.setVisibility(View.VISIBLE);
        shopCartListItemLeavingsStock.setVisibility(View.GONE);
        shopCartListItemGoodsName.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        shopCartListItemGoodsSpecifiction.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        shopCartListItemGoodsMoneyUnit.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        shopCartListItemGoodsMoney.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        shopCartListItemGoodsSpecifiction.setVisibility(View.VISIBLE);
        shopCartChooseGoodsSpecBtn.setVisibility(View.GONE);
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setChecked(false);
        checkBox.setEnabled(true);
        checkBox.setButtonDrawable(helper.itemView.getResources().getDrawable(R.drawable.checkbox_style));
        shopCartListItemGoodsOldMoney.setVisibility(View.GONE);

        //解决复用问题
        for (int i = 0; i < checkedGoodsList.size(); i++) {
            if (item.getId() == checkedGoodsList.get(i).getId()) {
                checkBox.setChecked(true);
            }
        }
        //点击了全选
        if (checkAll) {
            checkBox.setChecked(true);
        }

        // helper.setImageResource(R.id.shop_cart_list_item_goods_img,item.getPicUrl());
        ImageView imageView = helper.getView(R.id.shop_cart_list_item_goods_img);
        Glide
                .with(helper.itemView.getContext())
                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                .load(AppFinal.BASEURL + item.getGoodsPicUrl())
                .into(imageView);
        helper.setText(R.id.shop_cart_list_item_goods_name, item.getGoodsName());
        helper.setText(R.id.shop_cart_list_item_goods_specifiction, item.getGoodsSpecificationDetailsName());
        helper.setText(R.id.shop_cart_list_item_goods_money, item.getGoodsUnitlPrice() + "");

        if (item.getGoodsDetails() != null && item.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
            for (int i = 0; i < item.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {

                if (item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId() == item.getGoodsSpecificationDetailsId()) {

                    //无参加的活动
                    if (item.getGoodsDetails().getGoodsActivitys().size() == 0 && item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGoodsActivitys().size() == 0) {
                        shopCartActivityItem.setVisibility(View.GONE);
                    }
                    //参与了活动
                    else {
                        shopCartActivityItem.setVisibility(View.VISIBLE);
                        //判断商品参与的是否是预售活动
                        int isPreSell = item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState();
                        if(isPreSell == 1){//是预售
                            //获取预售活动的名称
                            String preSellActivityName = item.getActivityName();
                            shopCartActivityType.setText("预售");
                            goodsActivityContent.setText("已参加预售活动："+preSellActivityName);
                            goodsActivityMoreInfo.setVisibility(View.GONE);

                        }else{//非预售
                            //把商品所参与的活动归纳出来
                            if (item.getGoodsDetails().getGoodsActivitys().size() > 0) {
                                for (int g = 0; g < item.getGoodsDetails().getGoodsActivitys().size(); g++) {
                                    goodsHasActivity.add(item.getGoodsDetails().getGoodsActivitys().get(g));
                                }
                            }
                            ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX activitysBeanX;
                            ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX.ActivityInformationBeanX activityInformationBeanX;
                            if (item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGoodsActivitys().size() > 0) {
                                for (int a = 0; a < item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGoodsActivitys().size(); a++) {
                                    ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean.GoodsActivitysBean goodsActivitysBean = item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGoodsActivitys().get(a);
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
                            //判断活动类型，显示优先级：折扣>团购>秒杀>立减>满减
                            int g = 0;
                            for (g = 0; g < goodsHasActivity.size(); g++) {
                                switch (goodsHasActivity.get(g).getActivityInformation().getActivityType()) {
                                    case 0:
                                        goodsActivityType_zk=g;
                                        break;
                                    case 1:
                                        goodsActivityType_tg=g;
                                        break;
                                    case 2:
                                        goodsActivityType_ms=g;
                                        break;
                                    case 3:
                                        goodsActivityType_lj=g;
                                        break;
                                    case 4:
                                        goodsActivityType_mj=g;
                                        break;
                                    default:break;
                                }
                            }
                            goodsActivityMoreInfo.setVisibility(View.GONE);
                            if(goodsHasActivity.size()>1){
                                goodsActivityMoreInfo.setVisibility(View.VISIBLE);
                            }
                            if(goodsActivityType_zk!=-1){
                                shopCartActivityType.setText("折扣");
                                goodsActivityContent.setText("打 "+goodsHasActivity.get(goodsActivityType_zk).getActivityInformation().getDiscount()*10+" 折，最多可购买"+goodsHasActivity.get(goodsActivityType_zk).getActivityInformation().getMaxNum()+"个");
                            }
                            else if(goodsActivityType_tg!=-1){
                                //shopCartListItemGoodsOldMoney.setVisibility(View.VISIBLE);
                                shopCartActivityType.setText("团购");
                                // shopCartListItemGoodsOldMoney.setText("¥"+item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getPrice());
                                // shopCartListItemGoodsMoney.setText(goodsHasActivity.get(goodsActivityType_tg).getActivityInformation().getDiscount()+"");
                                goodsActivityContent.setText("团购单价为¥"+goodsHasActivity.get(goodsActivityType_tg).getActivityInformation().getDiscount()+"，最多可购买"+goodsHasActivity.get(goodsActivityType_tg).getActivityInformation().getMaxNum()+"个");
                            }
                            else if(goodsActivityType_ms!=-1){
                                //shopCartListItemGoodsOldMoney.setVisibility(View.VISIBLE);
                                shopCartActivityType.setText("秒杀");
                                // shopCartListItemGoodsOldMoney.setText("¥"+item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getPrice());
                                // shopCartListItemGoodsMoney.setText(goodsHasActivity.get(goodsActivityType_ms).getActivityInformation().getDiscount()+"");
                                goodsActivityContent.setText("秒杀单价为¥"+goodsHasActivity.get(goodsActivityType_ms).getActivityInformation().getDiscount()+"，最多可购买"+goodsHasActivity.get(goodsActivityType_ms).getActivityInformation().getMaxNum()+"个");
                            }
                            else if(goodsActivityType_lj!=-1){
                                shopCartActivityType.setText("立减");
                                goodsActivityContent.setText("购买可立减¥"+goodsHasActivity.get(goodsActivityType_lj).getActivityInformation().getDiscount()+"，最多可购买"+goodsHasActivity.get(goodsActivityType_lj).getActivityInformation().getMaxNum()+"个");
                            }
                            else if(goodsActivityType_mj!=-1){
                                shopCartActivityType.setText("满减");
                                goodsActivityContent.setText("该商品满¥"+goodsHasActivity.get(goodsActivityType_mj).getActivityInformation().getPrice()+"，可减¥"+goodsHasActivity.get(goodsActivityType_mj).getActivityInformation().getDiscount());
                            }
                            shopCartListItemGoodsOldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                    }
                    //商品无效
                    if (item.getState()!=0){
                        shopCartListItemUndercarriage.setVisibility(View.VISIBLE);
                        shopCartListItemChangeNum.setVisibility(View.GONE);
                        shopCartListItemGoodsName.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
                        shopCartListItemGoodsSpecifiction.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
                        shopCartListItemGoodsMoneyUnit.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
                        shopCartListItemGoodsMoney.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
                        checkBox.setEnabled(false);
                        checkBox.setChecked(false);
                        checkBox.setButtonDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_checkbox_disable2));
                        shopCartActivityItem.setVisibility(View.GONE);
                    }
                    //商品有效
                    else{
                        shopCartListItemUndercarriage.setVisibility(View.GONE);
                        //库存不足
                        if (item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock() <= 0) {
                            //不是预售并且不允许0库存出库
                            if (item.getGoodsDetails().getZeroStock()==0&&item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2) {
                                shopCartListItemNoGoods.setVisibility(View.VISIBLE);
                                shopCartListItemChangeNum.setVisibility(View.GONE);
                                shopCartListItemGoodsName.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
                                shopCartListItemGoodsSpecifiction.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
                                shopCartListItemGoodsMoneyUnit.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
                                shopCartListItemGoodsMoney.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
                                checkBox.setEnabled(false);
                                checkBox.setChecked(false);
                                checkBox.setButtonDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_checkbox_disable2));
                                shopCartActivityItem.setVisibility(View.GONE);
                            }

                        }
                        //仅剩
                        if (item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock() > 0 && item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock() < 5) {
                            //不是预售并且不允许0库存出库
                            if (item.getGoodsDetails().getZeroStock()==0&&item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2) {
                                shopCartListItemLeavingsStock.setVisibility(View.VISIBLE);
                                shopCartListItemLeavingsStock.setText("仅剩" + item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock() + "件");
                            }

                        }
                    }



                    helper.setText(R.id.shop_cart_edit_goods_spec, item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getSpecifications());
                    helper.setText(R.id.goods_spec_strock, item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock() + "");
                    helper.setText(R.id.shop_cart_goods_buy_num, item.getGoodsNum() + "");
                    if (item.getGoodsNum()>1){
                        tvReduce.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
                    }
                    else{
                        tvReduce.setTextColor(helper.itemView.getResources().getColor(R.color.moreText));
                    }
                    if (item.getGoodsNum()>=item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock()){
                        if (item.getGoodsDetails().getZeroStock()==0&&item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2){
                            tvAdd.setTextColor(helper.itemView.getResources().getColor(R.color.moreText));
                        }
                        else{
                            tvAdd.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
                        }

                    }
                    else{
                        tvAdd.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
                    }
                    if (item.getGoodsNum()>=500) {
                        tvAdd.setTextColor(helper.itemView.getResources().getColor(R.color.moreText));
                    }

                    //点击了编辑按钮
                    if (isCheckEditBtn) {
                        shopCartActivityItem.setVisibility(View.GONE);
                        if(item.getState()!=0){
                            checkBox.setVisibility(View.GONE);
                            shopCartListItemDelete.setVisibility(View.VISIBLE);
                        }
                        if (item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock() <= 0) {
                            //不是预售并且不允许0库存出库
                            if (item.getGoodsDetails().getZeroStock()==0&&item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2) {
                                checkBox.setVisibility(View.GONE);
                                shopCartListItemDelete.setVisibility(View.VISIBLE);
                            }
                        }
                        if (item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock() <= 0) {
                            //不是预售并且不允许0库存出库
                            if (item.getGoodsDetails().getZeroStock()==0&&item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2) {
                                shopCartListItemGoodsSpecifiction.setVisibility(View.VISIBLE);
                                shopCartChooseGoodsSpecBtn.setVisibility(View.GONE);
                            }
                            else{
                                shopCartListItemGoodsSpecifiction.setVisibility(View.GONE);
                                shopCartChooseGoodsSpecBtn.setVisibility(View.VISIBLE);
                            }

                        } else {
                            shopCartListItemGoodsSpecifiction.setVisibility(View.GONE);
                            shopCartChooseGoodsSpecBtn.setVisibility(View.VISIBLE);
                        }

                        //若在编辑时重新选择了规格
                        if (helper.getLayoutPosition() == checkedChooseGoodsSpecPopIndex) {
                            shopCartEditGoodsSpec.setText(checkedGoodsSpec);
                        }
                    }
                    if (helper.getLayoutPosition() == checkedChooseGoodsSpecPopIndex) {
                        //我点击了减
                        if (checkAddOrReduce == 0) {
                            if (!"".equals(shopCartGoodsBuyNum.getText().toString().trim())) {
                                Integer shopCartGoodsNum = Integer.parseInt(shopCartGoodsBuyNum.getText().toString().trim());
                                if (shopCartGoodsNum != 1) {
                                    Toast.makeText(helper.itemView.getContext(), "不能再减少了!", Toast.LENGTH_SHORT).show();
                                } else {
                                    shopCartGoodsBuyNum.setText((shopCartGoodsNum - 1) + "");
                                }
                            } else {
                                Toast.makeText(helper.itemView.getContext(), "数据有误!", Toast.LENGTH_SHORT).show();
                            }
                        } else if (checkAddOrReduce == 1) {
                            if (!"".equals(shopCartGoodsBuyNum.getText().toString().trim())) {
                                Integer shopCartGoodsNum = Integer.parseInt(shopCartGoodsBuyNum.getText().toString().trim());
                                Integer goodsStock = Integer.parseInt(goodsSpecStrock.getText().toString().trim());

                                Log.d("aaaashopcart++","zero:"+item.getGoodsDetails().getZeroStock()+" state:"+item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState());
                                if (item.getGoodsDetails().getZeroStock()==0&&item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2) {
                                    if (shopCartGoodsNum >= goodsStock) {
                                        Toast.makeText(helper.itemView.getContext(), "已到库存上线!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        shopCartGoodsBuyNum.setText((shopCartGoodsNum + 1) + "");
                                    }
                                }
                                else{
                                    shopCartGoodsBuyNum.setText((shopCartGoodsNum + 1) + "");
                                }

                            } else {
                                Toast.makeText(helper.itemView.getContext(), "数据有误!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
            }
        }
        else{
            shopCartListItemUndercarriage.setVisibility(View.VISIBLE);
            shopCartListItemChangeNum.setVisibility(View.GONE);
            shopCartListItemGoodsName.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
            shopCartListItemGoodsSpecifiction.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
            shopCartListItemGoodsMoneyUnit.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
            shopCartListItemGoodsMoney.setTextColor(helper.itemView.getResources().getColor(R.color.font_grey));
            checkBox.setEnabled(false);
            checkBox.setChecked(false);
            checkBox.setButtonDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_checkbox_disable2));
            shopCartActivityItem.setVisibility(View.GONE);
            //点击了编辑按钮
            if (isCheckEditBtn) {
                shopCartActivityItem.setVisibility(View.GONE);
                if(item.getState()==1){
                    checkBox.setVisibility(View.GONE);
                    shopCartListItemDelete.setVisibility(View.VISIBLE);
                }
            }
        }

        helper.addOnClickListener(R.id.shop_cart_list_item_checkbox);
        helper.addOnClickListener(R.id.tv_reduce);
        helper.addOnClickListener(R.id.tv_add);
        helper.addOnClickListener(R.id.shop_cart_goods_buy_num);
        helper.addOnClickListener(R.id.shop_cart_list_item_delete);
        helper.addOnClickListener(R.id.shop_cart_choose_goods_spec_btn);
        helper.addOnClickListener(R.id.goods_activity_more_info);
    }


}
