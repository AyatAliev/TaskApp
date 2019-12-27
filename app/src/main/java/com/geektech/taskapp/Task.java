package com.geektech.taskapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

@Entity
public class Task implements Serializable {


    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String desc;

    @Ignore
    public Task() {

    }

    public Task(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
