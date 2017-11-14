package com.twy.ui.adpter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twy on 2017/11/14.
 */

public class ComonAdapter<T> extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutId;
    private int variableId;
    public List<T> list = new ArrayList<>();
    private DataBindingListener listener;
    public ComonAdapter(LayoutInflater inflater, int layoutId, int variableId,DataBindingListener listener){
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setDatas(List<T> list){
        if(list!=null && list.size()>0) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addMoreData(List<T> list){
        if(list!=null && list.size()>0){
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding binding;
        if(convertView==null){
            binding = DataBindingUtil.inflate(inflater,layoutId,parent,false);
        }else{
            binding = DataBindingUtil.getBinding(convertView);
        }
        if(variableId>-1){
            binding.setVariable(variableId,list.get(position));
        }
        if(listener!=null){
            listener.getBackBinding(list.get(position),binding);
        }
        return binding.getRoot();
    }

    public interface DataBindingListener<T,B extends ViewDataBinding>{
        void getBackBinding(T t, B b);
    }

}
