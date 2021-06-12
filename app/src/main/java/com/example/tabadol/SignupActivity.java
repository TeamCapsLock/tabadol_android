package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle(R.string.signup);
    }
}