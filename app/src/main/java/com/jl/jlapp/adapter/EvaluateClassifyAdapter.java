package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.EvaluateTypeModel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 评价分类
 * Created by fyf on 2018/1/20.
 */

public final class EvaluateClassifyAdapter extends BaseQuickAdapter<EvaluateTypeModel, BaseViewHolder> {

    int clickItemNum=0;

    public void setClickItemNum(int clickItemNum) {
        this.clickItemNum = clickItemNum;
    }

    public EvaluateClassifyAdapter(int layoutResId, @Nullable List<EvaluateTypeModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateTypeModel item) {
        TextView tvEvaluateType=helper.getView(R.id.tv_evaluate_type);
        TextView tvEvaluateNum=helper.getView(R.id.tv_evaluate_num);

        helper.setText(R.id.tv_evaluate_type, item.getType());
        helper.setText(R.id.tv_evaluate_num, item.getEvaluate_num());
        if (helper.getLayoutPosition()==clickItemNum){
            tvEvaluateType.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
            tvEvaluateNum.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        }
        else{
            tvEvaluateType.setTextColor(helper.itemView.getResources().getColor(R.color.moreText));
            tvEvaluateNum.setTextColor(helper.itemView.getResources().getColor(R.color.moreText));
        }

    }
}
