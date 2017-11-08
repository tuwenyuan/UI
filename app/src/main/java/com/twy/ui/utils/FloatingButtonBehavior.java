package com.twy.ui.utils;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/7/12.
 */

public class FloatingButtonBehavior extends FloatingActionButton.Behavior{
    private static final String TAG = "tuch";
    private  boolean isAnimate=false;
    //加速器
    private static final FastOutSlowInInterpolator FASTOUTSLOWININTERPOLATOR = new FastOutSlowInInterpolator();

    public FloatingButtonBehavior(Context context, AttributeSet attrs) {
        super();
    }

    /**
     * 关心  滑动横轴
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        // 当观察的view(RecyclerView)发生滑动开始的时候回调
        //nestedScrollAxes:滑动关联轴---我们这里只关心垂直滑动
        return nestedScrollAxes== ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * 
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i(TAG, "onNestedScroll: dyConsumed  "+dyConsumed);
        if (dyConsumed > 0) {
            animateOut(child);
        } else if (dyConsumed < 0) {
            animateIn(child);
        }
    }

    private void animateIn(FloatingActionButton child) {
        child.setVisibility(View.VISIBLE);
        if(Build.VERSION.SDK_INT>=14) {
            ViewCompat.animate(child)
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .alpha(1.0f)
                    .setListener(null)
                    .setInterpolator(FASTOUTSLOWININTERPOLATOR)
                    .start();
        }
    }

    private void animateOut(FloatingActionButton child) {
        if(Build.VERSION.SDK_INT>=14){
            ViewCompat.animate(child).scaleX(0.0f).scaleY(0.0f).setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {
                    isAnimate=true;
                }

                @Override
                public void onAnimationEnd(View view) {
                    isAnimate=false;
                }

                @Override
                public void onAnimationCancel(View view) {
                    isAnimate=false;
                }
            });
        }
    }
}
