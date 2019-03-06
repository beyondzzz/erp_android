package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ShipAddressModel;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/22 0022.
 */

public class ChooseSendToAddressAdapter extends BaseQuickAdapter<ShipAddressModel.ResultDataBean,BaseViewHolder> {

    private String chooseAddress="";

    public void setChooseAddress(String chooseAddress) {
        this.chooseAddress = chooseAddress;
    }

    public ChooseSendToAddressAdapter(int layoutResId, @Nullable List<ShipAddressModel.ResultDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShipAddressModel.ResultDataBean item) {
        helper.setText(R.id.choose_address_location,item.getRegion()+item.getDetailedAddress());

        TextView chooseAddressLocation=helper.getView(R.id.choose_address_location);
        ImageView chooseAddressLocationImg=helper.getView(R.id.choose_address_location_img);
        ImageView chooseAddressLocationCheckmarkImg=helper.getView(R.id.choose_address_location_checkmark_img);

        chooseAddressLocation.setTextColor(helper.itemView.getResources().getColor(R.color.trans_333333));
        chooseAddressLocationImg.setImageDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_location_gray_m));
        chooseAddressLocationCheckmarkImg.setVisibility(View.GONE);

        if ((item.getRegion()+item.getDetailedAddress()).equals(chooseAddress)){
            chooseAddressLocation.setTextColor(helper.itemView.getResources().getColor(R.color.checkGreenColor));
            chooseAddressLocationImg.setImageDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_location_green_m));
            chooseAddressLocationCheckmarkImg.setVisibility(View.VISIBLE);
        }


    }
}
