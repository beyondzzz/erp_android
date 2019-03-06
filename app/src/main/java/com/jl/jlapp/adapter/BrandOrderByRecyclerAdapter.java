package com.jl.jlapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jl.jlapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by 柳亚婷 on 2018/1/12 0012.
 */

public class BrandOrderByRecyclerAdapter extends RecyclerView.Adapter<BrandOrderByRecyclerAdapter.ViewHolder> implements View.OnClickListener {
    //private static final String TAG = BrandOrderByRecyclerAdapter.class.getSimpleName();
    private List<String> data;
    private LayoutInflater inflater;
    private RecyclerView mRecyclerView;//用来计算Child位置
    private OnItemClickListener onItemClickListener;
    private RelativeLayout brandBackground;
    private ImageView brandIsCheckImg;
    private TextView brandNameText;
    List<Map<String,Object>> checkedBrandMsgList;//之前选中的品牌


    public void setCheckedBrandMsgList(List<Map<String, Object>> checkedBrandMsgList) {
        this.checkedBrandMsgList = checkedBrandMsgList;
    }

    //对外提供接口初始化方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    //构造函数
    public BrandOrderByRecyclerAdapter(Context context, List<String> data) {
        this.data = data;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        RelativeLayout brandBackground;
        ImageView brandIsChecked;

        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.brand_name);
            brandBackground=itemView.findViewById(R.id.check_brand_relativelayout);
            brandIsChecked=itemView.findViewById(R.id.brand_checkmark);

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
        View itemView = inflater.inflate(R.layout.shop_list_brand_search_item, parent, false);
        //导入itemView，为itemView设置点击事件
        itemView.setOnClickListener(this);
        return new ViewHolder(itemView);
    }

    /**
     * 绑定VIewHolder，加载数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(data.get(position));//加载数据
       //防止组件复用造成的显示错乱
        holder.brandBackground.setBackgroundResource(R.drawable.goods_check_button_xs);
        holder.brandIsChecked.setVisibility(View.GONE);
        holder.name.setTextColor(Color.parseColor("#333333"));
        //若之前有选中的品牌，此时在数据加载的时候就要默认选中
        if(checkedBrandMsgList!=null){
           // Log.e(TAG, "length: "+checkedBrandMsgList.size() );
            for (int i=0;i<checkedBrandMsgList.size();i++){
                if(data.get(position)==(String)checkedBrandMsgList.get(i).get("brandName")){
                    holder.brandBackground.setBackgroundResource(R.drawable.goods_check_button_xs_checked);
                    holder.brandIsChecked.setVisibility(View.VISIBLE);
                    holder.name.setTextColor(Color.parseColor("#60b746"));
                }
            }
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
        //往页面中传入是选中还是取消选中 (0：取消选中,1：选中)
        int isCheckOrRemoveChecked=-1;
        brandBackground=v.findViewById(R.id.check_brand_relativelayout);
        brandIsCheckImg=v.findViewById(R.id.brand_checkmark);
        brandNameText=v.findViewById(R.id.brand_name);
        //判断是否被选中
        //0    --------   VISIBLE    可见
        //4    --------   INVISIBLE    不可见但是占用布局空间
        //8    --------   GONE    不可见也不占用布局空间
        //对勾是显示状态，此时该品牌是选中状态，要取消选中
        if(brandIsCheckImg.getVisibility()==View.VISIBLE){
            brandBackground.setBackgroundResource(R.drawable.goods_check_button_xs);
            brandIsCheckImg.setVisibility(View.GONE);
            brandNameText.setTextColor(v.getResources().getColor(R.color.basic_wheelview_color));
            isCheckOrRemoveChecked=0;
        }
        else{
            brandBackground.setBackgroundResource(R.drawable.goods_check_button_xs_checked);
            brandIsCheckImg.setVisibility(View.VISIBLE);
            brandNameText.setTextColor(v.getResources().getColor(R.color.checkGreenColor));
            isCheckOrRemoveChecked=1;
        }

        //Log.e(TAG, "onClick: "+childAdapterPosition +" id:"+v.findViewById(R.id.check_brand_relativelayout));
        if (onItemClickListener!=null) {
            onItemClickListener.onItemClick(childAdapterPosition,isCheckOrRemoveChecked,data.get(childAdapterPosition));
        }
    }


    /**
     * 接口回调
     * 1、定义接口，定义接口中的方法
     * 2、在数据产生的地方持有接口，并提供初始化方法，在数据产生的时候调用接口的方法
     * 3、在需要处理数据的地方实现接口，实现接口中的方法，并将接口传递到数据产生的地方
     */
    public interface OnItemClickListener{
        void onItemClick(int position,int isChecked,String data);
    }


}
