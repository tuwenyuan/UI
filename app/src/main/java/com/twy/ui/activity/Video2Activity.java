package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.twy.ui.R;
import com.twy.ui.databinding.ActivityVideo2Binding;
import com.twy.ui.databinding.ItemMyRvBinding;
import com.twy.ui.entity.VideoConstant;

import cn.jzvd.JZVideoPlayer;

/**
 * Created by twy on 2018/2/7.
 */

public class Video2Activity extends AppCompatActivity {

    private ActivityVideo2Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_video2,null,false);
        setContentView(binding.getRoot());
        initData();
        initListener();
    }

    private void initListener() {
        binding.rv.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                JZVideoPlayer.onChildViewAttachedToWindow(view, R.id.videoplayer);
                //JZVideoPlayer.onScrollAutoTiny();
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayer.onChildViewDetachedFromWindow(view);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (JZVideoPlayer.backPress()) {
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    /*
     @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && binding != null) {
            JZVideoPlayer.releaseAllVideos();
        }
    }*/

    private void initData() {
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(new MyRvAdapter());
    }

    public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder>{
        int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemMyRvBinding binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_my_rv,null,false);
            return new MyViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.binding.videoplayer.setUp(VideoConstant.videoUrls[0][position], JZVideoPlayer.SCREEN_WINDOW_LIST,
                    VideoConstant.videoTitles[0][position]);
            Glide.with(Video2Activity.this)
                    .load(VideoConstant.videoThumbs[0][position])
                    .into(holder.binding.videoplayer.thumbImageView);
        }

        @Override
        public int getItemCount() {
            return videoIndexs.length;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ItemMyRvBinding binding;
            public MyViewHolder(ItemMyRvBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

}
