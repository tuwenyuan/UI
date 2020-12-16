package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityProgressViewBinding;

/*
 * Created by twy on 12/16/20
 */
public class ProgressViewActivity extends AppCompatActivity {
    ActivityProgressViewBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_progress_view, null, false);
        setContentView(binding.getRoot());
    }
}
