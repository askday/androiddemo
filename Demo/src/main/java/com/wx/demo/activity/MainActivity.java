package com.wx.demo.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.wx.demo.R;
import com.wx.demo.model.DataDetail;
import com.wx.demo.model.GridItem;
import com.wx.demo.util.CacheUtil;
import com.wx.demo.util.LogUtil;
import com.wx.demo.util.PreferenceUtils;
import com.wx.demo.util.ToolUtil;
import com.wx.demo.util.VolleyUtil;
import com.wx.demo.view.CustomView;
import com.wx.demo.view.TreeNodeHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends Activity implements TreeNode.TreeNodeClickListener {
    ViewGroup containerView;
    AndroidTreeView tView;
    GridLayout gridLayout;
    TreeNode root;

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

        Button refreshBtn= (Button) findViewById(R.id.btn_refresh_tree);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestTreeData();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            gridLayout = (GridLayout) findViewById(R.id.gridLayout);
            gridLayout.setColumnCount(13);
            for (int i = 0; i < 13 * 13; i++) {
                CustomView customView = new CustomView(this);
                customView.init(i, 13);
                gridLayout.addView(customView);
            }
        }
        // 初始化缓存
        PreferenceUtils.getInstance().init(this, "db");
        // 初始化网络
        VolleyUtil.getInstance().init(this);
        // 初始化
        getTreeData();
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

    @Override
    public void onClick(TreeNode node, Object value) {
        try {
            DataDetail data = (DataDetail) value;
            //Toast.makeText(this, data.detail.getName(), Toast.LENGTH_SHORT).show();
            // 更新gridlayout里的网格数据,紧在最终的子节点更新数据
            if (data != null && data.getChildren().size() == 0) {
                updateGridData(data.getCategory());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /****************************以下是自定义函数********************************/

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
            TreeNode node = new TreeNode(detail);
            node.setViewHolder(new TreeNodeHolder(this));
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
                TreeNode s1 = new TreeNode(detail);
                s1.setViewHolder(new TreeNodeHolder(this));
                s1.setClickListener(this);
                tView.addNode(root, s1);
                fillFolder(s1, detail.getChildren());
            }
        }
    }

    private void updateTreeViews(JSONObject response) {
        try {
            Gson gson = new GsonBuilder().create();
            Type listType = new TypeToken<List<DataDetail>>() {
            }.getType();
            List<DataDetail> list = gson.fromJson((response.get("data")).toString(), listType);
            if (list.size() > 0) {
                clearRootNode();
                addRootNode(list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 24小时才更新一次数据
     * 否则如果本地有缓存使用缓存数据
     */
    private void getTreeData() {
        try {
            long current = System.currentTimeMillis();
            long cacheRequestTreeTime = PreferenceUtils.getInstance().getLong(CacheUtil.kLastRequestTreeTime, current);

            boolean isSameDay = ToolUtil.isSameDay(cacheRequestTreeTime, current);
            if (isSameDay) {
                requestTreeData();
            } else {
                String cacheTreeData = PreferenceUtils.getInstance().getString(CacheUtil.kTreeData);
                if (cacheTreeData != null || cacheTreeData.length() > 0) {
                    JSONObject jsonObject = new JSONObject(cacheTreeData);
                    updateTreeViews(jsonObject);
                } else {
                    requestTreeData();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void requestTreeData() {
        LogUtil.d("=======do list request======");
        VolleyUtil.getInstance().loadData("list", new VolleyUtil.Listener<Object>() {
            @Override
            public void onSuccess(Object resObj) {
                LogUtil.d("=======request success======");
                JSONObject jsonObject = (JSONObject) resObj;
                long current = System.currentTimeMillis();
                PreferenceUtils.getInstance().save(CacheUtil.kLastRequestTreeTime, current);
                PreferenceUtils.getInstance().save(CacheUtil.kTreeData, jsonObject.toString());
                updateTreeViews(jsonObject);
            }

            @Override
            public void onError(Object errObj) {
                LogUtil.d("=======request error======");
                VolleyError error = (VolleyError) errObj;
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject jsonObject = new JSONObject(responseBody);
                    updateTreeViews(jsonObject);
                } catch (Exception e) {
                    //Handle a malformed json response
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateGridViews(JSONObject jsonObject) {
        if (gridLayout != null) {
            try {
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<List<GridItem>>() {
                }.getType();
                List<GridItem> list = gson.fromJson((jsonObject.get("data")).toString(), listType);
                if (list.size() > 0) {
                    for (int i = 0; i < gridLayout.getChildCount(); i++) {
                        CustomView customView = (CustomView) gridLayout.getChildAt(i);
                        customView.updateData(list, i);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void requestGridData(final int category) {
        try {
            LogUtil.d("=======do grid request======");
            JSONObject params = new JSONObject();
            params.put("category", category);
            VolleyUtil.getInstance().loadData("grid", params, new VolleyUtil.Listener<Object>() {
                @Override
                public void onSuccess(Object resObj) {
                    LogUtil.d("=======request success======");
                    JSONObject jsonObject = (JSONObject) resObj;
                    PreferenceUtils.getInstance().save(CacheUtil.kGridData + category, jsonObject.toString());
                    updateGridViews(jsonObject);
                }

                @Override
                public void onError(Object errObj) {
                    LogUtil.d("=======request error======");
                    VolleyError error = (VolleyError) errObj;
                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        updateGridViews(jsonObject);
                    } catch (Exception e) {
                        //Handle a malformed json response
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
        }
    }

    /**
     * 点击树的叶子结点，显示右侧的grid信息
     * 如果本地有缓存就用缓存，否则进行网络请求
     */
    private void updateGridData(int category) {
        LogUtil.d("==========updateGridData==========");
        // 先检测本地缓存是否存在
        String cacheGridData = PreferenceUtils.getInstance().getString(CacheUtil.kGridData + category);
        if (cacheGridData != null && cacheGridData.length() > 0) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(cacheGridData);
                updateGridViews(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            requestGridData(category);
        }

    }

}
