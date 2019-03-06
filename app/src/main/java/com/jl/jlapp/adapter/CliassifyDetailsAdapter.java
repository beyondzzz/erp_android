package com.jl.jlapp.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ClassificationModel;
import com.jl.jlapp.eneity.CliassifyDetailsModel;
import com.jl.jlapp.mvp.activity.MainActivity;
import com.jl.jlapp.nets.AppFinal;


import java.util.List;

/**
 * Created by fyf on 2018/1/13.
 */

public class CliassifyDetailsAdapter extends BaseQuickAdapter<ClassificationModel.ResultDataBean.TwoClassificationsBean, BaseViewHolder> {

    public static final String TAG = "CliassifyDetailsAdapter";

    public CliassifyDetailsAdapter(@LayoutRes int layoutResId, @Nullable List<ClassificationModel.ResultDataBean.TwoClassificationsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassificationModel.ResultDataBean.TwoClassificationsBean item) {
        helper.setText(R.id.tv_foods_name, item.getName());
        ImageView imageView = helper.getView(R.id.iv_classify);
        Glide
                .with(helper.itemView.getContext())
                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_noimg_xs))
                .load(AppFinal.BASEURL + item.getPicUrl())
                .into(imageView);

    }
}
