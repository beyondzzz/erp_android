package com.jl.jlapp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jl.jlapp.R;


public class GridImageLayout extends ViewGroup {

    private int verticalSpace = 0;
    private int horizontalSpace = 0;
    private int columnNum = 5;
    private int childWidth = 0;
    private int childHeight = 0;

    private int maxNum = 5;
    private boolean enableLimit = false;

    private String tagImgAdd;

    public GridImageLayout(Context context) {
        this(context, null);
    }

    public GridImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GridImageLayout);
        if (ta != null) {
            verticalSpace = (int) ta.getDimension(R.styleable.GridImageLayout_verticalSpace, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
            horizontalSpace = (int) ta.getDimension(R.styleable.GridImageLayout_horizontalSpace, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
            columnNum = ta.getInt(R.styleable.GridImageLayout_columnNum, 5);
            maxNum = ta.getInt(R.styleable.GridImageLayout_maxNum, 5);
            enableLimit = ta.getBoolean(R.styleable.GridImageLayout_enableLimit, false);
            ta.recycle();
        }

        tagImgAdd = context.getString(R.string.img_add);

        setClickable(true);
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getPaddingLeft() + getPaddingRight(), widthMeasureSpec),
                getDefaultSize(getPaddingTop() + getPaddingBottom(), heightMeasureSpec));

        int rw = MeasureSpec.getSize(widthMeasureSpec);
        int rh = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        if (childCount > 0 ) {
            childWidth = (rw - getPaddingLeft() - getPaddingRight() - (columnNum - 1) * horizontalSpace) / columnNum;
            //noinspection SuspiciousNameCombination
            childHeight = childWidth;

            int vw = rw;
            if (childCount < columnNum) {
                vw = childCount * childHeight + (childCount - 1) * horizontalSpace + getPaddingLeft() + getPaddingRight();
            }

            int rowCount = childCount / columnNum + (childCount % columnNum != 0 ? 1 : 0);
            int vh = rowCount * childHeight + (rowCount > 0 ? rowCount - 1 : 0) * verticalSpace + getPaddingTop() + getPaddingBottom();

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(vw, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(vh, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(getPaddingTop() + getPaddingBottom(), MeasureSpec.UNSPECIFIED);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left;
        int top;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            left = getPaddingLeft() + (i % columnNum) * (childWidth + horizontalSpace);
            top = getPaddingTop() + (i / columnNum) * (childHeight + verticalSpace);
            child.layout(left, top, left + childWidth, top + childHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);

                Rect rc = new Rect();
                child.getHitRect(rc);

                if (rc.contains(x, y)) {
                    String tag = (String) child.getTag(R.id.tag_data);
                    if (onImageClickedListener != null) {
                        onImageClickedListener.onImageClicked(this, TextUtils.equals(tag, tagImgAdd), i);
                    }
                    break;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public String getPics() {
        if (enableLimit) {
            return mSb.toString();
        }
        StringBuilder sb = new StringBuilder();
        int count = getChildCount();
        String tagAdd = getContext().getString(R.string.img_add);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof ImageView) {
                String path = (String) child.getTag(R.id.tag_data);
                if (!TextUtils.isEmpty(path) && !TextUtils.equals(path, tagAdd)) {
                    sb.append(path).append(",");
                }
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private StringBuilder mSb = new StringBuilder();

    public void appendUrl(String url) {
        if (mSb.length() > 0) {
            mSb.append(",");
        }
        mSb.append(url);
    }

    public void clearUrl() {
        mSb.delete(0, mSb.length());
    }

    public int getMaxNum() {
        return maxNum;
    }

    private OnImageClickedListener onImageClickedListener;

    public void setOnImageClickedListener(OnImageClickedListener onImageClickedListener) {
        this.onImageClickedListener = onImageClickedListener;
    }

    public interface OnImageClickedListener {
        void onImageClicked(GridImageLayout view, boolean addFlag, int position);
    }
}
