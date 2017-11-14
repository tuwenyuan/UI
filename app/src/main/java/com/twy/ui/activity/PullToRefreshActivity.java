package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.handmark.pulltorefresh.library.expand.BaseListView;
import com.handmark.pulltorefresh.library.expand.PullToRefreshBase;
import com.twy.ui.R;
import com.twy.ui.adpter.ComonAdapter;
import com.twy.ui.base.BaseAplication;
import com.twy.ui.databinding.ActivityPullToRefreshBinding;
import com.twy.ui.databinding.ItemPtrDataBinding;
import com.twy.ui.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twy on 2017/11/14.
 */

public class PullToRefreshActivity extends AppCompatActivity implements PullToRefreshBase.OnRefreshListener<BaseListView> {

    private ActivityPullToRefreshBinding binding;
    private ComonAdapter<String> adapter;
    private List<String> list = new ArrayList<>();
    private int pageNum = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pull_to_refresh, null, false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        binding.lv.getRefreshableView().setDividerHeight(0);
        binding.lv.setOnRefreshListener(this);
        binding.lv.setPullLoadEnabled(true);
        binding.lv.setScrollLoadEnabled(true);
        adapter = new ComonAdapter<>(getLayoutInflater(), R.layout.item_ptr_data, -1, new ComonAdapter.DataBindingListener<String, ItemPtrDataBinding>() {
            @Override
            public void getBackBinding(String data, ItemPtrDataBinding viewDataBinding) {

            }
        });
        binding.lv.getRefreshableView().setAdapter(adapter);
        binding.lv.doPullRefreshing(true,200);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<BaseListView> refreshView) {
        binding.lv.setHasMoreData(true);
        binding.lv.setPullLoadEnabled(true);
        pageNum = 1;
        loadNetData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<BaseListView> refreshView) {
        pageNum++;
        loadNetData();
    }

    private void loadNetData(){
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add("" + 60 * 1000);
        }
        BaseAplication.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.lv.setLastUpdatedLabel(StringUtil.getCurrentLocalDate());
                if(pageNum==1) {
                    adapter.setDatas(list);
                    binding.lv.onPullDownRefreshComplete();
                }else {
                    adapter.addMoreData(list);
                    binding.lv.onPullUpRefreshComplete();
                }

                if(adapter.list.size()>=30){
                    binding.lv.setHasMoreData(false,false);
                    binding.lv.setPullLoadEnabled(false);
                }
            }
        }, 500);
    }
}
