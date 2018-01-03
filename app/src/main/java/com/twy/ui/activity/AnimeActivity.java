package com.twy.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.twy.ui.R;
import com.twy.ui.view.CustomImage;

/**
 * Created by twy on 2017/11/9.
 */

public class AnimeActivity extends AppCompatActivity {

    private ViewDataBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_anime, null, false);
        setContentView(binding.getRoot());
    }

    public void imageClick(View view){
        Intent intent = new Intent(AnimeActivity.this, AnimeDetailActivity.class);
        // 创建一个 rect 对象来存储共享元素位置信息
        Rect rect = new Rect();
        // 获取元素位置信息
        view.getGlobalVisibleRect(rect);
        // 将位置信息附加到 intent 上
        intent.setSourceBounds(rect);
        CustomImage customImage = (CustomImage) view;
        intent.putExtra(AnimeDetailActivity.EXTRA_IMAGE, customImage.getImageId());
        intent.putExtra("tag",view.getTag().toString());
        startActivity(intent);
        // 屏蔽 Activity 默认转场效果
        overridePendingTransition(0, 0);
    }
}
