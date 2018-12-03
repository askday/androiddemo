package com.wx.demo.util;

import android.util.Log;

import com.wx.demo.BuildConfig;

public class LogUtil {
    private final static String Tag = "DEMO";

    public static void d(String log) {
        if (BuildConfig.DEBUG) {
            Log.d(Tag, log);
        }
    }
}
