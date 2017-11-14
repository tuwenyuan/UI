package com.twy.ui.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by twy on 2017/11/8.
 */

public class BaseAplication extends Application {


    private static Context instance;
    public static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mHandler = new Handler();
        initImageLoader(getApplicationContext());
    }

    public static Context getInstance() {
        return instance;
    }

    /**
     * 初始化图片加载
     *
     * @param context 上下文对象
     */
    public void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "ui/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100)
                .diskCache(new UnlimitedDiskCache(cacheDir)).build();
        L.writeLogs(false);
        ImageLoader.getInstance().init(config);

    }
}
