package com.twy.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.twy.ui.R;
import com.twy.ui.databinding.ActivityAnime2DetailBinding;

/**
 * Created by twy on 2018/1/26.
 */

public class Anime2DetailActivity extends AppCompatActivity {

    private ActivityAnime2DetailBinding binding;
    public static final String NAME_IMG_AVATAR = "imgAvatar";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_anime2_detail,null,false);
        setContentView(binding.getRoot());
        ViewCompat.setTransitionName(binding.iv, NAME_IMG_AVATAR);
    }

    public static void startWithTransitionAnimation(@NonNull Activity activity, @NonNull ImageView imgAvatar) {
        Intent intent = new Intent(activity, Anime2DetailActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imgAvatar, NAME_IMG_AVATAR);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
