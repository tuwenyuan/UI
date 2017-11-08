package twy.com.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import twy.com.ui.R;
import twy.com.ui.databinding.ActivityLinePieViewBinding;

/**
 * Created by twy on 2017/11/8.
 */

public class LinePieViewActivity extends AppCompatActivity {

    private ActivityLinePieViewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_line_pie_view, null, false);
        setContentView(binding.getRoot());
        handerPieView();
    }

    private void handerPieView(){
        double[] data = new double[]{
                10000d,
                10000d,
                50d,
                50d,
                50d
        };
        int[] colors = new int[]{
                Color.parseColor("#ff0000"),
                Color.parseColor("#00ff00"),
                Color.parseColor("#0000ff"),
                Color.parseColor("#500000"),
                Color.parseColor("#005000")
        };
        List<String> name = new ArrayList<>();
        for(int i = 0;i<5;i++){
            name.add("item"+i);
            if(i==0){
                binding.tvOneIn.setText("item"+i);
                binding.vDailishouyiIn.setBackgroundColor(colors[i]);
            }else if(i==3){
                binding.tvFourIn.setText("item"+i);
                binding.vHuiyuanshouyiIn.setBackgroundColor(colors[i]);
            }else if(i==1){
                binding.tvTwoIn.setText("item"+i);
                binding.vDailifenhongIn.setBackgroundColor(colors[i]);
            }else if(i==2){
                binding.tvThreeIn.setText("item"+i);
                binding.vHuiyuanfenhongIn.setBackgroundColor(colors[i]);
            }else if(i==4){
                binding.tvFiveIn.setText("item"+i);
                binding.vOthersIn.setBackgroundColor(colors[i]);
            }
        }
        binding.lpv.setData(data,name.toArray(new String[name.size()]),colors);
    }
}
