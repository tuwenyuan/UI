package com.twy.ui.activity;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.twy.ui.R;
import com.twy.ui.base.BaseAplication;
import com.twy.ui.databinding.ActivitySignatureBinding;
import com.twy.ui.listener.IPermissionsListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by twy on 12/16/20
 */
public class SignatureActivity extends AppCompatActivity {
    ActivitySignatureBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_signature, null, false);
        setContentView(binding.getRoot());
        initListener();
    }

    private void initListener() {
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = new ArrayList<String>();
                list.add("android.permission.WRITE_EXTERNAL_STORAGE");
                list.add("android.permission.READ_EXTERNAL_STORAGE");
                startRequestPermission(list, new IPermissionsListener() {
                    @Override
                    public void permissionsOnSuccess() {
                        try {
                            binding.vSignature.save(BaseAplication.getInstance().getCacheDir().getAbsolutePath() + "/sign.png");
                            Toast.makeText(SignatureActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            //image();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(this@SignatureActivity,"保存成功",Toast.LENGTH_SHORT).show()

                    }
                });
            }
        });
    }

    private IPermissionsListener permissionListener;
    public void startRequestPermission(List<String> permissions, IPermissionsListener listener){
        permissionListener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ) {
            List<String> nPermissions = new ArrayList<>();
            for(String i : permissions){
                int checkSelfPermission = ContextCompat.checkSelfPermission(this,i);
                if(checkSelfPermission == PackageManager.PERMISSION_DENIED){
                    nPermissions.add(i);
                }
            }
            if(nPermissions.size()<=0){
                permissionListener.permissionsOnSuccess();
            }else {
                String[] strings = new String[nPermissions.size()];
                nPermissions.toArray(strings);
                ActivityCompat.requestPermissions(this,strings,100);
            }
        }else{
            permissionListener.permissionsOnSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 100){
            boolean flag = true;
            for(int i : grantResults){
                if(i != PackageManager.PERMISSION_GRANTED){
                    flag = false;
                    break;
                }
            }
            if(flag){
                permissionListener.permissionsOnSuccess();
            }else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
