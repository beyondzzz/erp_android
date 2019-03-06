package com.jl.jlapp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.jl.jlapp.R;


public class SuperTextView extends android.support.v7.widget.AppCompatTextView {

    private String title;
    private String subTitle;

    private float ratio;
    private int subTitleColor;

    private Paint titlePaint;
    private Paint subTitlePaint;

    private Rect rcTitle;
    private Rect rcSubTitle;

    public SuperTextView(Context context) {
        this(context, null);
    }

    public SuperTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        title = getText().toString();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SuperTextView);
        for (int i = 0, stop = ta.getIndexCount(); i < stop; i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case R.styleable.SuperTextView_subTitle:
                    subTitle = ta.getString(index);
                    break;
                case R.styleable.SuperTextView_subTitleColor:
                    subTitleColor = ta.getColor(index, ContextCompat.getColor(context, R.color.dark_gray));
                    break;
                case R.styleable.SuperTextView_ratio:
                    ratio = ta.getFraction(index, 1, 1, 0.88f);
                    break;
                default:
                    subTitle = "";
                    break;
            }
        }
        ta.recycle();

        if (TextUtils.isEmpty(subTitle)) {
            subTitle = "";
        }

        titlePaint = getPaint();
        titlePaint.setColor(getCurrentTextColor());

        subTitlePaint = new Paint(titlePaint);

        float titleSize = getTextSize();
        subTitlePaint.setTextSize(titleSize * ratio);
        subTitlePaint.setColor(subTitleColor);
        subTitlePaint.setAntiAlias(true);
        subTitlePaint.setTextAlign(Paint.Align.CENTER);

        rcTitle = new Rect();
        rcSubTitle = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (TextUtils.isEmpty(subTitle)) {
            super.onDraw(canvas);
            return;
        }

        titlePaint.getTextBounds(title, 0, title.length(), rcTitle);
        subTitlePaint.getTextBounds(subTitle, 0, subTitle.length(), rcSubTitle);

        final int pdl = getTotalPaddingLeft();
        final int pdt = getTotalPaddingTop();
        final int pdr = getTotalPaddingRight();
        final int pdb = getTotalPaddingBottom();

        int width = getMeasuredWidth() - pdl - pdr;
        int height = getMeasuredHeight() - pdt - pdb;

        int h = Math.max(rcTitle.height(), rcSubTitle.height());

        Paint.FontMetricsInt titleFM = titlePaint.getFontMetricsInt();
        Paint.FontMetricsInt subTitleFM = subTitlePaint.getFontMetricsInt();

        int left = pdl;
        int baseline = pdt + (height - h) / 2 + (h - titleFM.bottom - titleFM.top) / 2;
        int baselineSub = pdt + (height - h) / 2 + (h - subTitleFM.bottom - subTitleFM.top) / 2;

        int subLeft = getMeasuredWidth() - pdr - rcSubTitle.width() / 2;

        canvas.drawText(title, left, baseline, titlePaint);
        canvas.drawText(subTitle, subLeft, baselineSub, subTitlePaint);

        Drawable[] drawables = getCompoundDrawables();
        if (drawables[0] != null) {
            int dw = drawables[0].getIntrinsicWidth();
            int dh = drawables[0].getIntrinsicHeight();
            left = getPaddingLeft();
            int top = pdt + (height - dh) / 2;
            drawables[0].setBounds(left, top, left + dw, top + dh);
            drawables[0].draw(canvas);
        }
        if (drawables[1] != null) {
            int dw = drawables[1].getIntrinsicWidth();
            int dh = drawables[1].getIntrinsicHeight();
            left = pdl + (width - dw) / 2;
            int top = getPaddingTop();
            drawables[1].setBounds(left, top, left + dw, top + dh);
            drawables[1].draw(canvas);
        }
        if (drawables[2] != null) {
            int dw = drawables[2].getIntrinsicWidth();
            int dh = drawables[2].getIntrinsicHeight();
            left = getMeasuredWidth() - getPaddingRight() - dw;
            int top = pdt + (height - dh) / 2;
            drawables[2].setBounds(left, top, left + dw, top + dh);
            drawables[2].draw(canvas);
        }
        if (drawables[3] != null) {
            int dw = drawables[3].getIntrinsicWidth();
            int dh = drawables[3].getIntrinsicHeight();
            left = pdl + (width - dw) / 2;
            int top = getMeasuredHeight() - getPaddingBottom() - dh;
            drawables[3].setBounds(left, top, left + dw, top + dh);
            drawables[3].draw(canvas);
        }
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        invalidate();
    }

    public String getSubTitle() {
        return subTitle;
    }
}
