package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityPicStyleBinding;
import com.twy.ui.utils.UIUtils;

/**
 * Created by twy on 2017/11/16.
 */

public class PicStyleActivity extends AppCompatActivity {

    private ActivityPicStyleBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pic_style, null, false);
        setContentView(binding.getRoot());
        initData();

    }

    private void initData() {
        ViewGroup.LayoutParams layoutParams1 = binding.ivKitchenWindowOne.getLayoutParams();
        layoutParams1.width = UIUtils.getScreenWidth(this) / 2;
        layoutParams1.height = UIUtils.getScreenWidth(this) / 2;
        ViewGroup.LayoutParams layoutParams2 = binding.ivKitchenWindowTwo.getLayoutParams();
        layoutParams2.width = UIUtils.getScreenWidth(this) / 2;
        layoutParams2.height = UIUtils.getScreenWidth(this) / 4;
        ViewGroup.LayoutParams layoutParams3 = binding.ivKitchenWindowThree.getLayoutParams();
        layoutParams3.width = UIUtils.getScreenWidth(this) / 2;
        layoutParams3.height = UIUtils.getScreenWidth(this) / 4;
    }
}
