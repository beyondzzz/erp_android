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
import com.jl.jlapp.eneity.ClissifyModel;
import com.jl.jlapp.mvp.activity.SearchGoodsListActivity;
import com.jl.jlapp.mvp.fragment.ClassifityFragment;

import java.util.List;
import java.util.Map;

/**
 * Created by fyf on 2018/1/13.
 */

public class SideslipBrandAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    List<String> chechedName;//品牌

    public void setChechedName(List<String> chechedName) {
        this.chechedName = chechedName;
    }

    public SideslipBrandAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.brand_name, item);
        TextView name = helper.getView(R.id.brand_name);
        RelativeLayout brandBackground=helper.getView(R.id.check_brand_relativelayout);
        ImageView brandIsCheckImg=helper.getView(R.id.brand_checkmark);

        brandBackground.setBackgroundResource(R.drawable.goods_check_button_xs);
        brandIsCheckImg.setVisibility(View.GONE);
        name.setTextColor(Color.parseColor("#333333"));

        if(chechedName!=null){
            Log.e(TAG,"chechedName:"+chechedName.size());
            for(String chechedName:chechedName){
                if(item==chechedName){
                    brandBackground.setBackgroundResource(R.drawable.goods_check_button_xs_checked);
                    brandIsCheckImg.setVisibility(View.VISIBLE);
                    name.setTextColor(Color.parseColor("#60b746"));
                }

            }
        }


    }
}

