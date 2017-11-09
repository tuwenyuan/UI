package com.twy.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.twy.ui.R;
import com.twy.ui.adpter.SubjectRvAdapter;
import com.twy.ui.databinding.ActivityMainBinding;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements SubjectRvAdapter.OnItemClickListener {

    private String[] items = {
            "pinned-selection-listview",
            "jbox2d",
            "透明状态栏效果1",
            "自定义饼图",
            "自定义控件集合",
            "ZXing二维码",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test"
    };
    private ActivityMainBinding dataBinding;
    private SubjectRvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        setContentView(dataBinding.getRoot());
        initData();
        initListener();
    }

    private void initData() {
        //SubjectAdapter mAdapter = new SubjectAdapter(this,getLayoutInflater(), Arrays.asList(items));
        mAdapter = new SubjectRvAdapter(this,getLayoutInflater(), Arrays.asList(items));

        dataBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.rv.setAdapter(mAdapter);
    }

    private void initListener() {
        //dataBinding.rv.setOnItemClickListener(this);
        mAdapter.setOnItemClickListener(this);

    }

    /*@Override
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
            case 4:
                startActivity(new Intent(this,CustomControlActivity.class));
                break;
        }
    }*/

    @Override
    public void onItemClick(View view, int position) {
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
            case 4:
                startActivity(new Intent(this,CustomControlActivity.class));
                break;
            case 5:
                startActivity(new Intent(this,ZXingActivity.class));
                break;
        }
    }
}
