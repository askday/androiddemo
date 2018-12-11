package com.wx.demo.activity;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;

        import com.wx.demo.util.LogUtil;

/**
 * Created by wx on 16/7/28.
 */

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        LogUtil.d(String.valueOf(id));
    }
}
