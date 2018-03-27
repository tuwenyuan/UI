package com.twy.ui.runtime_permissions_check.imagloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.twy.ui.base.BaseAplication;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import cc.dagger.photopicker.PhotoPickerImageLoader;
import cn.finalteam.galleryfinal.widget.zoonview.PhotoView;


/**
 * Created by wzfu on 16/6/5.
 */
public class XUtilsImageLoader extends PhotoPickerImageLoader<ImageView, PhotoView> {

    private Bitmap.Config mImageConfig;

    public XUtilsImageLoader() {
        x.Ext.init(BaseAplication.getInstance());
        this.mImageConfig = Bitmap.Config.RGB_565;
    }

    @Override
    public ImageView onCreateGridItemView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }

    @Override
    public void loadGridItemView(ImageView view, String imagePath, int tag, int width, int height) {
        ImageOptions options = new ImageOptions.Builder()
                .setLoadingDrawableId(getDefaultPlaceHolder())
                .setFailureDrawableId(getDefaultPlaceHolder())
                .setConfig(mImageConfig)
                .setSize(width, height)
                .setCrop(true)
                .setUseMemCache(true)
                .build();
        x.image().bind(view, "file://" + imagePath, options);
    }

    @Override
    public void resumeRequests(Context mCxt, int tag) {

    }

    @Override
    public void pauseRequests(Context mCxt, int tag) {

    }

    @Override
    public PhotoView onCreatePreviewItemView(Context context) {
        PhotoView photoView = new PhotoView(context);
        return photoView;
    }

    @Override
    public void loadPreviewItemView(PhotoView view, String imagePath, int width, int height) {
        ImageOptions options = new ImageOptions.Builder()
                .setConfig(mImageConfig)
                .setSize(width, height)
                .setUseMemCache(false)
                .build();
        x.image().bind(view, "file://" + imagePath, options);
    }
}
