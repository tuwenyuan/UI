package cc.dagger.photopicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.dagger.photopicker.PhotoPicker;
import cc.dagger.photopicker.PhotoPickerImageLoader;
import cc.dagger.photopicker.R;
import cc.dagger.photopicker.bean.Folder;
import cc.dagger.photopicker.utils.ScreenUtils;

/**
 * 文件夹Adapter
 * Created by Nereo on 2015/4/7.
 * Updated by nereo on 2016/1/19.
 * Update by wzfu on 2016/6/12
 */
public class FolderAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<Folder> mFolders = new ArrayList<>();

    int mImageSize;

    int lastSelected = 0;

    public FolderAdapter(Context context){
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.folder_cover_size);
//        getDimensionPixelSize
    }

    public int getItemHeight(){
        // ImageHeight + PaddingTop + PaddingBottom
        return mImageSize + ScreenUtils.dip2px(mContext, 10) * 2;
    }

    /**
     * 设置数据集
     * @param folders
     */
    public void setData(List<Folder> folders) {
        if(folders != null && folders.size()>0){
            mFolders = folders;
        }else{
            mFolders.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolders.size()+1;
    }

    @Override
    public Folder getItem(int i) {
        if(i == 0) return null;
        return mFolders.get(i-1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = mInflater.inflate(R.layout.photopicker_list_item_folder, viewGroup, false);
            holder = new ViewHolder(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        if (holder != null) {
            if(i == 0){
                holder.name.setText(R.string.folder_all);
                holder.path.setText("/sdcard");
                holder.size.setText(String.format("%d%s",
                        getTotalImageSize(), mContext.getResources().getString(R.string.photo_unit)));
                if(mFolders.size() > 0) {
                    Folder f = mFolders.get(0);
                    if (f != null && PhotoPicker.getInstance() != null) {

                        PhotoPicker.getInstance().getImageLoader()
                                .loadGridItemView(
                                        holder.thumbView,
                                        f.cover.path,
                                        R.id.photopicker_item_tag_id,
                                        mImageSize,
                                        mImageSize);
                    }
                }
            }else {
                holder.bindData(getItem(i));
            }

            if(lastSelected == i){
                holder.indicator.setVisibility(View.VISIBLE);
            }else{
                holder.indicator.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    private int getTotalImageSize(){
        int result = 0;
        if(mFolders != null && mFolders.size()>0){
            for (Folder f: mFolders){
                result += f.images.size();
            }
        }
        return result;
    }

    public void setSelectIndex(int i) {
        if(lastSelected == i) return;

        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex(){
        return lastSelected;
    }

    class ViewHolder{
        TextView name;
        TextView path;
        TextView size;
        ImageView indicator;
        FrameLayout itemViewFrame;
        ImageView thumbView;

        ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            path = (TextView) view.findViewById(R.id.path);
            size = (TextView) view.findViewById(R.id.size);
            indicator = (ImageView) view.findViewById(R.id.indicator);
            itemViewFrame = (FrameLayout) view.findViewById(R.id.itemViewFrame);
            // Add ItemView
            if (PhotoPicker.getInstance() != null) {
                thumbView = PhotoPicker.getInstance().getImageLoader().onCreateGridItemView(view.getContext());
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                );
                itemViewFrame.addView(thumbView, lp);
            }
            view.setTag(this);
        }

        void bindData(Folder data) {
            if(data == null){
                return;
            }
            name.setText(data.name);
            path.setText(data.path);
            if (data.images != null) {
                size.setText(String.format("%d%s", data.images.size(), mContext.getResources().getString(R.string.photo_unit)));
            }else{
                size.setText("*"+mContext.getResources().getString(R.string.photo_unit));
            }
            if (data.cover != null && PhotoPicker.getInstance() != null) {
                PhotoPicker.getInstance().getImageLoader()
                        .loadGridItemView(
                                thumbView,
                                data.cover.path,
                                R.id.photopicker_item_tag_id,
                                mImageSize,
                                mImageSize);
            }
        }
    }

}
