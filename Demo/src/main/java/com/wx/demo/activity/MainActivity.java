package com.wx.demo.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.wx.demo.R;
import com.wx.demo.model.DataDetail;
import com.wx.demo.util.LogUtil;
import com.wx.demo.util.PreferenceUtils;
import com.wx.demo.view.CustomView;
import com.wx.demo.view.IconTreeItemHolder;
import com.wx.demo.view.SelectableHeaderHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends Activity implements TreeNode.TreeNodeClickListener {
    ViewGroup containerView;
    AndroidTreeView tView;
    GridLayout gridLayout;
    TreeNode root;

    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = TreeNode.root();
        root.setExpanded(true);

        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom, false);

        containerView = (ViewGroup) findViewById(R.id.tree_container);
        containerView.addView(tView.getView());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            gridLayout = (GridLayout) findViewById(R.id.gridLayout);
            gridLayout.setColumnCount(13);
            for (int i = 0; i < 13 * 13; i++) {
                CustomView customView = new CustomView(this);
                customView.init(i, 13);
                gridLayout.addView(customView);
            }
        }

        mQueue = Volley.newRequestQueue(this);

        requestData();

        PreferenceUtils.getInstance().init(this, "db");
//        PreferenceUtils.getInstance().saveObject("test", 1);
//        int test = PreferenceUtils.getInstance().getInt("test");
//        LogUtil.d(String.valueOf(test));
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

    private void clearRootNode() {
        if (root != null) {
            List<TreeNode> nodes = root.getChildren();
            LogUtil.d(String.valueOf(nodes.size()));
            while (nodes.size() > 0) {
                TreeNode node = nodes.get(nodes.size() - 1);
                tView.removeNode(node);
            }
        }
    }

    private void fillFolder(TreeNode folder, List<DataDetail> children) {
        for (int i = 0; i < children.size(); i++) {
            DataDetail detail = children.get(i);
            TreeNode node = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, detail));
            node.setViewHolder(new SelectableHeaderHolder(this));
            node.setClickListener(this);
            folder.addChild(node);
            if (detail.getChildren().size() > 0) {
                fillFolder(node, detail.getChildren());
            }
        }
    }

    private void addRootNode(List<DataDetail> list) {
        if (root != null) {
            for (DataDetail detail : list) {
                TreeNode s1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, detail));
                s1.setViewHolder(new SelectableHeaderHolder(this));
                s1.setClickListener(this);
                tView.addNode(root, s1);
                fillFolder(s1, detail.getChildren());
            }
        }
    }

    private void updateViews(JSONObject response) {
        try {
            Gson gson = new GsonBuilder().create();
            Type listType = new TypeToken<List<DataDetail>>() {
            }.getType();
            List<DataDetail> list = gson.fromJson((response.get("data")).toString(), listType);
            if (list.size() > 0) {
                clearRootNode();
                addRootNode(list);
                updateGrid(list.get(0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void requestData() {
        String url = "http://10.236.181.33:3005/list";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                updateViews(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                try {
                                    String responseBody = new String(error.networkResponse.data, "utf-8");
                                    JSONObject jsonObject = new JSONObject(responseBody);
                                    updateViews(jsonObject);
                                } catch (Exception e) {
                                    //Handle a malformed json response
                                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        mQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        try {
            IconTreeItemHolder.IconTreeItem data = (IconTreeItemHolder.IconTreeItem) value;
//            Toast.makeText(this, data.detail.getName(), Toast.LENGTH_SHORT).show();
            // 更新gridlayout里的网格数据
            updateGrid(data.detail);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateGrid(DataDetail data) {
        if (gridLayout != null) {
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                CustomView customView = (CustomView) gridLayout.getChildAt(i);
                customView.updateData(data.getInfo(), i);
            }
        }
    }
}
