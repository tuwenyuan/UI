package com.handmark.pulltorefresh.library.expand;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

/**
 * @描述:
 * @项目名: ugou
 * @包名: com.ugou88.ugou.ui.view
 * @类名: ServiceFeedbackActivity
 * @作者: soongkun
 * @创建时间: 2016/5/4 9:24
 */
public class NoScrollExpandableListView extends ExpandableListView {
    private ExpandableListAdapter mAdapter;

    public NoScrollExpandableListView(Context context) {
        super(context);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = adapter;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mAdapter == null){
            return;
        }

        for(int i = 0 ;i < mAdapter.getGroupCount();i++){
            expandGroup(i);
        }
        this.setFocusable(false);
    }
}
