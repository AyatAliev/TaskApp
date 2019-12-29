package com.geektech.taskapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText editText;
    private String userId;
    private EditText editName;
    private EditText editEmail;
    private ImageView imageView;
    private ImageView haderImage;
    private Uri imageUri;
    SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    Uri muri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userId = FirebaseAuth.getInstance().getUid();
        editText = findViewById(R.id.editName);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progress_bar);
        haderImage = findViewById(R.id.imageViewhader);
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
        StorageReference storage = FirebaseStorage.getInstance().getReference();

        storage.child("avatars/" + userId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ProfileActivity.this).load(uri).circleCrop().into(imageView);
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
        hideKeyboard(ProfileActivity.this);
        if (imageUri != null) {
            uploadImage(null);
        } else {
            save(null);
        }
    }

    private void uploadImage(final Uri uri) {
        String userId = FirebaseAuth.getInstance().getUid();
//        editor.putString("uri", String.valueOf(uri));
  //      editor.apply();
        StorageReference reference =
                FirebaseStorage.getInstance().getReference().child("avatars/" + userId);
        final UploadTask task = reference.putFile(imageUri);
        progressBar.setVisibility(View.VISIBLE);

        task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toaster.show("Успешно");
                    save(String.valueOf(uri));
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });


    }


    private void save(String avatarUri) {
        Map<String, Object> map = new HashMap<>();
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        map.put("name", name);
        map.put("email", email);
        map.put("avatar", avatarUri);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toaster.show("Успешно");
                            finish();
                        } else {
                            Toaster.show("Ошибка");
                        }
                    }
                });
        editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.apply();
    }


    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void onClickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            imageUri = data.getData();
            uploadImage(data.getData());
            Glide.with(this).load(data.getData()).circleCrop().into(imageView);

        }
    }
}
