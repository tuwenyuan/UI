package com.twy.ui.utils.shipei;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.twy.ui.R;

/**
 * Created by twy on 2017/12/8.
 */

public class PrecentRelativeLayout extends RelativeLayout {
    public PrecentRelativeLayout(Context context) {
        super(context);
    }

    public PrecentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrecentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PrecentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //测量出子控件的宽高进行改变
        int childCount = this.getChildCount();
        for(int i =0;i<childCount;i++){
            View child = this.getChildAt(i);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            //把已经得到的布局参数进行更改
            float widthPrecent = 0;
            float heightPrecent = 0;
            if(layoutParams instanceof LayoutParams){
                //获取到布局文件上的内容
                widthPrecent = ((LayoutParams) layoutParams).getWidthPrecent();
                heightPrecent = ((LayoutParams) layoutParams).getHeightPrecent();
            }

            if(widthPrecent>0){
                layoutParams.width = (int)(width*widthPrecent);
            }
            if(heightPrecent>0){
                layoutParams.height = (int)(height*heightPrecent);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    //需要把自己自定义的属性封装进去
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //这里返回自己设置好的布局参数
        return new LayoutParams(getContext(),attrs);
    }

    public static class LayoutParams extends RelativeLayout.LayoutParams{

        private float widthPrecent;

        public float getWidthPrecent() {
            return widthPrecent;
        }

        public void setWidthPrecent(float widthPrecent) {
            this.widthPrecent = widthPrecent;
        }

        public float getHeightPrecent() {
            return heightPrecent;
        }

        public void setHeightPrecent(float heightPrecent) {
            this.heightPrecent = heightPrecent;
        }

        private float heightPrecent;


        /**
         * 这里把我们自定义属性加入
         * @param c
         * @param attrs
         */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.PrecentRelativeLayout);
            widthPrecent = array.getFloat(R.styleable.PrecentRelativeLayout_layout_widthPrecent,this.getWidthPrecent());
            heightPrecent = array.getFloat(R.styleable.PrecentRelativeLayout_layout_heightPrecent,this.getHeightPrecent());

        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public LayoutParams(RelativeLayout.LayoutParams source) {
            super(source);
        }
    }
}
