package com.enixma.sample.mobile.data.repository.datasource.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.enixma.sample.mobile.R;

import java.util.Map;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
public class AppSharedPreferences {

    private SharedPreferences sharedPreferences;

    public AppSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.SHAREDPREF_FILE), Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences(){
        return  sharedPreferences;
    }

    public void putInt(String key, int data) {
        sharedPreferences.edit().putInt(key, data).apply();
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key,defValue);
    }

    public void putFloat(String key, float data) {
        sharedPreferences.edit().putFloat(key, data).apply();
    }

    public float getFloat(String key, Float defValue) {
        return sharedPreferences.getFloat(key,defValue);
    }

    public void putLong(String key, Long data) {
        sharedPreferences.edit().putLong(key, data).apply();
    }

    public Long getLong(String key, Long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public void putString(String key, String data) {
        sharedPreferences.edit().putString(key, data).apply();
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public void putBoolean(String key, boolean data) {
        sharedPreferences.edit().putBoolean(key, data).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public Map getAll(){
        return  sharedPreferences.getAll();
    }

    public boolean contains(String key){
        return  sharedPreferences.contains(key);
    }

}