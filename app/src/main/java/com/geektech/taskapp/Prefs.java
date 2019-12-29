package com.geektech.taskapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static Prefs instance;
    private SharedPreferences preferences;

    public Prefs() {
        instance = this;
        preferences = App.getInstance().getSharedPreferences("settings" , Context.MODE_PRIVATE);
    }

    public static Prefs getInstance() {
        return instance;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void saveUserInfo(String name, String email) {
        preferences.edit()
                .putString("name", name)
                .putString("email", email);
    }

    public void saveisShow(){
        preferences.edit().putBoolean("isShown" , false);
    }

    public String getName(){
        return preferences.getString("name", null);
    }

    public boolean getIsShow(){
        return  preferences.getBoolean("isShown", false);
    }

    public void saveinURL(String url) {
        preferences.edit()
                .putString("url", url);
    }
}
