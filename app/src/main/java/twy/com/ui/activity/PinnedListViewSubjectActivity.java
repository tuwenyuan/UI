package twy.com.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import twy.com.ui.R;
import twy.com.ui.adapter.PlvSubjectAdapter;
import twy.com.ui.databinding.ActivityPinnedListViewSubjectBinding;

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
