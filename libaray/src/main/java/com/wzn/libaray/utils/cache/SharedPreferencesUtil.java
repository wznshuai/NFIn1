package com.wzn.libaray.utils.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;


public class SharedPreferencesUtil {

    private static SharedPreferencesUtil instance;
    private SharedPreferences settings;

    private SharedPreferencesUtil(Context context) {
        settings = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtil getInstance(@NonNull Context context) {
        if (null == context)
            throw new NullPointerException("MyApplication 未初始化?");
        if (instance == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (instance == null)
                    instance = new SharedPreferencesUtil(
                            context.getApplicationContext());
            }
        }

        return instance;

    }

    public void saveString(String key, String value) {
        settings.edit().putString(key, value).commit();
    }

    public void applyString(String key, String value) {
        settings.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return settings.getString(key, "");
    }


    public void applyInt(String key, int value) {
        settings.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return settings.getInt(key, defaultValue);
    }


    public void saveBoolean(String key, boolean bool) {
        settings.edit().putBoolean(key, bool).commit();
    }

    public void applyBoolean(String key, boolean bool) {
        settings.edit().putBoolean(key, bool).apply();
    }


    public boolean getBoolean(String key, boolean is) {
        return settings.getBoolean(key, is);
    }


    public void removeByKey(String key) {
        settings.edit().remove(key).commit();
    }

    public void applyRemoveByKey(String key) {
        settings.edit().remove(key).apply();
    }

    public void clear() {
        settings.edit().clear().commit();
    }

    public void applyClear(){
        settings.edit().clear().apply();
    }

    public boolean contains(String alarmHour) {
        return settings.contains(alarmHour);
    }
}
