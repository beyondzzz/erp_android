package com.jl.jlapp.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.CompanyListModel;
import com.jl.jlapp.eneity.InvoiceUnitAndVatInvoiceAptitude;
import com.jl.jlapp.mvp.activity.OrderWriteActivity;


import java.util.List;

/**
 * Created by fyf on 2018/1/15.
 */

public class CompanyListAdapter extends BaseQuickAdapter<InvoiceUnitAndVatInvoiceAptitude.ResultDataBean.InvoiceUnitBean, BaseViewHolder> {

    public CompanyListAdapter(@LayoutRes int layoutResId, @Nullable List<InvoiceUnitAndVatInvoiceAptitude.ResultDataBean.InvoiceUnitBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceUnitAndVatInvoiceAptitude.ResultDataBean.InvoiceUnitBean item) {
        helper.setText(R.id.rb_company_item, item.getUnitName());
        helper.setText(R.id.tv_nummber, item.getTaxpayerIdentificationNumber());
        RadioButton rbCompanyItem = helper.getView(R.id.rb_company_item);

        if (OrderWriteActivity.index == helper.getAdapterPosition()) {
            rbCompanyItem.setChecked(true);
        } else {
            rbCompanyItem.setChecked(false);
        }

        helper.addOnClickListener(R.id.rb_company_item);
    }
}
