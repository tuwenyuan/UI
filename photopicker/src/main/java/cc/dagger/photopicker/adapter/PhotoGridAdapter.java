package cc.dagger.photopicker.adapter;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import cc.dagger.photopicker.PhotoPicker;
import cc.dagger.photopicker.PhotoPickerImageLoader;
import cc.dagger.photopicker.R;
import cc.dagger.photopicker.bean.Image;
import cc.dagger.photopicker.event.OnPhotoGridClickListener;

/**
 * Created by wzfu on 2016/5/25.
 */
public class PhotoGridAdapter extends SelectableAdapter {

    public final static int ITEM_TYPE_CAMERA = 100;
    public final static int ITEM_TYPE_PHOTO  = 101;

    private Context mCxt;
    private LayoutInflater inflater;
    private PhotoPickerImageLoader imageLoader;

    private int imageSize;
    private boolean showCamera = true;
    // 多选时显示指示器
    private boolean showSelectIndicator = true;
    private OnPhotoGridClickListener onPhotoGridClickListener;

    public PhotoGridAdapter(Context mCxt, boolean showCamera, int colNum){
        this.mCxt = mCxt;
        this.showCamera = showCamera;
        this.imageLoader = PhotoPicker.getInstance().getImageLoader();
        inflater = LayoutInflater.from(mCxt);

        WindowManager wm = (WindowManager) mCxt.getSystemService(Context.WINDOW_SERVICE);
        int width;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            width = size.x;
        } else {
            width = wm.getDefaultDisplay().getWidth();
        }
        imageSize = width / colNum;
    }

    public void setOnPhotoGridClickListener(OnPhotoGridClickListener listener) {
        this.onPhotoGridClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View itemView;
        if(viewType == ITEM_TYPE_PHOTO) {
            itemView = inflater.inflate(R.layout.photopicker_grid_item_image, parent, false);
            holder = new PhotoViewHolder(itemView, imageSize);
        }else{
            itemView = inflater.inflate(R.layout.photopicker_grid_item_camera, parent, false);
            holder = new CaremaViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onPhotoGridClickListener != null) {
                        onPhotoGridClickListener.onCameraClick();
                    }
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position) == ITEM_TYPE_PHOTO) {

            final PhotoViewHolder holder = (PhotoViewHolder) viewHolder;

            final Image image = mImages.get(showCamera() ? position - 1 : position);
            // 是否显示指示器
            holder.indicator.setVisibility(showSelectIndicator ? View.VISIBLE : View.GONE);
            // 设置选中状态和背景
            boolean selected = isSelected(image);
            holder.mask.setVisibility(selected ? View.VISIBLE : View.GONE);
            if(showSelectIndicator){
                holder.indicator.setImageResource(selected ? R.drawable.btn_selected
                        : R.drawable.btn_unselected);
            }
            // 显示图片
            if(imageLoader != null) {
                imageLoader.loadGridItemView(
                                holder.gridItemView,
                                image.path,
                                R.id.photopicker_item_tag_id,
                                imageSize, imageSize);

                holder.gridItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int pos = holder.getAdapterPosition();

                        if(onPhotoGridClickListener != null){
                            onPhotoGridClickListener.onPhotoClick(image, pos);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (showCamera() && position == 0) ? ITEM_TYPE_CAMERA : ITEM_TYPE_PHOTO;
    }

    @Override
    public int getItemCount() {
        int count = mImages.size();
        if(showCamera()){
            return count + 1;
        }
        return count;
    }

    public void setShowCamera(boolean b) {
        if (showCamera == b) return;
        this.showCamera = b;
        notifyDataSetChanged();
    }

    public boolean showCamera() {
        return showCamera;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder{

        FrameLayout imageFrame;
        ImageView gridItemView;
        View mask;
        ImageView indicator;

        public PhotoViewHolder(View itemView, int imageSize) {
            super(itemView);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(imageSize, imageSize);
            itemView.setLayoutParams(layoutParams);

            mask = itemView.findViewById(R.id.mask);
            indicator = (ImageView) itemView.findViewById(R.id.checkmark);
            imageFrame = (FrameLayout) itemView.findViewById(R.id.imageFrame);

            if (PhotoPicker.getInstance() != null) {
                gridItemView = PhotoPicker.getInstance().getImageLoader()
                        .onCreateGridItemView(itemView.getContext());
                imageFrame.addView(gridItemView, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                ));
            }
        }
    }

    public static class CaremaViewHolder extends RecyclerView.ViewHolder{

        public CaremaViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 显示选择指示器
     *
     * @param b
     */
    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }


    /**
     * 选择某个图片，改变选择状态
     *
     * @param image
     */
    public void select(Image image, int pos) {
        toggleSelection(image);
        notifyItemChanged(pos);
    }
}
