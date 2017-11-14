package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.twy.ui.R;
import com.twy.ui.base.BaseAplication;
import com.twy.ui.databinding.ActivitySwipeRefreshLayoutBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by twy on 2017/11/14.
 */

public class SwipeRefreshLayoutActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ActivitySwipeRefreshLayoutBinding binding;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Java", "Javascript", "C++", "Ruby", "Json",
            "HTML"));
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_swipe_refresh_layout, null, false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        binding.srl.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        binding.lv.setAdapter(mAdapter);

        binding.srl.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        BaseAplication.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
                mAdapter.notifyDataSetChanged();
                binding.srl.setRefreshing(false);
            }
        },2000);
    }
}
