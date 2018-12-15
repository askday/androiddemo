package com.wx.demo.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wx.demo.R;
import com.wx.demo.model.DeskDetail;

import java.util.List;

public class DeskListAdapter implements ListAdapter {
    Context context;
    List<DeskDetail> list;

    public DeskListAdapter(Context context, List<DeskDetail> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (vh == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.layout_dest_list, null);
            vh.mText = (TextView) view.findViewById(R.id.list_item_name);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.mText.setText(String.valueOf(list.get(i).getDeskNo()));
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    class ViewHolder {
        private TextView mText;
    }
}
