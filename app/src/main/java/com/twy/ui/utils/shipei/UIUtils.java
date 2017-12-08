package com.twy.ui.utils.shipei;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.twy.ui.base.BaseAplication;

import java.lang.reflect.Field;

/**
 * Created by twy on 2017/12/7.
 * 用来按美工的基准生成真实设备上需要的宽高值
 */

public class UIUtils {

    public static UIUtils ourInstance;
    public static UIUtils getInstance(Context context){
        if (ourInstance==null)
            ourInstance = new UIUtils(context);
        return ourInstance;
    }
    //基准
    public static final int STANDARD_WIDTH = 1080;
    public static final int STANDARD_HEIGHT = 1920;//1920-48
    private static final String DIMEN_CLASS="com.android.internal.R$dimen";

    //实际设备分辨率 480  800
    public float displayMetricsWidth;
    public float diplayMetricsHeight;

    private UIUtils(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if(displayMetricsWidth==0f || diplayMetricsHeight == 0f){
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            //获取到状态框的高度48
            int systemBarHight = getSystemBarHeight(context);
            //处理真实宽度的问题
            if(displayMetrics.widthPixels>displayMetrics.heightPixels){//横
                this.displayMetricsWidth = displayMetrics.heightPixels;
                this.diplayMetricsHeight = displayMetrics.widthPixels-systemBarHight;
            }else{//竖
                this.displayMetricsWidth = displayMetrics.widthPixels;
                this.diplayMetricsHeight = displayMetrics.heightPixels-systemBarHight;
            }
        }
    }

    private int getSystemBarHeight(Context context) {
        return getValue(context,"com.android.internal.R$dimen","system_bar_height",48);
    }

    /**
     *
     * @param context
     * @param attrGroupClass android中找到存放围度的类
     * @param attrName 状态框的信息
     * @return
     */
    private int getValue(Context context, String attrGroupClass, String attrName,int defValue) {
        try {
            Class e = Class.forName(attrGroupClass);
            Object obj = e.newInstance();
            Field field = e.getField(attrName);
            //获取到的是一个ID
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelOffset(x);
        } catch (Exception e1) {
            e1.printStackTrace();
            return defValue;
        }
    }

    //开始获取缩放以后的结果
    public float getWidth(float width){
        return width*this.diplayMetricsHeight/STANDARD_WIDTH;
    }

    public float getHeight(float height){
        return height*this.diplayMetricsHeight/(STANDARD_HEIGHT-getSystemBarHeight(BaseAplication.getInstance()));
    }
    public int getWidth(int width){
        return (int) (width*this.diplayMetricsHeight/STANDARD_WIDTH);//1080
    }

    public int getHeight(int height){
        return (int) (height*this.diplayMetricsHeight/(STANDARD_HEIGHT-getSystemBarHeight(BaseAplication.getInstance())));//1920-48
    }


}
