package com.twy.ui.runtime_permissions_check;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.twy.ui.R;
import com.twy.ui.runtime_permissions_check.imagloader.FrescoImageLoader;
import com.twy.ui.runtime_permissions_check.imagloader.GlideImageLoader;
import com.twy.ui.runtime_permissions_check.imagloader.PicassoImageLoader;
import com.twy.ui.runtime_permissions_check.imagloader.UILImageLoader;
import com.twy.ui.runtime_permissions_check.imagloader.XUtilsImageLoader;
import com.twy.ui.utils.ImageCompress;



import cc.dagger.photopicker.PhotoPicker;
import cc.dagger.photopicker.picker.Load;
import cc.dagger.photopicker.picker.PhotoFilter;
import cc.dagger.photopicker.picker.PhotoSelectBuilder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 创建者     涂文远
 * 创建时间   2016/9/17 11:08
 * 描述	      ${TODO}
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class RuntimePermissionsActivity extends AppCompatActivity {
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    private TextView mResultText;
    private RadioGroup mChoiceMode, mShowCamera;
    private EditText mRequestNum;
    private EditText mRequestColumns;
    private LinearLayout pick_size_layout;
    private Spinner spinner_imageloader;

    public static final String[] IMAGELODERS = {
            "Picasso",
            "Glide",
            "Fresco",
            "Universal-Image-Loader",
            "Xutils3"
    };

    private ArrayList<String> mSelectPath;
    private File cameraFile;
    private File compressFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permissions);

        mResultText = (TextView) findViewById(R.id.result);
        mChoiceMode = (RadioGroup) findViewById(R.id.choice_mode);
        mShowCamera = (RadioGroup) findViewById(R.id.show_camera);
        mRequestNum = (EditText) findViewById(R.id.request_num);
        mRequestColumns = (EditText) findViewById(R.id.edit_colums);
        pick_size_layout = (LinearLayout) findViewById(R.id.pick_size_layout);
        spinner_imageloader = (Spinner) findViewById(R.id.spinner_imageloader);

        mChoiceMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId != R.id.multi) {
                    mRequestNum.setText("");
                }
                pick_size_layout.setVisibility(checkedId == R.id.multi ? View.VISIBLE : View.GONE);
            }
        });

        ArrayAdapter<String> loadersAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, IMAGELODERS);
        loadersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_imageloader.setAdapter(loadersAdapter);

        View button = findViewById(R.id.button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickImage();
                }
            });
        }
    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            initImageLoader(spinner_imageloader.getSelectedItem().toString());
            boolean showCamera = mShowCamera.getCheckedRadioButtonId() == R.id.show;
            int maxNum = 9;

            if (!TextUtils.isEmpty(mRequestNum.getText())) {
                try {
                    maxNum = Integer.valueOf(mRequestNum.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            int columns = 3;
            if (!TextUtils.isEmpty(mRequestColumns.getText().toString())){
                columns = Integer.parseInt(mRequestColumns.getText().toString());
            }

            Load load = PhotoPicker.load()
                    .showCamera(showCamera)
                    .filter(PhotoFilter.build().showGif(false).minSize(2 * 1024))
                    .gridColumns(columns);

            PhotoSelectBuilder builder;

            if (mChoiceMode.getCheckedRadioButtonId() == R.id.single) {
                builder = load.single();
            } else {
                builder = load.multi().maxPickSize(maxNum).selectedPaths(mSelectPath);
            }
            builder.start(RuntimePermissionsActivity.this);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(RuntimePermissionsActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.permission_dialog_cancel, null)
                    .create().show();
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//增加了写入文件的权限申请要不然压缩图片会失败
            new AlertDialog.Builder(this)
                    .setTitle(R.string.permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(RuntimePermissionsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.permission_dialog_cancel, null)
                    .create().show();
        }else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoPicker.REQUEST_SELECTED) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(PhotoPicker.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");

                }
                mResultText.setText(sb.toString());
                //对选择的图片处理  注意文件读写权限要动态申请
//                cameraFile = new File(sb.toString().split("\n")[0]);
//                //showLoading(true);
//                if(cameraFile.exists()){
//                    String compressAvatar = null;
//                    try {
//                        long length = cameraFile.length();
//                        //压缩图片
//                        compressAvatar = ImageCompress.compressAvatar(cameraFile.getAbsolutePath(), Environment.getExternalStorageDirectory().getPath()+"/.image/head.png");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    compressFile = new File(compressAvatar);
//                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), compressFile);
//                    Map<String, RequestBody> map = new HashMap<>();
//                    map.put("iconFile\"; filename=\""+ compressFile.getName()+"",requestBody);
//                    //上传图片
//                    uploadUserUserHeadPic(map);
//                }else {
//                    //ToastUtil.showMyToast("此图片不支持修改功能。");
//                    //hideLoding();
//                }

            }
        }
    }

    private void uploadUserUserHeadPic(Map<String, RequestBody> map) {
        /*@Multipart
    @POST("i/member/info/uploadMemberIcon")
    Observable<CommonBean> uploadMemberIcon(@PartMap Map<String, RequestBody> params);*/
        //上传完图片回显
    }

    private void initImageLoader(String selectedItem){
        switch (selectedItem){
            case "Picasso":
                PhotoPicker.init(new PicassoImageLoader(), null);
                break;
            case "Glide":
                PhotoPicker.init(new GlideImageLoader(), null);
                break;
            case "Fresco":
                PhotoPicker.init(new FrescoImageLoader(), null);
                break;
            case "Universal-Image-Loader":
                PhotoPicker.init(new UILImageLoader(), null);
                break;
            case "Xutils3":
                PhotoPicker.init(new XUtilsImageLoader(), null);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
