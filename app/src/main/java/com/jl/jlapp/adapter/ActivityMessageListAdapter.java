package com.jl.jlapp.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ActivityMessageByUserIdModel;
import com.jl.jlapp.eneity.OrderListModel;
import com.jl.jlapp.nets.AppFinal;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class ActivityMessageListAdapter extends BaseQuickAdapter<ActivityMessageByUserIdModel.ResultDataBean.ActivityMessageListBean,BaseViewHolder> {

    public ActivityMessageListAdapter(@LayoutRes int layoutResId, @Nullable List<ActivityMessageByUserIdModel.ResultDataBean.ActivityMessageListBean> data) {

        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ActivityMessageByUserIdModel.ResultDataBean.ActivityMessageListBean item) {

        String picUrl = item.getActivityInformation().getMessagePicUrl();
        ImageView imageView = helper.getView(R.id.activity_img);

        helper.setText(R.id.activity_name,item.getActivityInformation().getName());
        helper.setText(R.id.activity_introduce,item.getActivityInformation().getIntroduction());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String dateStr = sdf.format(item.getGetTime());
        helper.setText(R.id.tv_date,dateStr);

        //如果无图片
        if(picUrl==null || picUrl == ""){

            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_banner_middle).error(R.drawable.img_noimg_banner_middle))
                    .load(R.drawable.img_noimg_banner_middle)
                    .into(imageView);
        }
        //有图片
        else{
            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_lose_banner_middle).error(R.drawable.img_lose_banner_middle))
                    .load(AppFinal.BASEURL + picUrl)
                    .into(imageView);
        }
    }

}
