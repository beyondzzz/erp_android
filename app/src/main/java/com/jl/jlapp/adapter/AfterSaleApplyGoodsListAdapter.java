package com.jl.jlapp.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.CustomerServiceByUserIdAndOrderIdModel;
import com.jl.jlapp.eneity.OrderDetail;
import com.jl.jlapp.nets.AppFinal;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class AfterSaleApplyGoodsListAdapter extends BaseQuickAdapter<OrderDetail.ResultDataBean.OrderDetailsBean,BaseViewHolder> {

    public AfterSaleApplyGoodsListAdapter(@LayoutRes int layoutResId, @Nullable List<OrderDetail.ResultDataBean.OrderDetailsBean> data) {

        super(layoutResId, data);

    }
    @Override
    protected void convert(BaseViewHolder helper, OrderDetail.ResultDataBean.OrderDetailsBean item) {

        String picUrl = item.getGoodsCoverUrl();
        ImageView imageView = helper.getView(R.id.goods_img);

        helper.setText(R.id.goods_name,item.getGoodsName());
        helper.setText(R.id.goods_price,item.getGoodsOriginalPrice()+"");
        helper.setText(R.id.my_order_goods_num,item.getGoodsQuantity()+"");
        helper.setText(R.id.goods_total_price,(item.getGoodsOriginalPrice()*item.getGoodsQuantity())+"");
        //如果无图片
        if(picUrl==null || picUrl == ""){

            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_noimg_xs))
                    .load(R.drawable.img_noimg_xs)
                    .into(imageView);
        }
        //有图片
        else{
            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_lose_xss).error(R.drawable.img_lose_xss))
                    .load(AppFinal.BASEURL + picUrl)
                    .into(imageView);
        }
    }

}
