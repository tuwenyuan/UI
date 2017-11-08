package com.twy.ui.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by twy on 2017/11/8.
 */

public class BaseAplication extends Application {


    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }
}
