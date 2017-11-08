package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twy.ui.R;
import com.twy.ui.adpter.PlvSubjectAdapter;
import com.twy.ui.databinding.ActivityPinnedListViewSubjectBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by twy on 2017/11/7.
 */

public class PinnedListViewSubjectActivity extends AppCompatActivity {

    private ActivityPinnedListViewSubjectBinding dataBinding;
    private List<Integer> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pinned_list_view_subject, null, false);
        setContentView(dataBinding.getRoot());
        initData();
    }

    private void initData() {
        for (int i = 0; i < 120; i++) {
            mList.add(i);
        }
        PlvSubjectAdapter mAdapter = new PlvSubjectAdapter(this, mList);
        dataBinding.plv.setAdapter(mAdapter);
        dataBinding.plv.setShadowVisible(true);
    }
}
