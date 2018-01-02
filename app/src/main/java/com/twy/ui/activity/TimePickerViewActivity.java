package com.twy.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.twy.timepickerviewlib.pickerview.TimePickerView;
import com.twy.ui.R;
import com.twy.ui.databinding.ActivityTimePickerViewBinding;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by twy on 2018/1/2.
 */

public class TimePickerViewActivity extends AppCompatActivity {

    private ActivityTimePickerViewBinding binding;
    private TimePickerView timePickerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_time_picker_view,null,false);
        initData();
        setContentView(binding.getRoot());
    }

    private void initData() {
        timePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH);
        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Calendar c=Calendar.getInstance();
                c.setTime(date);
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH) + 1;
                Toast.makeText(TimePickerViewActivity.this,c.toString(),Toast.LENGTH_SHORT).show();
                timePickerView.dismiss();
            }
        });
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerView.show();
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(timePickerView.isShowing()){
                timePickerView.dismiss();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

}
