package com.handmark.pulltorefresh.library.expand;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ListView;

public class BaseListView extends ListView {

	public BaseListView(Context context) {
		super(context);
		if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
			this.setOverScrollMode(View.OVER_SCROLL_NEVER);
		}
	}
}
