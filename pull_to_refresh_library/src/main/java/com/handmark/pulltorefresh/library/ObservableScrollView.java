package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

/**
 * @描述: 监听ScrollView的滑动
 * @项目名: ugou
 * @包名: com.handmark.pulltorefresh.library
 * @类名: ObservableScrollView
 * @作者: zuojie
 * @创建时间: 2016/6/2 10:31
 */
public class ObservableScrollView extends ScrollView {

    private OnScrollChangedListener onScrollChangedListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}