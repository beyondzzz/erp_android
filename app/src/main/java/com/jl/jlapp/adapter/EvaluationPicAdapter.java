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
import com.jl.jlapp.eneity.EvaluationDetailByUserIdAndIdModel;
import com.jl.jlapp.nets.AppFinal;

import java.util.List;

/**
 * Created by 景雅倩 on 2018/1/14 0014.
 */

public class EvaluationPicAdapter extends BaseQuickAdapter<EvaluationDetailByUserIdAndIdModel.ResultDataBean.EvaluationPicsBean,BaseViewHolder> {

    public EvaluationPicAdapter(@LayoutRes int layoutResId, @Nullable List<EvaluationDetailByUserIdAndIdModel.ResultDataBean.EvaluationPicsBean> data) {
        super(layoutResId, data);

    }
    @Override
    protected void convert(BaseViewHolder helper, EvaluationDetailByUserIdAndIdModel.ResultDataBean.EvaluationPicsBean item) {
        String picUrl = item.getPicUrl();
        ImageView imageView = helper.getView(R.id.img);
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
        helper.addOnClickListener(R.id.img);

    }

}
