package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityBigImgBinding;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by twy on 2018/1/17.
 */

public class BigImgActivity extends AppCompatActivity{

    private ActivityBigImgBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_big_img,null,false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        InputStream is = null;
        try {
            //从assets 加载图片
            is = getAssets().open("big.jpg");
            binding.bigView.setImage(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
