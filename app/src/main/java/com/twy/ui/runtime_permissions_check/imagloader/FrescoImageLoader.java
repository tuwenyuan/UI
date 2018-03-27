package com.twy.ui.runtime_permissions_check.imagloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.twy.ui.R;
import com.twy.ui.base.BaseAplication;

import cc.dagger.photopicker.PhotoPickerImageLoader;
import me.relex.photodraweeview.PhotoDraweeView;


/**
 * Created by wzfu on 16/6/5.
 */
public class FrescoImageLoader extends PhotoPickerImageLoader<SimpleDraweeView, PhotoDraweeView> {

    public FrescoImageLoader() {
        // Init Fresco, 建议写到 Application 中
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(BaseAplication.getInstance())
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(BaseAplication.getInstance(), imagePipelineConfig);
    }

    @Override
    public SimpleDraweeView onCreateGridItemView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.photopicker_frsco_itemview, null);
        return (SimpleDraweeView) view;
    }

    @Override
    public void loadGridItemView(SimpleDraweeView view, String imagePath, int tag, int width, int height) {

        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(imagePath)
                .build();

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(view.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();

        view.setController(controller);
    }

    @Override
    public void resumeRequests(Context mCxt, int tag) {

    }

    @Override
    public void pauseRequests(Context mCxt, int tag) {

    }

    @Override
    public PhotoDraweeView onCreatePreviewItemView(Context context) {
        PhotoDraweeView mPhotoDraweeView = new PhotoDraweeView(context);
        return mPhotoDraweeView;
    }

    @Override
    public void loadPreviewItemView(final PhotoDraweeView mPhotoDraweeView, String imagePath,
                                       int width, int height) {

        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();

        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(imagePath)
                .build();
        controller.setUri(uri);

        controller.setOldController(mPhotoDraweeView.getController());

        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))//图片目标大小
                .build();
        controller.setImageRequest(imageRequest);

        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || mPhotoDraweeView == null) {
                    return;
                }
                mPhotoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });

        mPhotoDraweeView.setController(controller.build());
    }
}
