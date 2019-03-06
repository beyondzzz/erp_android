package com.jl.jlapp.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ClassificationModel;
import com.jl.jlapp.eneity.ClissifyModel;
import com.jl.jlapp.mvp.activity.MainActivity;
import com.jl.jlapp.mvp.fragment.ClassifityFragment;


import java.util.List;

/**
 * Created by fyf on 2018/1/13.
 */

public class CliassifyAdapter extends BaseQuickAdapter<ClassificationModel.ResultDataBean, BaseViewHolder> {


    public CliassifyAdapter(@LayoutRes int layoutResId, @Nullable List<ClassificationModel.ResultDataBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ClassificationModel.ResultDataBean item) {
        helper.setText(R.id.tv_clissify_type, item.getName());
        TextView tvType = helper.getView(R.id.tv_clissify_type);
        if (ClassifityFragment.index == helper.getAdapterPosition()) {

            //tvType.setTextColor(R.color.clickspan_color);
            tvType.setTextColor(helper.itemView.getContext().getResources().getColor(R.color.checkGreenColor));
            tvType.setBackgroundResource(R.color.ashenBackgroundColor);

        } else {
            tvType.setTextColor(helper.itemView.getContext().getResources().getColor(R.color.trans_333333));
            tvType.setBackgroundResource(R.color.white);

        }
    }
}

