package com.geektech.taskapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geektech.taskapp.room.TaskDao;
import com.geektech.taskapp.ui.home.HomeFragment;
import com.geektech.taskapp.ui.home.TaskAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText desc;
    private String userId;

    Task mtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.editTitle);
        desc = findViewById(R.id.description);
        userId = FirebaseAuth.getInstance().getUid();

        edit();
    }

    public void edit() {

        mtask = (Task) getIntent().getSerializableExtra("Task");
        if (mtask != null) {
            editTitle.setText(mtask.getTitle());
            desc.setText(mtask.getDesc());
        }
    }

    private void getInfo2() {
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .add(mtask)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            mtask.setId(task.getResult().getId());
                            App.getDatabase().taskDao().insert(mtask);
                            finish();
                        }
                    }
                });
    }

    public void onClick(View view) {

        String title = editTitle.getText().toString().trim();
        String description = desc.getText().toString().trim();
        if (mtask != null) {
            mtask.setTitle(title);
            mtask.setDesc(description);
            updateTask();
        } else {
            mtask = new Task();
            mtask.setTitle(title);
            mtask.setDesc(description);
            getInfo2();
        }
       /* Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .document()
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toaster.show("Успешно");
                        } else {
                            Toaster.show("Ошибка");
                        }
                    }

                });*/

        if (mtask != null) {
            mtask.setTitle(title);
            mtask.setDesc(description);
            App.getDatabase().taskDao().update(mtask);
        } else {
            mtask = new Task(title, description);
            App.getDatabase().taskDao().insert(mtask);
        }
        finish();
    }

    private void updateTask() {
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .document(mtask.getId())
                .set(mtask)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        if (task.isSuccessful()){
                            App.getDatabase().taskDao().update(mtask);
                            finish();
                        }
                    }
                });
    }
}

