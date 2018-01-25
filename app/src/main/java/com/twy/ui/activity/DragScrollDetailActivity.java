package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityDragScrollDetailBinding;

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
    }
}
