package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.twy.ui.R;
import com.twy.ui.base.BaseAplication;
import com.twy.ui.databinding.ActivitySwipeRefreshLayoutExpendBinding;
import com.twy.ui.view.MyRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by twy on 2017/11/14.
 */

public class SwipeRefreshLayoutExpendActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MyRefreshLayout.OnLoadListener {

    private ActivitySwipeRefreshLayoutExpendBinding binding;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Java", "Javascript", "C++", "Ruby", "Json","Java", "Javascript", "C++", "Ruby", "Json","Java", "Javascript", "C++", "Ruby", "Json","HTML"));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_swipe_refresh_layout_expend, null, false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        binding.msrf.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mAdapter = new ArrayAdapter<String>(SwipeRefreshLayoutExpendActivity.this, android.R.layout.simple_list_item_1, mDatas);
        binding.lv.setAdapter(mAdapter);
        /*binding.msrf.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.msrf.setRefreshing(true);
                binding.msrf.setLoading(true);
                binding.msrf.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                        android.R.color.holo_orange_light, android.R.color.holo_red_light);

                //假设第一次数据请求回来了 执行下两句代码
                binding.msrf.setLoading(false);
                binding.msrf.setRefreshing(false);
                mAdapter = new ArrayAdapter<String>(SwipeRefreshLayoutExpendActivity.this, android.R.layout.simple_list_item_1, mDatas);
                binding.lv.setAdapter(mAdapter);
                binding.msrf.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });*/

        binding.msrf.setOnRefreshListener(this);
        binding.msrf.setOnLoadListener(this);
        binding.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SwipeRefreshLayoutExpendActivity.this,"点击了...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRefresh() {
        BaseAplication.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
                mAdapter.notifyDataSetChanged();
                binding.msrf.setRefreshing(false);
                binding.msrf.setLoading(false);
            }
        },2000);
    }

    @Override
    public void onLoad() {
        BaseAplication.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
                mAdapter.notifyDataSetChanged();
                binding.msrf.setRefreshing(false);
                binding.msrf.setLoading(false);
            }
        },2000);
    }
}
