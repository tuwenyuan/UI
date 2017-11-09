package com.twy.ui.zxing;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 
 * @ClassName: DimenUtils
 * @Description: TODO
 * @author: Administrator
 * @date: 2015-12-15 上午9:35:02
 */
public class DimenUtils {
	/**
	 * dp 转 px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, int dp) {

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int dpi = metrics.densityDpi;

		return (int) (dp * (dpi / 160f) + 0.5f);
	}

	/**
	 * px 转 dp
	 * 
	 * @param context
	 * @param px
	 * @return
	 */
	public static int px2dp(Context context, int px) {

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();

		int dpi = metrics.densityDpi;
		return (int) (px * 160f / dpi + 0.5f);
	}

	//获取颜色值
	public static ColorStateList getColorStateList(Context context, int id) {
		ColorStateList csl3 = (ColorStateList) context.getResources()
				.getColorStateList(id);
		return csl3;
	}
	
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	public static Point getScreenResolution(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point screenResolution = new Point();
		if (android.os.Build.VERSION.SDK_INT >= 13) {
			display.getSize(screenResolution);
		} else {
			screenResolution.set(display.getWidth(), display.getHeight());
		}
		return screenResolution;
	}
}
