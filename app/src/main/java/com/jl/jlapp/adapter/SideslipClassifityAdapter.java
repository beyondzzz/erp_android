package com.jl.jlapp.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ClassificationModel;

import java.util.List;
import java.util.Map;

/**
 * Created by fyf on 2018/1/13.
 */

public class SideslipClassifityAdapter extends BaseQuickAdapter<ClassificationModel.ResultDataBean.TwoClassificationsBean, BaseViewHolder> {

    Map<String,Object> checkedClassifity;//选中的分类

    public void setCheckedClassifity(Map<String, Object> checkedClassifity) {
        this.checkedClassifity = checkedClassifity;
    }

    public SideslipClassifityAdapter(@LayoutRes int layoutResId, @Nullable List<ClassificationModel.ResultDataBean.TwoClassificationsBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ClassificationModel.ResultDataBean.TwoClassificationsBean item) {

        helper.setText(R.id.brand_name, item.getOneClassificationsName()+"/"+item.getName());
        helper.setText(R.id.classifity_id,item.getId()+"");
        Log.d("aaaaaaaaaaa","分类Id:"+item.getId());

        TextView name = helper.getView(R.id.brand_name);
        RelativeLayout brandBackground=helper.getView(R.id.check_brand_relativelayout);
        ImageView brandIsCheckImg=helper.getView(R.id.brand_checkmark);

        brandBackground.setBackgroundResource(R.drawable.goods_check_button_xs);
        brandIsCheckImg.setVisibility(View.GONE);
        name.setTextColor(Color.parseColor("#333333"));

        if(checkedClassifity!=null){
            if((item.getOneClassificationsName()+"/"+item.getName()).equals(checkedClassifity.get("classifityName"))&&item.getId()==(int)checkedClassifity.get("classifityId")){
                brandBackground.setBackgroundResource(R.drawable.goods_check_button_xs_checked);
                brandIsCheckImg.setVisibility(View.VISIBLE);
                name.setTextColor(Color.parseColor("#60b746"));
            }
        }


    }
}

