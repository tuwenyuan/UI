package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityAdapterScreenBinding;
import com.twy.ui.utils.shipei.ViewCalculateUtil;

/**
 * Created by twy on 2017/12/8.
 */

public class AdapterScreenActivity extends AppCompatActivity {

    private ActivityAdapterScreenBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_adapter_screen, null, false);
        setContentView(binding.getRoot());
        ViewCalculateUtil.setViewLinearLayoutParam(binding.tv1,1040,80,10,0,20,20);
        ViewCalculateUtil.setViewLinearLayoutParam(binding.tv2,400,400,50,0,0,0);
    }
}
