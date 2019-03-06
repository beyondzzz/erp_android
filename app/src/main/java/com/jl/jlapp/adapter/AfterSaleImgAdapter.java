package com.jl.jlapp.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.AfterSalePic;
import com.jl.jlapp.eneity.CustomerServiceByUserIdAndOrderIdModel;
import com.jl.jlapp.eneity.Goods;
import com.jl.jlapp.nets.AppFinal;

import java.util.List;

/**
 * Created by 景雅倩 on 2018/1/14 0014.
 */

public class AfterSaleImgAdapter extends BaseQuickAdapter<CustomerServiceByUserIdAndOrderIdModel.ResultDataBean.AfterSalePicsBean,BaseViewHolder> {

    public AfterSaleImgAdapter(@LayoutRes int layoutResId, @Nullable List<CustomerServiceByUserIdAndOrderIdModel.ResultDataBean.AfterSalePicsBean> data) {
        super(layoutResId, data);

    }
    @Override
    protected void convert(BaseViewHolder helper, CustomerServiceByUserIdAndOrderIdModel.ResultDataBean.AfterSalePicsBean item) {
        String picUrl = item.getPicUrl();
        ImageView imageView = helper.getView(R.id.img);
        //如果无图片
        if(picUrl==null || picUrl == ""){

            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                    .load(R.drawable.img_noimg_xs)
                    .into(imageView);
        }
        //有图片
        else{
            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_lose_xss).error(R.drawable.img_lose_xs))
                    .load(AppFinal.BASEURL + picUrl)
                    .into(imageView);
            helper.addOnClickListener(R.id.img);
        }


    }

//    @Override
//    protected View getItemView(int layoutResId, ViewGroup parent) {
//        PhotoView p = new PhotoView();
//        p.setLayoutParams(new AbsListView.LayoutParams((int) (getResources().getDisplayMetrics().density * 100), (int) (getResources().getDisplayMetrics().density * 100)));
//        p.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        p.setImageResource(imgs[position]);
//        // 把PhotoView当普通的控件把触摸功能关掉
//        p.disenable();
//        return p;
//    }
}
