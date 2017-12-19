package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityTabLayoutImgBinding;
import com.twy.ui.fragment.CommFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twy on 2017/12/19.
 */

public class TabLayoutImgActivity extends AppCompatActivity{

    private ActivityTabLayoutImgBinding binding;
    private ArrayList<String> titles;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_tab_layout_img,null,false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add("消费收入");
        titles.add("伙伴");
        fragments = new ArrayList<>();
        fragments.add(new CommFragment());
        fragments.add(new CommFragment());

        MyAdapater adapater = new MyAdapater(getSupportFragmentManager(), fragments, titles);
        binding.vp.setAdapter(adapater);
        binding.tab.setupWithViewPager(binding.vp);

        setupTabIcons();
        binding.vp.setCurrentItem(1);
        binding.vp.setCurrentItem(0);//直接设置0的话竟然不起作用,好吧.就这样迂回一下吧

    }

    private void setupTabIcons() {
        binding.tab.getTabAt(0).setCustomView(getTabView(0));
        binding.tab.getTabAt(1).setCustomView(getTabView(1));
    }

    private int[] tabIcons = {
            R.drawable.selector_tab1,
            R.drawable.selector_tab2,
    };

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(titles.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }


    private class MyAdapater extends FragmentPagerAdapter{

        private List<Fragment> fragments;
        private List<String> titles;

        public MyAdapater(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
