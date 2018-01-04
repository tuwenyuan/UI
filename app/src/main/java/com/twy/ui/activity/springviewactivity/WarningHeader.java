package com.twy.ui.activity.springviewactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liaoinstan.springview.container.BaseHeader;
import com.twy.ui.R;

/**
 * Created by liaoinstan on 2016/3/28.
 */
public class WarningHeader extends BaseHeader {
    private TextView textView;

    private int i = 0;

    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.header_warning, viewGroup, true);
        textView = (TextView) view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void onPreDrag(View rootView) {

    }

    @Override
    public void onDropAnim(View rootView, int dy) {

    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        i++;
        textView.setText("this is TextView " + i);
    }

    @Override
    public void onStartAnim() {

    }

    @Override
    public void onFinishAnim() {

    }
}
