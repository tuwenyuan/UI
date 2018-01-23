package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityStyle1Binding;
import com.twy.ui.databinding.ItemMainListBinding;

/**
 * Created by twy on 2018/1/19.
 */

public class Style1Activity extends AppCompatActivity {

    private ActivityStyle1Binding binding;
    private MyAdapter adpter;
    private MyListAdapter ad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_style1,null,false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        adpter = new MyAdapter();
        binding.rv.setAdapter(adpter);

        /*ad = new MyListAdapter();
        binding.lv.setAdapter(ad);*/

    }

    public class MyListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemMainListBinding binding1;
            if(convertView==null){
                binding1 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_main_list, null, false);

            }else{
                binding1 = DataBindingUtil.getBinding(convertView);
            }
            return binding1.getRoot();
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH>{

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemMainListBinding binding1 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_main_list, null, false);
            return new VH(binding1.getRoot());
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {

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
