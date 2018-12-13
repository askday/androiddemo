package com.wx.demo.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.wx.demo.R;
import com.wx.demo.fragment.FirstTab;
import com.wx.demo.fragment.SecTab;
import com.wx.demo.util.LogUtil;
import com.wx.demo.util.PreferenceUtils;
import com.wx.demo.util.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Titles
    private static final int[] mTitles = {R.string.first_tab, R.string.sec_tab};

    @SeleIcons
    private static final int[] mSeleIcons = {R.mipmap.tab1_selected, R.mipmap.tab2_selected};

    @NorIcons
    private static final int[] mNormalIcons = {R.mipmap.tab1_normal, R.mipmap.tab2_normal};

    private List<Fragment> list = new ArrayList<>();
    private ViewPager mPager;
    private JPTabBar mTabbar;
    private FirstTab mTab1;
    private SecTab mTab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // 初始化缓存
        PreferenceUtils.getInstance().init(this, "db");
        // 初始化网络
        VolleyUtil.getInstance().init(this);
    }

    private void initTab() {
        mTabbar = (JPTabBar) findViewById(R.id.tabbar);
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mTab1 = new FirstTab();
        mTab2 = new SecTab();
        list.add(mTab1);
        list.add(mTab2);
        mTabbar.setGradientEnable(true);
        mTabbar.setPageAnimateEnable(true);
//        mTabbar.setTabListener(this);
        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
        });
        mTabbar.setContainer(mPager);
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
//        if (gridLayout != null) {
//            gridLayout.requestLayout();
//        }
    }

}
