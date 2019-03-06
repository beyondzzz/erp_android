package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.Evaluation;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.eneity.Order;
import com.jl.jlapp.nets.AppFinal;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/20 0020.
 */

public class GoodsEvaluationImageAdapter extends BaseQuickAdapter<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean.EvaluationPicsBean,BaseViewHolder> {

    public GoodsEvaluationImageAdapter(int layoutResId, @Nullable List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean.EvaluationPicsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean.EvaluationPicsBean item) {
        //helper.setImageDrawable(R.id.goods_evaluation_img,helper.itemView.getResources().getDrawable(item));
        ImageView imageView=helper.getView(R.id.goods_evaluation_img);
        String picUrl = item.getPicUrl();
        Glide
                .with(helper.itemView.getContext())
                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                .load(AppFinal.BASEURL + picUrl)
                .into(imageView);
        helper.addOnClickListener(R.id.goods_evaluation_img);
    }
}
