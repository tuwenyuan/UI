package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityStyle3Binding;

/**
 * Created by twy on 2018/1/23.
 */

public class Style3Activity extends AppCompatActivity {


    private ActivityStyle3Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_style3,null,false);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        binding.collapsingToolbarLayout.setTitle("DesignLibrarySample");
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        binding.collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        binding.iv.setImageResource(R.mipmap.ic_bg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_notifications) {
            Log.d("cylog","click menu");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
