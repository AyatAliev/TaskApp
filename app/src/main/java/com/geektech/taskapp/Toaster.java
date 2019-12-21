package com.geektech.taskapp;

import android.widget.Toast;

public class Toaster {
    public static void show(String message){
        Toast.makeText(App.getInstance(),message,Toast.LENGTH_SHORT).show();
    }
}
