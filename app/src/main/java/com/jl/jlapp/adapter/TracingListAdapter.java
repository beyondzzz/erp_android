package com.jl.jlapp.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.OrderStateDetail;
import com.jl.jlapp.eneity.OrderStateDetailModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 景雅倩 on 2018/1/14 0014.
 */

public class TracingListAdapter extends BaseQuickAdapter<OrderStateDetailModel.ResultDataBean,BaseViewHolder> {
    int length;
    public TracingListAdapter(@LayoutRes int layoutResId, @Nullable List<OrderStateDetailModel.ResultDataBean> data) {
        super(layoutResId, data);
        length = data.size();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStateDetailModel.ResultDataBean item) {
        TextView startLine = helper.getView(R.id.start_line);
        ImageView icon = helper.getView(R.id.icon);
        TextView endLine = helper.getView(R.id.end_line);
        TextView state = helper.getView(R.id.state);
        helper.setText(R.id.state,item.getOrderStateDetail());
        long time = item.getAddTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatTime = sdf.format(time);
        helper.setText(R.id.time,formatTime);
        if(helper.getLayoutPosition()==0){
            icon.setImageDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_point_green));
            startLine.setVisibility(View.INVISIBLE);
            state.setTextColor(helper.itemView.getResources().getColor(R.color.checkGreenColor));
        }else if(helper.getLayoutPosition()==length-1){
            endLine.setVisibility(View.INVISIBLE);
        }
    }



}
