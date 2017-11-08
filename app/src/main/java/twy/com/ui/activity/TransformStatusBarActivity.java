package twy.com.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import twy.com.ui.R;
import twy.com.ui.databinding.ActivityTransformStatusBarBinding;
import twy.com.ui.view.ListeningScrollView;

/**
 * Created by twy on 2017/11/8.
 */

public class TransformStatusBarActivity extends AppCompatActivity {

    private ActivityTransformStatusBarBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_transform_status_bar, null, false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        binding.sv.setScrollYViewListener(new ListeningScrollView.ScrollYListener() {
            @Override
            public void onScrollChanged(int y) {
                float ration = (y + 0f) / (binding.rlTop.getMeasuredHeight());
                //设置导航栏按钮的透明度
                setTitleSwitchAlpha(ration);
            }
        });
    }

    public void setTitleSwitchAlpha(float ration) {
        binding.navTrans2.setAlpha(ration);
        Log.i("twy", ration + "--------------");
        /*if(ration>1){

        }else{
            dataBinding.navTrans.setVisibility(View.VISIBLE);
            dataBinding.navTrans2.setVisibility(View.VISIBLE);
            dataBinding.navTrans2Left.setAlpha(1-ration);
            dataBinding.navTrans2Right.setAlpha(1-ration);
            dataBinding.navTrans2Home.setAlpha(1-ration);

            dataBinding.navTransLeft.setAlpha(ration);
            dataBinding.navTransRight.setAlpha(ration);
            //dataBinding.navTransHome.setAlpha(ration);
            dataBinding.vStatusBar1.setAlpha(ration);
        }*/
        if(ration>1)
            ration = 1;
        /*if (ration > 1) {
            dataBinding.vStatusBar.setVisibility(View.VISIBLE);
            ILog.e("比例值不能大于１");
        } else */if (ration > 0.5) {

            binding.navTrans.setVisibility(View.GONE);
            binding.navTrans2.setVisibility(View.VISIBLE);
            binding.vStatusBar.setVisibility(View.VISIBLE);

            //dataBinding.navTrans2Left.setAlpha((ration - 0.5f) * 2);
            //dataBinding.navTrans2Right.setAlpha((ration - 0.5f) * 2);
            binding.navTrans2Home.setAlpha((ration - 0.5f) * 2);
            binding.navTrans2.setAlpha((ration - 0.5f) * 2);
            binding.vStatusBar1.setAlpha((ration - 0.5f) * 2);
            //dataBinding.tvNum2.setAlpha((ration - 0.5f) * 2);

        } else if (ration >= 0) {
            binding.navTrans2.setVisibility(View.GONE);
            binding.navTrans.setVisibility(View.VISIBLE);
            binding.vStatusBar.setVisibility(View.GONE);
            //dataBinding.navTransLeft.setAlpha((0.5f - ration) * 2);
            //dataBinding.navTransRight.setAlpha((0.5f - ration) * 2);
            binding.vStatusBar1.setAlpha(0);
            //dataBinding.tvNum.setAlpha((0.5f - ration) * 2);
        }
    }

}
