package com.twy.ui.adpter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.twy.ui.R;
import com.twy.ui.databinding.ItemMainListBinding;

import java.util.List;

/**
 * Created by twy on 2017/11/8.
 */

public class SubjectRvAdapter extends RecyclerView.Adapter<SubjectRvAdapter.SubjectRvHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    private List<String> mList;

    public SubjectRvAdapter(Context context,LayoutInflater inflater, List<String> list){
        mContext = context;
        this.inflater = inflater;
        mList = list;
    }

    @Override
    public SubjectRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_main_list,parent,false);
        return new SubjectRvHolder(binding);
    }

    @Override
    public void onBindViewHolder(SubjectRvHolder holder, int position) {
        holder.binding.tvItemName.setText(mList.get(position));
        holder.binding.tvItemNum.setText(position+"");
        setPicByPostion(position,holder.binding.ivItemPic);
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class SubjectRvHolder extends RecyclerView.ViewHolder {

        public ItemMainListBinding binding;
        public SubjectRvHolder(ItemMainListBinding binding) {
            super(binding.getRoot());
            this.binding  = binding;
        }
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
