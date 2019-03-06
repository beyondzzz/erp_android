package com.jl.jlapp.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.CouponMsgModel;
import com.jl.jlapp.eneity.GoodsListModel;
import com.jl.jlapp.nets.AppFinal;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class CouponGoodsListAdapter extends BaseQuickAdapter<CouponMsgModel.ResultDataBean.GoodsBean, BaseViewHolder> {

    public CouponGoodsListAdapter(@LayoutRes int layoutResId, @Nullable List<CouponMsgModel.ResultDataBean.GoodsBean> data) {
        super(layoutResId, data);
        Log.d("aaaaaa", "size:" + data.size());

    }

    @Override
    protected void convert(BaseViewHolder helper, CouponMsgModel.ResultDataBean.GoodsBean item) {
        helper.setText(R.id.goods_name, item.getName() + " " + item.getGoodsSpecificationDetails().get(0).getSpecifications());
        helper.setText(R.id.goods_price, item.getGoodsSpecificationDetails().get(0).getPrice() + "");
        helper.setText(R.id.is_sale_num, item.getSaleCount() + "");
        helper.setText(R.id.goods_id,item.getId()+"");

        // helper.setImageResource(R.id.goods_img,item.getPicUrl());
        ImageView imageView = helper.getView(R.id.goods_img);
        //如果有图片
        if(item.getGoodsSpecificationDetails().get(0).getGoodsDisplayPictures().size()>0){
            String picUrl = item.getGoodsSpecificationDetails().get(0).getGoodsDisplayPictures().get(0).getPicUrl();
            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                    .load(AppFinal.BASEURL + picUrl)
                    .into(imageView);
        }
        //无图片
        else{
            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                    .load(R.drawable.img_noimg_xs)
                    .into(imageView);
        }
        helper.addOnClickListener(R.id.goods_list_add_shop_cart);

    }

}
