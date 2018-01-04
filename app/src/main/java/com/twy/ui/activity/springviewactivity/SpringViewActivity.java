package com.twy.ui.activity.springviewactivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivitySpringViewBinding;

/**
 * Created by twy on 2018/1/4.
 */

public class SpringViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySpringViewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_spring_view,null,false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        binding.demo1.setOnClickListener(this);
        binding.demo2.setOnClickListener(this);
        binding.demo3.setOnClickListener(this);
        binding.demo4.setOnClickListener(this);
        binding.demo5.setOnClickListener(this);
        binding.demo6.setOnClickListener(this);
        binding.demo7.setOnClickListener(this);
        binding.demo8.setOnClickListener(this);
        binding.demo9.setOnClickListener(this);
        binding.warning.setOnClickListener(this);
        binding.test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.demo1:
                intent.setClass(this, Demo1Activity.class);
                startActivity(intent);
                break;
            case R.id.demo2:
                intent.setClass(this, Demo2Activity.class);
                startActivity(intent);
                break;
            case R.id.demo3:
                intent.setClass(this, Demo3Activity.class);
                startActivity(intent);
                break;
            case R.id.demo4:
                intent.setClass(this, Demo4Activity.class);
                startActivity(intent);
                break;
            case R.id.demo5:
                intent.setClass(this, Demo5Activity.class);
                startActivity(intent);
                break;
            case R.id.demo6:
                intent.setClass(this, Demo6Activity.class);
                startActivity(intent);
                break;
            case R.id.demo7:
                intent.setClass(this, Demo7Activity.class);
                startActivity(intent);
                break;
            case R.id.demo8:
                intent.setClass(this, Demo8Activity.class);
                startActivity(intent);
                break;
            case R.id.demo9:
                intent.setClass(this, Demo9Activity.class);
                startActivity(intent);
                break;
            case R.id.warning:
                intent.setClass(this, WarningActivity.class);
                startActivity(intent);
                break;
            case R.id.test:
                intent.setClass(this, TestActivity.class);
                startActivity(intent);
                break;
        }
    }
}
