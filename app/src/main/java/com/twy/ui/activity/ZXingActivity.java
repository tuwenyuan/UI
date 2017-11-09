package com.twy.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityZXingBinding;
import com.twy.ui.utils.DialogUtil;
import com.twy.ui.zxing.QRCodeUtil;

import java.io.File;

/**
 * Created by twy on 2017/11/9.
 */

public class ZXingActivity extends AppCompatActivity{

    private ActivityZXingBinding binding;
    private View mDialogView;
    private FrameLayout mFl_loading;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_z_xing,null,false);
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDialog==null) {
                    mDialogView = View.inflate(ZXingActivity.this, R.layout.dialog_recommend_courteous, null);
                    ImageView iv_close = (ImageView) mDialogView.findViewById(R.id.iv_close);
                    ImageView iv_code = (ImageView) mDialogView.findViewById(R.id.iv_code);
                    mFl_loading = (FrameLayout) mDialogView.findViewById(R.id.fl_loading);
                    createChineseQRCode(iv_code);
                    iv_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                    mDialog = DialogUtil.getDialog(ZXingActivity.this, mDialogView);
                    mDialog.show();
                }else{
                    mDialog.show();
                }
            }
        });
    }

    private void createChineseQRCode(final ImageView iv_code) {
        final String shareUrl = "http://jl.xuecheyi.com/mobile/baoming.html";

        /*QRCodeEncoder.encodeQRCode(shareUrl, DimenUtils.dp2px(RecommendCourteousActivity.this, 170), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(final Bitmap bitmap) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv_code.setImageBitmap(bitmap);
                        mFl_loading.setVisibility(View.GONE);
                    }
                },10000);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(RecommendCourteousActivity.this, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
            }
        },mUserInfo.getAvatar());*/

        final String filePath = getFileRoot(ZXingActivity.this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";

        Bitmap logBitMap = null;

        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imageUri = "http://a.hiphotos.baidu.com/image/h%3D200/sign=63b47b6a0b23dd543e73a068e108b3df/80cb39dbb6fd526678b13b1aa918972bd4073621.jpg";
                boolean success = QRCodeUtil.createQRImage(shareUrl, 800, 800,
                        filePath,imageUri);

                if (success) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv_code.setImageBitmap(BitmapFactory.decodeFile(filePath));
                            mFl_loading.setVisibility(View.GONE);
                        }
                    });
                }else{
                    Toast.makeText(ZXingActivity.this, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
                    mFl_loading.setVisibility(View.GONE);
                }
            }
        }).start();
    }

    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
    }

}
