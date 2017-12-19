package com.twy.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twy.ui.R;
import com.twy.ui.databinding.FragmentCommBinding;

/**
 * Created by twy on 2017/12/19.
 */

public class CommFragment extends Fragment {

    private FragmentCommBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comm,null,false);
        return binding.getRoot();
    }
}
