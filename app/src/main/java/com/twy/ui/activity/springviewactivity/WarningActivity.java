package com.twy.ui.activity.springviewactivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.liaoinstan.springview.utils.DensityUtil;
import com.liaoinstan.springview.widget.SpringView;
import com.twy.ui.R;

public class WarningActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView textView;
    private SpringView springView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((RadioGroup) findViewById(R.id.group_text)).setOnCheckedChangeListener(this);

        springView = (SpringView) findViewById(R.id.springview);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        springView.setHeader(new WarningHeader());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.setwrap:
                textView = (TextView) springView.getHeaderView().findViewById(R.id.textView);
                textView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                break;
            case R.id.sethard:
                textView = (TextView) springView.getHeaderView().findViewById(R.id.textView);
                textView.getLayoutParams().width = DensityUtil.dp2px(120);
                break;
        }
    }
}
