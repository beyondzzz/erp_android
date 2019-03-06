package com.jl.jlapp.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.GoodsListModel;
import com.jl.jlapp.nets.AppFinal;

import java.util.List;

/**
 * Created by 景雅倩 on 2018/1/14 0014.
 */

public class GoodsListAdapter3 extends RecyclerView.Adapter<GoodsListAdapter3.ViewHolder> implements View.OnClickListener {

    //    public GoodsListAdapter3(@LayoutRes int layoutResId, @Nullable List<Goods> data) {
//        super(layoutResId, data);
//    }
//    @Override
//    protected void convert(BaseViewHolder helper, Goods item) {
//        helper.setText(R.id.goods_name,item.getGoodsName());
//        helper.setText(R.id.goods_price,item.getPrice()+"");
//        helper.setImageResource(R.id.goods_img,item.getPicUrl());
//
//        helper.getView(R.id.goods_img).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText()
//            }
//        });
//
//    }


    private  List<GoodsListModel.ResultDataBean> data;
    private LayoutInflater inflater;
    private RecyclerView mRecyclerView;//用来计算Child位置
    private OnItemClickListener onItemClickListener;


    //对外提供接口初始化方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    //构造函数
    public GoodsListAdapter3(Context context, List<GoodsListModel.ResultDataBean> data) {
        this.data = data;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView goods_name;
        TextView goods_price;
        TextView oldPrice;
        ImageView goods_img;

        public ViewHolder(View itemView) {
            super(itemView);
            goods_name= itemView.findViewById(R.id.goods_name);
            goods_price=itemView.findViewById(R.id.goods_price);
            oldPrice = itemView.findViewById(R.id.old_goods_price);
            goods_img=itemView.findViewById(R.id.goods_img);

        }
    }

    /**
     * 创建VIewHolder，导入布局，实例化itemView
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

            itemView = inflater.inflate(R.layout.goods_list_item3, parent, false);


        if(itemView != null){
            //导入itemView，为itemView设置点击事件
            itemView.setOnClickListener(this);
        }
        return new ViewHolder(itemView);
    }

    /**
     * 绑定VIewHolder，加载数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsListModel.ResultDataBean item = data.get(position);
        holder.oldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        holder.goods_name.setText(item.getName());//加载数据
        holder.goods_price.setText(item.getGoodsSpecificationDetails().get(0).getPrice()+"");
        if(item.getGoodsSpecificationDetails().get(0).getOldPrice()>0){
            holder.oldPrice.setVisibility(View.VISIBLE);
            holder.oldPrice.setText("¥"+item.getGoodsSpecificationDetails().get(0).getOldPrice()+"");
        }
        else{
            holder.oldPrice.setVisibility(View.GONE);
        }

//        holder.goods_img.setImageResource(item.getGoodsSpecificationDetails().get(0).getGoodsDisplayPictures().get(0));
        ImageView imageView =holder.goods_img;
        //如果有图片
        if(item.getGoodsSpecificationDetails().get(0).getGoodsDisplayPictures().size()>0){
            String picUrl = item.getGoodsSpecificationDetails().get(0).getGoodsDisplayPictures().get(0).getPicUrl();

                Glide
                        .with(holder.itemView.getContext())
                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_m).error(R.drawable.img_noimg_m))
                        .load(AppFinal.BASEURL + picUrl)
                        .into(imageView);

        }
        //无图片
        else{

                Glide
                        .with(holder.itemView.getContext())
                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_m).error(R.drawable.img_noimg_m))
                        .load(R.drawable.img_noimg_m)
                        .into(imageView);

        }

    }

    /**
     * 数据源的数量，item的个数
     * @return
     */
    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    /**
     * 适配器绑定到RecyclerView 的时候，会将绑定适配器的RecyclerView 传递过来
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView=recyclerView;
    }

    /**
     *
     * @param v 点击的View
     */
    @Override
    public void onClick(View v) {
        //RecyclerView可以计算出这是第几个Child
        int childAdapterPosition = mRecyclerView.getChildAdapterPosition(v);
        if (onItemClickListener!=null) {
            onItemClickListener.onItemClick(childAdapterPosition,data.get(childAdapterPosition));
        }
    }


    /**
     * 接口回调
     * 1、定义接口，定义接口中的方法
     * 2、在数据产生的地方持有接口，并提供初始化方法，在数据产生的时候调用接口的方法
     * 3、在需要处理数据的地方实现接口，实现接口中的方法，并将接口传递到数据产生的地方
     */
    public interface OnItemClickListener{
        void onItemClick(int position,GoodsListModel.ResultDataBean data);
    }


}
