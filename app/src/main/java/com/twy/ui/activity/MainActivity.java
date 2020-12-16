package com.twy.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.twy.ui.R;
import com.twy.ui.activity.springviewactivity.SpringViewActivity;
import com.twy.ui.adpter.SubjectRvAdapter;
import com.twy.ui.databinding.ActivityMainBinding;
import com.twy.ui.runtime_permissions_check.RuntimePermissionsActivity;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements SubjectRvAdapter.OnItemClickListener {

    private String[] items = {
            "pinned-selection-listview",
            "jbox2d",
            "透明状态栏效果1",
            "自定义饼图",
            "自定义控件集合",
            "ZXing二维码",
            "android中转场动画",
            "扩展pull_to_Refresh自动加载更多及自动下拉刷新功能",
            "SwipeRefreshLayout",
            "SwipeRefreshLayout扩展加载更多",
            "厨具窗口设计",
            "100%屏幕适配",
            "新手引导",
            "tablayout实现带图片",
            "仿ios选择时间控件",
            "富文本",
            "SpringView",
            "加载长图",
            "Design特效1",
            "Design特效2",
            "Design特效3",
            "仿京东淘宝商品详情",
            "android中转场动画2",
            "视频播放",
            "视频播放2",//原作者地址 https://github.com/lipangit/JiaoZiVideoPlayer
            "引导页",
            "photoPicker",
            "QQ气泡拖拽效果",
            "访地图标记GuideMap",
            "变色圆环进度条",
            "签名"
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
            case 6:
                startActivity(new Intent(this,AnimeActivity.class));
                break;
            case 7:
                startActivity(new Intent(this,PullToRefreshActivity.class));
                break;
            case 8:
                startActivity(new Intent(this,SwipeRefreshLayoutActivity.class));
                break;
            case 9:
                startActivity(new Intent(this,SwipeRefreshLayoutExpendActivity.class));
                break;
            case 10:
                startActivity(new Intent(this,PicStyleActivity.class));
                break;
            case 11:
                startActivity(new Intent(this,AdapterScreenActivity.class));
                break;
            case 12:
                startActivity(new Intent(this,EasyGuideViewActivity.class));
                break;
            case 13:
                startActivity(new Intent(this,TabLayoutImgActivity.class));
                break;
            case 14:
                startActivity(new Intent(this,TimePickerViewActivity.class));
                break;
            case 15:
                startActivity(new Intent(this,SpannableStringActivity.class));
                break;
            case 16:
                startActivity(new Intent(this,SpringViewActivity.class));
                break;
            case 17:
                startActivity(new Intent(this,BigImgActivity.class));
                break;
            case 18:
                startActivity(new Intent(this,Style1Activity.class));
                break;
            case 19:
                startActivity(new Intent(this,Style2Activity.class));
                break;
            case 20:
                startActivity(new Intent(this,Style3Activity.class));
                break;
            case 21:
                startActivity(new Intent(this,DragScrollDetailActivity.class));
                break;
            case 22:
                startActivity(new Intent(this,Anime2Activity.class));
                break;
            case 23:
                startActivity(new Intent(this,VideoActivity.class));
                break;
            case 24:
                startActivity(new Intent(this,Video2Activity.class));
                break;
            case 25:
                startActivity(new Intent(this,SplashActivity.class));
                break;
            case 26:
                startActivity(new Intent(this,RuntimePermissionsActivity.class));
                break;
            case 27:
                startActivity(new Intent(this,QQTipsViewActivity.class));
                break;
            case 28:
                startActivity(new Intent(this,GuideActivity.class));
                break;
            case 29:
                startActivity(new Intent(this,ProgressViewActivity.class));
                break;
            case 30:
                startActivity(new Intent(this,SignatureActivity.class));
                break;
        }
    }
}
