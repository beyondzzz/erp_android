package com.jl.jlapp.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.GeneralInvoiceModel;
import com.jl.jlapp.eneity.InvoiceUnitAndVatInvoiceAptitude;
import com.jl.jlapp.mvp.activity.OrderWriteActivity;

import java.util.List;

/**
 * Created by fyf on 2018/1/15.
 */

public class GeneralInvoiceListAdapter extends BaseQuickAdapter<GeneralInvoiceModel.ResultDataBean, BaseViewHolder> {

    public GeneralInvoiceListAdapter(@LayoutRes int layoutResId, @Nullable List<GeneralInvoiceModel.ResultDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GeneralInvoiceModel.ResultDataBean item) {
        helper.setText(R.id.general_unit_id, item.getId()+"");
        helper.setText(R.id.text_unit_name, item.getUnitName());
        helper.setText(R.id.text_taxpayer_identification_number, item.getTaxpayerIdentificationNumber());

        helper.addOnClickListener(R.id.general_invoice_edit_linear);
        helper.addOnClickListener(R.id.general_invoice_del_linear);
    }
}
