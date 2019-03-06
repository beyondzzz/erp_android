package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.eneity.ShoppingCartModel;
import com.jl.jlapp.nets.AppFinal;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/19 0019.
 */

public class WriteOrderGoodsMsgAdapter extends BaseQuickAdapter<ShoppingCartModel.ResultDataBean,BaseViewHolder>{

    int isFromGoodsDetail=0;//0表示从购物车页面跳转而来,1表示从商品详情页面跳转而来

    int buyNum=0;

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public void setIsFromGoodsDetail(int isFromGoodsDetail) {
        this.isFromGoodsDetail = isFromGoodsDetail;
    }

    public WriteOrderGoodsMsgAdapter(int layoutResId, @Nullable List<ShoppingCartModel.ResultDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCartModel.ResultDataBean item) {
        ImageView imageView = helper.getView(R.id.iv_foods);
        RelativeLayout shopCartListItemChangeNum=helper.getView(R.id.shop_cart_list_item_change_num);
        TextView tvReduce=helper.getView(R.id.tv_reduce);
        TextView tvAdd=helper.getView(R.id.tv_add);
        TextView fromGoodsMsgNum=helper.getView(R.id.from_goods_msg_num);
        shopCartListItemChangeNum.setVisibility(View.VISIBLE);
        fromGoodsMsgNum.setVisibility(View.GONE);
        if(isFromGoodsDetail==0){
            shopCartListItemChangeNum.setVisibility(View.GONE);
            fromGoodsMsgNum.setVisibility(View.VISIBLE);
            fromGoodsMsgNum.setText("x "+item.getGoodsNum());
        }
        Glide
                .with(helper.itemView.getContext())
                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                .load(AppFinal.BASEURL + item.getGoodsPicUrl())
                .into(imageView);
        helper.setText(R.id.tv_foods_name, item.getGoodsName());
        helper.setText(R.id.tv_food_number, item.getGoodsSpecificationDetailsName());
        helper.setText(R.id.tv_foods_prices, item.getGoodsUnitlPrice() + "");
        helper.setText(R.id.shop_cart_goods_buy_num, item.getGoodsNum()+"");

        if (item.getGoodsDetails() != null && item.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
            for (int i = 0; i < item.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                if (item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId() == item.getGoodsSpecificationDetailsId()) {
                    if (item.getGoodsNum()>1){
                        tvReduce.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
                    }
                    else{
                        tvReduce.setTextColor(helper.itemView.getResources().getColor(R.color.moreText));
                    }
                    if(item.getGoodsDetails().getZeroStock()==0&&item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2){
                        if (item.getGoodsNum()>=item.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock()){
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

                }
            }
        }


        helper.addOnClickListener(R.id.tv_add);
        helper.addOnClickListener(R.id.tv_reduce);
        helper.addOnClickListener(R.id.shop_cart_goods_buy_num);
    }
}
