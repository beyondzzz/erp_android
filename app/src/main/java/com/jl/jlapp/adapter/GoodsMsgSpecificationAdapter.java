package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.GoodsDetailModel;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/18 0018.
 */

public class GoodsMsgSpecificationAdapter extends BaseQuickAdapter<GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean,BaseViewHolder> {

    public String checkedGoodsSpc="";

    public void setCheckedGoodsSpc(String checkedGoodsSpc) {
        this.checkedGoodsSpc = checkedGoodsSpc;
    }

    public GoodsMsgSpecificationAdapter(int layoutResId, @Nullable List<GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean item) {
        TextView text=helper.getView(R.id.goods_spec_detail);
        text.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        text.setBackgroundResource(R.drawable.bg_text_grey_border);
        helper.setText(R.id.goods_spec_detail,item.getSpecifications());

        if(item.getSpecifications().equals(checkedGoodsSpc)){
            text.setTextColor(helper.itemView.getResources().getColor(R.color.checkGreenColor));
            text.setBackgroundResource(R.drawable.bg_text_green_border);
        }


    }
}
