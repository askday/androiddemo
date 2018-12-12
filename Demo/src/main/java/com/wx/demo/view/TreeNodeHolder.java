package com.wx.demo.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.wx.demo.R;
import com.wx.demo.model.DataDetail;

/**
 * Created by Bogdan Melnychuk on 2/15/15.
 */

public class TreeNodeHolder extends TreeNode.BaseNodeViewHolder<DataDetail> {

    Boolean isLeaf = false;
    TextView tvName;

    PrintView arrowView;

    public TreeNodeHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, DataDetail value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (value.getChildren().size() == 0) {
            isLeaf = true;
            view = inflater.inflate(R.layout.layout_file_node, null, false);
            tvName = (TextView) view.findViewById(R.id.file_node_name);
            tvName.setText(value.getName());
        } else {
            isLeaf = false;
            view = inflater.inflate(R.layout.layout_dir_node, null, false);

            arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

            PrintView iconView = (PrintView) view.findViewById(R.id.icon);
            iconView.setIconText(context.getResources().getString(R.string.ic_folder));

            tvName = (TextView) view.findViewById(R.id.dir_node_name);
            tvName.setText(value.getName());
        }

        return view;
    }

    @Override
    public void toggle(boolean active) {
        if(isLeaf){

        }else{
            arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
        }
    }

    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
    }

}

