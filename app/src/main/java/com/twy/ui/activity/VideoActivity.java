package com.twy.ui.activity;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.fy.videolib.JCVideoPlayer;
import com.fy.videolib.JCVideoPlayerStandard;
import com.twy.ui.R;
import com.twy.ui.databinding.ActivityVideoBinding;
import com.twy.ui.listener.MyUserActionStandard;
import com.twy.ui.utils.StatusBarUtil;

/**
 * Created by twy on 2018/2/5.
 */

public class VideoActivity extends AppCompatActivity {

    private ActivityVideoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_video,null,false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        StatusBarUtil.setTranslucentForImageView(this,0,null);
        binding.vStatus.getLayoutParams().height = StatusBarUtil.getStatusBarHeight(this);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        binding.video.setActivity(this);
        binding.video.setUp("http://owmd1e3cy.bkt.clouddn.com/%E8%93%9D%E5%A4%A9%E5%BC%98%E6%9E%97%E9%BB%84%E8%8A%B1%E6%A2%A8%E6%88%90%E7%89%87~3.mp4"
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        Glide.with(this)
                .load("https://img2.ugou88.com/2ac0c4da-a1d5-4740-8a82-67596ee5e611.png")
                .into(binding.video.thumbImageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
    //fragment
    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && binding != null) {
            if (binding.video.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING)
                binding.video.startButton.performClick();
        }
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /*1）.View.SYSTEM_UI_FLAG_VISIBLE ：状态栏和Activity共存，Activity不全屏显示。也就是应用平常的显示画面
          2）.View.SYSTEM_UI_FLAG_FULLSCREEN ：Activity全屏显示，且状态栏被覆盖掉
          3）. View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN ：Activity全屏显示，但是状态栏不会被覆盖掉，而是正常显示，只是Activity顶端布 局会被覆盖住
          4）.View.INVISIBLE ： Activity全屏显示，隐藏状态栏*/
        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) { //切换为竖屏
            binding.getRoot().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {//切换为横屏
            binding.getRoot().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (binding != null) {
                Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
                if (mConfiguration.orientation == mConfiguration.ORIENTATION_LANDSCAPE) {//横屏
                    binding.video.backPress();
                    return true;
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
