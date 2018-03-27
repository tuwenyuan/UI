package com.twy.ui.runtime_permissions_check.imagloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.twy.ui.base.BaseAplication;

import cc.dagger.photopicker.PhotoPickerImageLoader;
import cn.finalteam.galleryfinal.widget.zoonview.PhotoView;


/**
 * Created by wzfu on 16/6/5.
 */
public class UILImageLoader extends PhotoPickerImageLoader<ImageView, PhotoView> {

    private Bitmap.Config mImageConfig;

    public UILImageLoader() {

        this.mImageConfig = Bitmap.Config.RGB_565;

        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(BaseAplication.getInstance());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public ImageView onCreateGridItemView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }

    @Override
    public void loadGridItemView(ImageView view, String imagePath, int tag, int width, int height) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(false)
                .cacheInMemory(true)
                .bitmapConfig(mImageConfig)
                .showImageOnLoading(getDefaultPlaceHolder())
                .showImageOnFail(getDefaultPlaceHolder())
                .showImageOnFail(getDefaultPlaceHolder())
                .build();

        ImageSize imageSize = new ImageSize(width, height);

        ImageLoader.getInstance().displayImage("file://" + imagePath, new ImageViewAware(view), options, imageSize, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                ImageView gfImageView = (ImageView) view;
                gfImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                ImageView gfImageView = (ImageView) view;
                gfImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        }, null);
    }

    @Override
    public void resumeRequests(Context mCxt, int tag) {
        ImageLoader.getInstance().resume();
    }

    @Override
    public void pauseRequests(Context mCxt, int tag) {
        ImageLoader.getInstance().pause();
    }

    @Override
    public PhotoView onCreatePreviewItemView(Context context) {
        PhotoView photoView = new PhotoView(context);
        return photoView;
    }

    @Override
    public void loadPreviewItemView(PhotoView view, String imagePath, int width, int height) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(false)
                .cacheInMemory(true)
                .bitmapConfig(mImageConfig)
                .build();

        ImageSize imageSize = new ImageSize(width, height);
        ImageLoader.getInstance().displayImage("file://" + imagePath,
                new ImageViewAware(view), options, imageSize, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                }, null);
    }
}
