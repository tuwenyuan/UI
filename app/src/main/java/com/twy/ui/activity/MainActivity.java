package com.twy.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.twy.ui.R;
import com.twy.ui.adpter.SubjectAdapter;
import com.twy.ui.databinding.ActivityMainBinding;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] items = {
            "pinned-selection-listview",
            "jbox2d",
            "透明状态栏效果1",
            "linePieView"
    };
    private ActivityMainBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        setContentView(dataBinding.getRoot());
        initData();
        initListener();
    }

    private void initData() {
        SubjectAdapter mAdapter = new SubjectAdapter(this,getLayoutInflater(), Arrays.asList(items));
        dataBinding.lv.setAdapter(mAdapter);
    }

    private void initListener() {
        dataBinding.lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                startActivity(new Intent(this,PinnedListViewSubjectActivity.class));
                break;
            case 1:
                startActivity(new Intent(this,JboxActivity.class));
                break;
            case 2:
                startActivity(new Intent(this,TransformStatusBarActivity.class));
                break;
            case 3:
                startActivity(new Intent(this,LinePieViewActivity.class));
                break;
        }
    }
}
