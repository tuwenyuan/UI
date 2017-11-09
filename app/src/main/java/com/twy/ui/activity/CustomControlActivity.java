package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityCustomControlBinding;
import com.twy.ui.view.SwitchHeaderButton;

/**
 * Created by twy on 2017/11/9.
 */

public class CustomControlActivity extends AppCompatActivity{

    private ActivityCustomControlBinding binding;
    private static final int MSG_PROGRESS_UPDATE = 0x110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_custom_control,null,false);
        setContentView(binding.getRoot());

        initData();
    }

    private void initData() {
        binding.shb.setOnSwitchedListner(new SwitchHeaderButton.OnSwitchedListener() {
            @Override
            public void onSwitched(SwitchHeaderButton shb, int switchtype) {
                Toast.makeText(CustomControlActivity.this,"item"+switchtype,Toast.LENGTH_SHORT).show();
            }
        });
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int progress = binding.idProgressbar01.getProgress();
            int roundProgress = binding.idProgress02.getProgress();
            binding.idProgressbar01.setProgress(++progress);
            binding.idProgress02.setProgress(++roundProgress);
            if (progress >= 100) {
                mHandler.removeMessages(MSG_PROGRESS_UPDATE);
            }
            mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);
        }
    };

}
