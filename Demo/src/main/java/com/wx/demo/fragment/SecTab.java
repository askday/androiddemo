package com.wx.demo.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wx.demo.R;
import com.wx.demo.model.DeskDetail;
import com.wx.demo.model.DeskUser;
import com.wx.demo.util.LogUtil;
import com.wx.demo.util.VolleyUtil;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class SecTab extends Fragment implements View.OnClickListener {
    RelativeLayout mContainer;
    Button mBtnSelectDesk;
    ViewTreeObserver.OnGlobalLayoutListener lisenter;
    int mWidth = 0;
    int mHeight = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_sec_fragment, null);
        mContainer = (RelativeLayout) layout.findViewById(R.id.desk_container);
        mBtnSelectDesk = (Button) layout.findViewById(R.id.btn_select_desk);
        mBtnSelectDesk.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d("===fragment onStart===");
        initDesk();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("===fragment onResume===");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("===fragment onPause===");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("===fragment onDestroy===");
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            if (v.getTag() instanceof DeskUser) {
                DeskUser deskUser = (DeskUser) v.getTag();
                if (deskUser.getNo() == -1) {
                    // 荷官操作
                }

                AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(getContext());
                normalDialog.setMessage("你点击了" + deskUser.getName());
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                            }
                        });
                // 显示
                normalDialog.show();
            } else {
                // 选择牌桌
                requestDeskList();
            }
        }
    }

    /****************************以下是自定义函数********************************/

    private void initDesk() {
        final View root = getView();
        lisenter = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LogUtil.d(root.getWidth() + "|" + root.getHeight());
                mWidth = root.getWidth();
                mHeight = root.getHeight();
                root.getViewTreeObserver().removeOnGlobalLayoutListener(lisenter);
                getDeskData(0);

            }
        };
        root.getViewTreeObserver().addOnGlobalLayoutListener(lisenter);
    }

    private void getDeskData(int no) {
        try {
            JSONObject params = new JSONObject();
            params.put("no", no);
            VolleyUtil.getInstance().loadData("desk", params, new VolleyUtil.Listener() {
                @Override
                public void onSuccess(Object response) {
                    updateDeskView((JSONObject) response);
                }

                @Override
                public void onError(Object response) {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
        }
    }

    private void updateDeskView(JSONObject response) {
        try {
            Gson gson = new GsonBuilder().create();
            DeskDetail detail = gson.fromJson((response.get("data")).toString(), DeskDetail.class);
            mContainer.removeAllViews();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            int count = detail.getList().size();
            float radius = mHeight - 200;
            double startRus = Math.PI * 7 / 6;
            double diffRus = Math.PI / 6;
            int destUserWidth = mWidth / 8;
            int destUserHeight = mHeight / 3;
            for (int i = 0; i < count; i++) {
                DeskUser user = detail.getList().get(i);

                RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.layout_desk_user, null);
                relativeLayout.setBackgroundColor(Color.GREEN);

                TextView tvUserId = (TextView) relativeLayout.findViewById(R.id.tv_desk_user_id);
                tvUserId.setText(String.valueOf(user.getNo()));

                TextView tvUserName = (TextView) relativeLayout.findViewById(R.id.tv_desk_user_name);
                tvUserName.setText(user.getName());

                TextView tvUserMoney = (TextView) relativeLayout.findViewById(R.id.tv_desk_user_money);
                tvUserMoney.setText(String.valueOf(user.getMoney()));

                Button btn = (Button) relativeLayout.findViewById(R.id.btn_desk);
                btn.setTag(user);
                btn.setOnClickListener(this);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(destUserWidth, destUserHeight);
                layoutParams.leftMargin = (int) (mWidth / 2 + Math.cos(startRus + diffRus * i) * radius) - destUserWidth / 2;
                layoutParams.topMargin = (int) (mHeight - 200 + Math.sin(startRus + diffRus * i) * radius);
                LogUtil.d(layoutParams.leftMargin + "||" + layoutParams.topMargin);
                mContainer.addView(relativeLayout, layoutParams);
            }

            Button btn = new Button(getContext());
            DeskUser deskUser = new DeskUser();
            deskUser.setNo(-1);
            deskUser.setName("荷官");
            btn.setTag(deskUser);
            btn.setText("荷官");
            btn.setOnClickListener(this);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(destUserWidth, destUserHeight);
            layoutParams.leftMargin = mWidth / 2 - destUserWidth / 2;
            layoutParams.topMargin = mHeight - 200 - destUserHeight / 2;
            LogUtil.d(layoutParams.leftMargin + "||" + layoutParams.topMargin);
            mContainer.addView(btn, layoutParams);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void requestDeskList() {
        try {
            JSONObject params = new JSONObject();
            VolleyUtil.getInstance().loadData("desklist", params, new VolleyUtil.Listener() {
                @Override
                public void onSuccess(Object response) {
                    showDeskList((JSONObject) response);
                }

                @Override
                public void onError(Object response) {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showDeskList(JSONObject response) {
        try {
            Gson gson = new GsonBuilder().create();
            Type listType = new TypeToken<List<DeskDetail>>() {
            }.getType();
            final List<DeskDetail> list = gson.fromJson((response.get("data")).toString(), listType);

            AlertDialog.Builder listDialog =
                    new AlertDialog.Builder(getContext());
            listDialog.setTitle("所有牌桌");
            listDialog.setAdapter(new ListAdapter() {
                @Override
                public boolean areAllItemsEnabled() {
                    return false;
                }

                @Override
                public boolean isEnabled(int position) {
                    return false;
                }

                @Override
                public void registerDataSetObserver(DataSetObserver observer) {

                }

                @Override
                public void unregisterDataSetObserver(DataSetObserver observer) {

                }

                @Override
                public int getCount() {
                    return list.size();
                }

                @Override
                public Object getItem(int position) {
                    return list.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public boolean hasStableIds() {
                    return false;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.layout_dest_list, null);
                    TextView tv = (TextView) relativeLayout.findViewById(R.id.list_item_name);
                    tv.setText(String.valueOf(list.get(position).getDeskNo()));
                    return relativeLayout;
                }

                @Override
                public int getItemViewType(int position) {
                    return 1;
                }

                @Override
                public int getViewTypeCount() {
                    return 1;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "你点击了" + list.get(which).getDeskNo(), Toast.LENGTH_SHORT).show();
                }
            });
            listDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
