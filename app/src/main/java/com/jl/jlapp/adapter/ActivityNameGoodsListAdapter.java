package com.jl.jlapp.adapter;


import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ActivityInformationByIdModel;
import com.jl.jlapp.nets.AppFinal;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class ActivityNameGoodsListAdapter extends BaseQuickAdapter<ActivityInformationByIdModel.ResultDataBean.GoodsBean,BaseViewHolder> {

    public ActivityNameGoodsListAdapter(@LayoutRes int layoutResId, @Nullable List<ActivityInformationByIdModel.ResultDataBean.GoodsBean> data) {

        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ActivityInformationByIdModel.ResultDataBean.GoodsBean item) {
        String picUrl = item.getGoodsSpecificationDetails().get(0).getGoodsDisplayPictures().get(0).getPicUrl();
        ImageView imageView = helper.getView(R.id.goods_img);
        TextView oldPrice = helper.getView(R.id.old_goods_price);
        oldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        helper.setText(R.id.goods_name,item.getName());
        helper.setText(R.id.goods_price,item.getGoodsSpecificationDetails().get(0).getPrice()+"");
        if(item.getGoodsSpecificationDetails().get(0).getOldPrice()>0){
            oldPrice.setVisibility(View.VISIBLE);
            helper.setText(R.id.old_goods_price,"¥"+item.getGoodsSpecificationDetails().get(0).getOldPrice());
        }
        else{
            oldPrice.setVisibility(View.GONE);
        }



        //如果无图片
        if(picUrl==null || picUrl == ""){

            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xss).error(R.drawable.img_noimg_xss))
                    .load(R.drawable.img_noimg_xss)
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
