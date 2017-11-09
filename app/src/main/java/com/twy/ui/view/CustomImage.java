package com.twy.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 创建者     涂文远
 * 创建时间   2016/11/3 9:43
 * 描述	      ${TODO}
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
@SuppressLint("AppCompatCustomView")
public class CustomImage extends ImageView {
    private int mResId;

    public CustomImage(Context context) {
        this(context, null, 0);
    }

    public CustomImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            String namespace = "http://schemas.android.com/apk/res/android";
            String attribute = "src";
            mResId = attrs.getAttributeResourceValue(namespace, attribute, 0);
        }
    }

    public int getImageId() {
        return mResId;
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mResId = resId;
    }
}