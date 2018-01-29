package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityAnime2Binding;

/**
 * Created by twy on 2018/1/26.
 */

public class Anime2Activity extends AppCompatActivity {

    private ActivityAnime2Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_anime2,null,false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        binding.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Anime2DetailActivity.startWithTransitionAnimation(Anime2Activity.this,binding.iv);
            }
        });
    }
}
