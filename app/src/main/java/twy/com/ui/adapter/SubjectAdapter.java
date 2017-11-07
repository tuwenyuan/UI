package twy.com.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import twy.com.ui.R;
import twy.com.ui.databinding.ItemMainListBinding;

/**
 * 创建者     涂文远
 * 创建时间   2016/8/23 21:48
 * 描述	      ${TODO}
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class SubjectAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<String> mList;
    private LayoutInflater inflater;

    public SubjectAdapter(Context context,LayoutInflater inflater, List<String> list){
        mContext = context;
        this.inflater = inflater;
        mList = list;
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
        ItemMainListBinding binding;
        if(convertView==null){
            binding = DataBindingUtil.inflate(inflater,R.layout.item_main_list,parent,false);
        }else{
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.tvItemName.setText(mList.get(position));
        binding.tvItemNum.setText(position+"");
        setPicByPostion(position,binding.ivItemPic);
        return binding.getRoot();
    }

    private void setPicByPostion(int postion,ImageView imageView){
        switch (postion){
            case 0:
                Glide.with(mContext).load(R.mipmap.pinned_selection_listview).asGif().into(imageView);
                break;
            default:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }
}
