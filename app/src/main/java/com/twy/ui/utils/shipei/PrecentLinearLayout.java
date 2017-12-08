package com.twy.ui.utils.shipei;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.twy.ui.R;

/**
 * Created by twy on 2017/12/8.
 */

public class PrecentLinearLayout extends LinearLayout {
    public PrecentLinearLayout(Context context) {
        super(context);
    }

    public PrecentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PrecentLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PrecentLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            int width = 0;
            int height = 0;
            int marginTop = 0;
            int marginBottom = 0;
            int marginLeft = 0;
            int marginRight = 0;
            if (layoutParams instanceof LayoutParams) {
                width = ((LayoutParams) layoutParams).getWidth();
                height = ((LayoutParams) layoutParams).getHeight();
                marginTop = ((LayoutParams) layoutParams).getMarginTop();
                marginBottom = ((LayoutParams) layoutParams).getMarginBottom();
                marginLeft = ((LayoutParams) layoutParams).getMarginLeft();
                marginRight = ((LayoutParams) layoutParams).getMarginRight();
            }
            if (width > 0) {
                layoutParams.width = UIUtils.getInstance(getContext()).getWidth(width);
            }
            if(height>0){
                layoutParams.height = UIUtils.getInstance(getContext()).getWidth(height);
            }
            if(marginTop>0){
                ((LayoutParams) layoutParams).marginTop = UIUtils.getInstance(getContext()).getHeight(marginTop);
            }
            if(marginBottom>0){
                ((LayoutParams) layoutParams).marginBottom = UIUtils.getInstance(getContext()).getHeight(marginBottom);
            }
            if(marginRight>0){
                ((LayoutParams) layoutParams).marginRight = UIUtils.getInstance(getContext()).getWidth(marginRight);
            }
            if(marginLeft>0){
                ((LayoutParams) layoutParams).marginLeft = UIUtils.getInstance(getContext()).getWidth(marginLeft);
            }
            child.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public LinearLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {

        private int width;
        private int height;
        private int marginTop;
        private int marginBottom;
        private int marginLeft;
        private int marginRight;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getMarginTop() {
            return marginTop;
        }

        public void setMarginTop(int marginTop) {
            this.marginTop = marginTop;
        }

        public int getMarginBottom() {
            return marginBottom;
        }

        public void setMarginBottom(int marginBottom) {
            this.marginBottom = marginBottom;
        }

        public int getMarginLeft() {
            return marginLeft;
        }

        public void setMarginLeft(int marginLeft) {
            this.marginLeft = marginLeft;
        }

        public int getMarginRight() {
            return marginRight;
        }

        public void setMarginRight(int marginRight) {
            this.marginRight = marginRight;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.PrecentLinearLayout);
            width = array.getInt(R.styleable.PrecentLinearLayout_adapter_width, this.getWidth());
            height = array.getInt(R.styleable.PrecentLinearLayout_adapter_height, this.getHeight());
            marginTop = array.getInt(R.styleable.PrecentLinearLayout_adapter_margin_top, this.getMarginTop());
            marginBottom = array.getInt(R.styleable.PrecentLinearLayout_adapter_margin_bottom, this.getMarginBottom());
            marginLeft = array.getInt(R.styleable.PrecentLinearLayout_adapter_margin_left, this.getMarginLeft());
            marginRight = array.getInt(R.styleable.PrecentLinearLayout_adapter_margin_right, this.getMarginRight());
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height, weight);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public LayoutParams(LinearLayout.LayoutParams source) {
            super(source);
        }
    }
}
