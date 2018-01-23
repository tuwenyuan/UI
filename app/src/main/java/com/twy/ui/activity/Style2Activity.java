package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.twy.ui.R;
import com.twy.ui.databinding.ItemMainListBinding;

/**
 * Created by twy on 2018/1/23.
 */

public class Style2Activity extends AppCompatActivity{

    //private ActivityStyle2Binding binding;
    private MyAdapter adpter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_style2,null,false);
        //setContentView(binding.getRoot());
        setContentView(R.layout.activity_style2);
        initData();
    }

    private void initData() {
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adpter = new MyAdapter();
        rv.setAdapter(adpter);
    }

    public class MyAdapter extends RecyclerView.Adapter<Style2Activity.MyAdapter.VH>{

        @Override
        public Style2Activity.MyAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemMainListBinding binding1 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_main_list, null, false);
            return new Style2Activity.MyAdapter.VH(binding1.getRoot());
        }

        @Override
        public void onBindViewHolder(Style2Activity.MyAdapter.VH holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 30;
        }

        public class VH extends RecyclerView.ViewHolder{

            public VH(View itemView) {
                super(itemView);
            }
        }
    }
}
