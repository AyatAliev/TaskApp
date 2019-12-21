package com.geektech.taskapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText editText;
    private String userId;
    private EditText editName;
    private EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userId = FirebaseAuth.getInstance().getUid();
        editText = findViewById(R.id.editName);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        //    getinfo();
        getinfo2();
    }

    private void getinfo2() {
        FirebaseFirestore.getInstance().collection("users").document(userId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {
                            String name = documentSnapshot.getString("name");
                            String email = documentSnapshot.getString("email");
                            editName.setText(name);
                            editEmail.setText(email);

                        }
                    }
                });
    }

    private void getinfo() {
        FirebaseFirestore.getInstance().collection("users")
                .document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            String name = task.getResult().getString("name");
                            editText.setText(name);
                        }
                    }
                });
    }

    public void onClick(View view) {
        Map<String, Object> map = new HashMap<>();
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        map.put("name", name);
        map.put("email", email);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toaster.show("Успешно");
                        } else {
                            Toaster.show("Ошибка");
                        }
                    }
                });
        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.apply();

        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        setResult(RESULT_OK, intent);
        finish();

        hideKeyboard(ProfileActivity.this);
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
