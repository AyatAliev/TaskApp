package com.geektech.taskapp;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.room.Room;

import com.geektech.taskapp.room.AppDatabase;

public class App extends Application {

    private static AppDatabase database;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
        instance = this;
    }

    public static App getInstance(){
        return instance;
    }

    public static AppDatabase getDatabase() {
        return database;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}


