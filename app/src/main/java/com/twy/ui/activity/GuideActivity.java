package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityGuideBinding;
import com.twy.ui.view.guide.MapContainer;
import com.twy.ui.view.guide.Marker;

import java.util.ArrayList;

/*
 * Created by twy on 12/16/20
 */
public class GuideActivity extends AppCompatActivity {
    ActivityGuideBinding binding;
    ArrayList<Marker> mMarkers;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_guide, null, false);
        setContentView(binding.getRoot());
        initData();
        initListener();
    }

    private void initListener() {
        //这里用女神赵丽颖的照片作地图~~
        binding.mc.getMapView().setImageResource(R.mipmap.test);
        mMarkers = new ArrayList<>();
        mMarkers.add(new Marker(0.1f, 0.2f, R.mipmap.location));
        mMarkers.add(new Marker(0.3f, 0.7f, R.mipmap.location));
        mMarkers.add(new Marker(0.3f, 0.3f, R.mipmap.location));
        mMarkers.add(new Marker(0.2f, 0.4f, R.mipmap.location));
        mMarkers.add(new Marker(0.8f, 0.4f, R.mipmap.location));
        mMarkers.add(new Marker(0.5f, 0.6f, R.mipmap.location));
        mMarkers.add(new Marker(0.8f, 0.8f, R.mipmap.location));
        binding.mc.setMarkers(mMarkers);

    }

    private void initData() {
        binding.mc.setOnMarkerClickListner(new MapContainer.OnMarkerClickListner() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(GuideActivity.this, "你点击了第" + position + "个marker", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
