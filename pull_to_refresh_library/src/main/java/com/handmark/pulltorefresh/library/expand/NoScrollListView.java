package com.handmark.pulltorefresh.library.expand;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * @描述: 适应嵌套ListView的效果
 * @项目名: ugou
 * @包名: com.ugou88.ugou.ui.view
 * @类名: NoScrollListView
 * @作者: zuojie
 * @创建时间: 2016/3/24 17:56
 */
public class NoScrollListView extends ListView {


    public NoScrollListView(Context context) {
        super(context);
        if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
            this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写该方法，达到适应嵌套ListView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
