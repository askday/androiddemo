package com.wx.demo.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.ViewGroup;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.wx.demo.R;
import com.wx.demo.util.LogUtil;
import com.wx.demo.view.CustomTreeNodeHolder;
import com.wx.demo.view.CustomView;

public class MainActivity extends Activity {
    ViewGroup containerView;
    AndroidTreeView tView;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_main);

        containerView = (ViewGroup) findViewById(R.id.tree_container);
        if (containerView != null) {
            TreeNode root = TreeNode.root();

            TreeNode child0 = new TreeNode("ChildNode0").setViewHolder(new CustomTreeNodeHolder(this));
            TreeNode child01 = new TreeNode("ChildNode01").setViewHolder(new CustomTreeNodeHolder(this));
            TreeNode child02 = new TreeNode("ChildNode02").setViewHolder(new CustomTreeNodeHolder(this));
            TreeNode child03 = new TreeNode("ChildNode03").setViewHolder(new CustomTreeNodeHolder(this));
            child0.addChildren(child01,child02,child03);

            TreeNode child1 = new TreeNode("ChildNode1").setViewHolder(new CustomTreeNodeHolder(this));
            TreeNode child11 = new TreeNode("ChildNode11").setViewHolder(new CustomTreeNodeHolder(this));
            TreeNode child12 = new TreeNode("ChildNode12").setViewHolder(new CustomTreeNodeHolder(this));
            TreeNode child13 = new TreeNode("ChildNode13").setViewHolder(new CustomTreeNodeHolder(this));
            child1.addChildren(child11,child12,child13);

            root.addChildren(child0, child1);

            tView = new AndroidTreeView(this, root);
            tView.setDefaultAnimation(true);
            tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom, true);
            containerView.addView(tView.getView());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            gridLayout = (GridLayout) findViewById(R.id.gridLayout);
            gridLayout.setColumnCount(13);
            for (int i = 0; i < 13 * 13; i++) {
                CustomView customView = new CustomView(this);
                customView.init(i, 13);
                gridLayout.addView(customView);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int width = newConfig.screenWidthDp;
        int height = newConfig.screenHeightDp;
        LogUtil.d(String.format("width:%d\theight:%d", width, height));
        if (gridLayout != null) {
            gridLayout.requestLayout();
        }
    }
}
