package com.twy.ui.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.twy.ui.R;


public class DialogUtil {

    public static Dialog getDialog(Context context, View dialogview) {
        final Dialog dialog = new Dialog(context, R.style.float_base);
        // View view = View.inflate(context, R.layout.custom_phone_dialog,
        // null);
        dialog.setContentView(dialogview);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = getScreenWidth(context) - dpToPx(context, 50);
        window.setGravity(Gravity.CENTER_VERTICAL);
        return dialog;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }

    public static int dpToPx(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dpValue * scale);
    }

}
