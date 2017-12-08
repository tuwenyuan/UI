package com.twy.ui.utils.shipei;

import android.view.View;
import android.widget.LinearLayout;

import com.twy.ui.base.BaseAplication;

/**
 * Created by twy on 2017/12/8.
 */

public class ViewCalculateUtil {
    //获取调用层传入的值进行设置
    public static void setViewLinearLayoutParam(View view,int width,int heigth,int topMargin,int bottomMargin,int leftMargin,int rightMargin){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        if(width!=LinearLayout.LayoutParams.MATCH_PARENT && width!=LinearLayout.LayoutParams.WRAP_CONTENT){
            layoutParams.width = UIUtils.getInstance(BaseAplication.getInstance()).getWidth(width);
        }else {
            layoutParams.width = width;
        }

        if(heigth!= LinearLayout.LayoutParams.MATCH_PARENT && heigth != LinearLayout.LayoutParams.WRAP_CONTENT){
            layoutParams.height = UIUtils.getInstance(BaseAplication.getInstance()).getHeight(heigth);
        }else{
            layoutParams.height = heigth;
        }

        layoutParams.topMargin = UIUtils.getInstance(BaseAplication.getInstance()).getHeight(topMargin);
        layoutParams.bottomMargin = UIUtils.getInstance(BaseAplication.getInstance()).getHeight(bottomMargin);
        layoutParams.leftMargin = UIUtils.getInstance(BaseAplication.getInstance()).getWidth(leftMargin);
        layoutParams.rightMargin = UIUtils.getInstance(BaseAplication.getInstance()).getWidth(rightMargin);
        view.setLayoutParams(layoutParams);
    }
}
