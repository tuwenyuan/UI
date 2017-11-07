package twy.com.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import twy.com.ui.view.PinnedSectionListView;

/**
 * 创建者     涂文远
 * 创建时间   2016/8/23 22:13
 * 描述	      ${TODO}
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class PlvSubjectAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private static final int TITLE_TYPE = 1;
    private final Context mContext;
    private final List<Integer> mList;

    public PlvSubjectAdapter(Context context, List<Integer> list){
        mContext = context;
        mList = list;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType==TITLE_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int num = mList.get(position);
        if(num==1 ||num == 20||num==40||num==60||num==80||num==100){
            return TITLE_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(mContext);
        AbsListView.LayoutParams ll = new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setText(mList.get(position)+"");
        tv.setPadding(0,20,0,20);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(ll);
        if(getItemViewType(position)==TITLE_TYPE){
            tv.setBackgroundColor(Color.RED);
            tv.setTextColor(Color.WHITE);
        }
        return tv;
    }
}
