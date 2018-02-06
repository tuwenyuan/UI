package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivitySplashBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twy on 2018/2/6.
 */

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private List<ImageView> mDatas = new ArrayList<ImageView>();
    private int grayPoints_dis = 0;//灰点之间的左间距

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_splash, null, false);
        setContentView(binding.getRoot());
        initData();
        initListener();
    }

    private void initListener() {
        binding.vGuidRedpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                grayPoints_dis = binding.llGuidGraypoints.getChildAt(1).getLeft()-binding.llGuidGraypoints.getChildAt(0).getLeft();
                binding.vGuidRedpoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        binding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) binding.vGuidRedpoint.getLayoutParams();

                //改变lp的left的值
                layoutParams.leftMargin = Math.round(grayPoints_dis * (position + positionOffset));

                //重新设置参数
                binding.vGuidRedpoint.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mDatas.size() - 1) {
                    binding.ivGuidExp.setVisibility(View.VISIBLE);
                } else {
                    binding.ivGuidExp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.ivGuidExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SplashActivity.this,"跳转至首页",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //1. 组织ViewPager的数据
        ImageView iv = null;
        for (int i = 0; i < 3; i++) {
            //新创建的iv对象
            iv = new ImageView(this);
            //设置模式:图片填充iv
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            //设置数据
            iv.setImageResource(R.mipmap.guide_page_1 + i);

            //添加到容器中
            mDatas.add(iv);


            //创建灰点
            View v_graypoint = new View(this);
            //设置背景
            v_graypoint.setBackgroundResource(R.drawable.graypoint);

            int dip = 10;
            int pix = dip2px(dip);
            //大小
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(pix, pix);// 10pix

            //如果不是第一个点 设置间距10
            if (i != 0) {
                lp.leftMargin = pix;
            }

            v_graypoint.setLayoutParams(lp);

            //添加点
            binding.llGuidGraypoints.addView(v_graypoint);
        }

        MyAdapter mAdapter = new MyAdapter();
        binding.vp.setAdapter(mAdapter);
    }

    public int dip2px(float var1) {
        float var2 = this.getResources().getDisplayMetrics().density;
        return (int)(var1 * var2 + 0.5F);
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mDatas.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //1. 获取数据
            ImageView imageView = mDatas.get(position);
            //2. 添加到ViewPager中
            container.addView(imageView);
            //3. 返回标记
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //移除view
            container.removeView((View) object);
        }
    }
}
