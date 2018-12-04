package com.wx.demo.util;


/**
 * Created by swx on 28/02/2017.
 * Mail: bjshenwenxing@netease.corp.com
 * Copyright (c) 2017 NetEase Spot Investment Platform.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.Map;

public class PreferenceUtils {
    protected static SharedPreferences prefs;
    protected static PreferenceUtils instance = new PreferenceUtils();
    protected WeakReference<Context> context = null;
    protected boolean mUseApply;

    protected PreferenceUtils() {
        mUseApply = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static PreferenceUtils getInstance() {
        return instance;
    }

    public void init(Context context, String name) {
        this.context = new WeakReference<>(context);
        if (name == null || name.isEmpty()) {
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
    }

    protected void checkPrefs() {
        if (prefs == null && context != null && context.get() != null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(context.get());
        }
    }

    public Map<String, ?> getAll() {
        checkPrefs();
        if (prefs != null) {
            return prefs.getAll();
        }
        return null;
    }

    public void saveObject(String key, Object value) {
        checkPrefs();
        if (prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            }
            if (mUseApply) {
                editor.apply();
            } else {
                editor.commit();
            }
        }
    }

    public void save(String key, String value) {
        checkPrefs();
        if (prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            if (mUseApply) {
                editor.apply();
            } else {
                editor.commit();
            }
        }
    }

    public void save(String key, int value) {
        checkPrefs();
        if (prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(key, value);
            if (mUseApply) {
                editor.apply();
            } else {
                editor.commit();
            }
        }
    }

    public void save(String key, long value) {
        checkPrefs();
        if (prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(key, value);
            if (mUseApply) {
                editor.apply();
            } else {
                editor.commit();
            }
        }
    }

    public void save(String key, float value) {
        checkPrefs();
        if (prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat(key, value);
            if (mUseApply) {
                editor.apply();
            } else {
                editor.commit();
            }
        }
    }

    public void save(String key, boolean value) {
        checkPrefs();
        if (prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(key, value);
            if (mUseApply) {
                editor.apply();
            } else {
                editor.commit();
            }
        }
    }

    public int getInt(String key) {
        checkPrefs();
        if (prefs != null) {
            return prefs.getInt(key, 0);
        } else {
            return 0;
        }
    }

    public int getInt(String key, int defaultValue) {
        checkPrefs();
        if (prefs != null) {
            return prefs.getInt(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public String getString(String key) {
        checkPrefs();
        if (prefs != null) {
            return prefs.getString(key, "");
        } else {
            return "";
        }
    }

    public boolean getBoolean(String key) {
        checkPrefs();
        if (prefs != null) {
            return prefs.getBoolean(key, false);
        } else {
            return false;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        checkPrefs();
        if (prefs != null) {
            return prefs.getBoolean(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    public float getFloat(String key, float defaultValue) {
        checkPrefs();
        if (prefs != null) {
            return prefs.getFloat(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public long getLong(String key, int defaultValue) {
        checkPrefs();
        return prefs != null ? prefs.getLong(key, (long) defaultValue) : (long) defaultValue;
    }

    public void delete(String key) {
        checkPrefs();
        if (prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            if (mUseApply) {
                editor.apply();
            } else {
                editor.commit();
            }
        }
    }

}

