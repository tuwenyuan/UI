package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.twy.ui.R;
import com.twy.ui.databinding.ActivityDragScrollDetailBinding;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by twy on 2018/1/25.
 */

public class DragScrollDetailActivity extends AppCompatActivity{

    private ActivityDragScrollDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_drag_scroll_detail,null,false);
        setContentView(binding.getRoot());

        initData();
    }

    private void initData() {
        Glide.with(this)
                .load("https://img2.ugou88.com/78a8aadc-d53c-4839-8d01-7b7571780e6b.jpg")
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new BlurTransformation(this, 14, 3))
                .into(binding.iv);
    }
}
