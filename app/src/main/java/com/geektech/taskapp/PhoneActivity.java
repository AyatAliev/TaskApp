package com.geektech.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.EasyPermissions;

public class PhoneActivity extends AppCompatActivity {


    private EditText editNumber;
    private EditText sms;
    private String id;
    private Button onSms;
    private boolean isCodeSend;
    String phoneNumber;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verifyPhoneNumber;

    @SuppressLint({"MissingPermission", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        sms = findViewById(R.id.smsCode);
        onSms = findViewById(R.id.onSmsClick);
        editNumber = findViewById(R.id.PAeditText);

  /*      if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_NUMBERS)) {
            TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            assert telephonyManager != null;
             editNumber.setText(telephonyManager.getLine1Number());
        } else {
            EasyPermissions.requestPermissions(this, "Разрешить?", 101,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }*/
    //    editNumber.setText(phoneNumber);
//        Log.d("ololo", phoneNumber);
        verifyPhoneNumber = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                if (isCodeSend) {
                } else {
                    singIn(phoneAuthCredential);
                }

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("ololo", "onVerificationCompleted" + e.getMessage());


            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                id = s;
                isCodeSend = true;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);

            }
        };
    }

    private void singIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                            Toaster.show("Успешно");
                            finish();
                        } else {
                            Toaster.show("Ошибка");
                        }
                    }
                });
    }

    public void onClick(View view) {
        hideKeyboard(PhoneActivity.this);
        String number = editNumber.getText().toString().trim();
        if (number.isEmpty()){
            editNumber.setError("Номер не введен");
            editNumber.requestFocus();
            return;
        }

        if (number.length() < 10){
            editNumber.setError("Неправильный номер телефона");
            editNumber.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60,
                TimeUnit.SECONDS, this, verifyPhoneNumber);
        editNumber.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        sms.setVisibility(View.VISIBLE);
        onSms.setVisibility(View.VISIBLE);
    }

    public void onCodeClic(View view) {
        String code = sms.getText().toString();
        if (code.isEmpty()){
            sms.setError("СМС код не введен");
            sms.requestFocus();
            return;
        }
        PhoneAuthCredential phoneCredential = PhoneAuthProvider.getCredential(id, code);
        singIn(phoneCredential);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
