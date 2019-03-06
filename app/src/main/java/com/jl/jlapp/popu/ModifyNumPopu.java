package com.jl.jlapp.popu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;


/**
 * Created by 柳亚婷 on 2018/1/19.
 */

public class ModifyNumPopu extends PopupWindow {

    private TextView tvKnow;
    private View view;
    CardView cardView;
    TextView cancelBtn, okBtn, tvReduce, tvAdd;
    EditText buyNum;
    OnClickOkBtn onClickOkBtn;

    public void setOnClickOkBtn(OnClickOkBtn onClickOkBtn) {
        this.onClickOkBtn = onClickOkBtn;
    }

    public ModifyNumPopu(Context context, Integer stockNum, String oldBuyNum,Integer zeroStock,Integer gxcState) {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.modify_num_popu, null);
        cardView = view.findViewById(R.id.layout);
        cancelBtn = view.findViewById(R.id.modify_num_bottom_cancel_btn);
        cancelBtn.setVisibility(View.VISIBLE);
        okBtn = view.findViewById(R.id.modify_num_bottom_ok_btn);
        buyNum = view.findViewById(R.id.tv_buy_num);
        tvReduce = view.findViewById(R.id.tv_reduce);
        tvAdd = view.findViewById(R.id.tv_add);

        buyNum.setText(oldBuyNum);

        if (Integer.parseInt(oldBuyNum)<=1){
            tvReduce.setTextColor(context.getResources().getColor(R.color.moreText));
        }

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(buyNum.getText().toString().trim())) {
                    if (onClickOkBtn != null) {
                        onClickOkBtn.OnClickOkBtn(buyNum.getText().toString().trim());
                    }
                } else {
                    Toast.makeText(context, "请输入购买数量", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aaamodifity", "我执行了减");
                if (!"".equals(buyNum.getText().toString().trim())) {
                    int num = Integer.parseInt(buyNum.getText().toString().trim());
                    if (num <= 1) {
                        Toast.makeText(context, "不能再减少了!", Toast.LENGTH_SHORT).show();
                        tvReduce.setTextColor(context.getResources().getColor(R.color.moreText));
                    } else {
                        buyNum.setText((num - 1) + "");
                        if (num - 1==1){
                            tvReduce.setTextColor(context.getResources().getColor(R.color.moreText));
                        }
                        if (num-1<500&&num-1<stockNum){
                            tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                        }
                    }
                } else {
                    Toast.makeText(context, "请输入购买数量", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aaamodifity", "我执行了加");
                if (!"".equals(buyNum.getText().toString().trim())) {
                    int num = Integer.parseInt(buyNum.getText().toString().trim());
                    //不是预售并且不允许0库存出库
                    if (zeroStock==0&&gxcState==2){
                        //填写的数量没有超过库存
                        if (num < stockNum) {
                            tvReduce.setTextColor(context.getResources().getColor(R.color.trans_333333));
                            if (num < 500) {
                                buyNum.setText((num + 1) + "");
                                if (num + 1 == 500) {
                                    tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                                }
                                else{
                                    tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                                }
                            } else {
                                buyNum.setText(500 + "");
                                tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                            }
                        } else {
                            Toast.makeText(context, "库存上限为" + stockNum, Toast.LENGTH_SHORT).show();
                            buyNum.setText(stockNum + "");
                            tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                        }
                    }
                    else{
                        tvReduce.setTextColor(context.getResources().getColor(R.color.trans_333333));
                        if (num < 500) {
                            buyNum.setText((num + 1) + "");
                            if (num + 1 == 500) {
                                tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                            }
                            else{
                                tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                            }
                        } else {
                            buyNum.setText(500 + "");
                            tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                        }
                    }

                } else {
                    Toast.makeText(context, "请输入购买数量", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buyNum.setInputType(EditorInfo.TYPE_CLASS_PHONE);//设置数字键盘
        buyNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        buyNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!"".equals(buyNum.getText().toString().trim())) {
                    int num = Integer.parseInt(buyNum.getText().toString().trim());
                    if (num <= 0) {
                        buyNum.setText("1");
                        num = 1;
                        tvReduce.setTextColor(context.getResources().getColor(R.color.moreText));
                    }
                    if (num>1){
                        tvReduce.setTextColor(context.getResources().getColor(R.color.trans_333333));
                    }
                    //不是预售并且不允许0库存出库
                    if (zeroStock==0&&gxcState==2){
                        //输入的数量没有超过库存
                        if (num <= stockNum) {
                            if (num > 500) {
                                buyNum.setText(500 + "");
                                tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                            }
                            else{
                                tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                            }

                        } else {
                            Toast.makeText(context, "库存上限为" + stockNum, Toast.LENGTH_SHORT).show();
                            buyNum.setText(stockNum + "");
                            tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                        }
                    }
                    else{
                        if (num > 500) {
                            buyNum.setText(500 + "");
                            tvAdd.setTextColor(context.getResources().getColor(R.color.moreText));
                        }
                        else{
                            tvAdd.setTextColor(context.getResources().getColor(R.color.trans_333333));
                        }
                    }

                }
            }
        });

        //tvKnow = view.findViewById(R.id.tv_know);

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable cd = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(cd);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {

                //获取控件距离手机顶部的高度和距离左边的距离
                int top = cardView.getTop();
                int left = cardView.getLeft();
                //获取控件的高度和宽度
                int width = cardView.getWidth();
                int height = cardView.getHeight();
                //获取手指触摸屏幕的坐标
                int touchY = (int) event.getY();
                int touchX = (int) event.getX();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (touchY < top || touchX < left || touchY > (top + height) || touchX > (left + width)) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    public interface OnClickOkBtn {
        void OnClickOkBtn(String num);
    }


}
