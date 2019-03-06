package com.jl.jlapp.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.AddressListModel;
import com.jl.jlapp.eneity.ShipAddressModel;

import java.util.List;

/**
 * Created by fyf on 2018/1/15.
 */

public class AddressListAdapter extends BaseQuickAdapter<ShipAddressModel.ResultDataBean, BaseViewHolder> {

    public AddressListAdapter(@LayoutRes int layoutResId, @Nullable List<ShipAddressModel.ResultDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShipAddressModel.ResultDataBean item) {
        TextView tvDefault=helper.getView(R.id.tv_default);
        tvDefault.setVisibility(View.GONE);

        helper.setText(R.id.tv_person_name, item.getConsigneeName());
        helper.setText(R.id.tv_person_phone, item.getConsigneeTel());
        helper.setText(R.id.tv_address_details, item.getRegion()+item.getDetailedAddress());
        if(item.getIsCommonlyUsed()==1){
            tvDefault.setVisibility(View.VISIBLE);
        }



    }
}
