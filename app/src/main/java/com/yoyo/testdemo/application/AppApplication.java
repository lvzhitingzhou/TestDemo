package com.yoyo.testdemo.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by liumin on 2015/5/5.
 */
public class AppApplication extends Application {
    private static AppApplication sInstance;
    private static String SHARED_PREFERENCE_FILE_NAME = "my_shared_preference";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

    public static void saveToSharedPreference(Context context, String key, String value){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
    }

    public static void saveToSharedPreference(Context context, String key, Boolean value){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).apply();
    }
    public static String readFromSharedPreference(Context context, String key, String defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, MODE_PRIVATE);
       return  preferences.getString(key,defaultValue);
    }

    public static boolean readFromPreferences(Context context, String preferenceName, boolean defaultValue) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, MODE_PRIVATE);
        return preferences.getBoolean(preferenceName, defaultValue);
    }
}
