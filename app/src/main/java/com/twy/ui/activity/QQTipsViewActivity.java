package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cjy.tipsview.tipsview.TipsView;
import com.twy.ui.R;
import com.twy.ui.databinding.ActivityQqTipsviewBinding;
import com.twy.ui.databinding.ItemMessageBinding;

public class QQTipsViewActivity extends AppCompatActivity {

    private ActivityQqTipsviewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_qq_tipsview,null,false);
        setContentView(binding.getRoot());

        initData();
    }

    private void initData() {
        MyAdapter adapter = new MyAdapter();
        binding.lv.setAdapter(adapter);
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 10;
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
            final ItemMessageBinding dataBinding;
            if(convertView!=null){
                dataBinding = DataBindingUtil.getBinding(convertView);
            }else{
                dataBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_message,null,false);
            }
            TipsView.create(QQTipsViewActivity.this).attach(dataBinding.tvNum, new TipsView.DragListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onCancel() {
                    dataBinding.tvNum.setVisibility(View.VISIBLE);
                }
            });
            return dataBinding.getRoot();
        }
    }
}
