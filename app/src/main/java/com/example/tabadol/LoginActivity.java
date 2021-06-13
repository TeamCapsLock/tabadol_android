package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView signupTextView;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // set the activity tile
        setTitle(R.string.login);
        // create shared preferences to store whether the user logged in or not
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // create an editor to save data to the shared preferences
        editor = sharedPreferences.edit();

        // if the user logged in, take him to the main page (MainActivity.java)
        if(sharedPreferences.getString("isLoggedIn", "null").equals("true")){
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            // kill the activity so when the back button clicked it does not return to the login page again.
            finish();
        }

        // find the view in the activity_login.xml page
        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.button_login);
        signupTextView = findViewById(R.id.text_signup);

        // set onClickListener on the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if the user enter the both fields
                if(TextUtils.isEmpty(username.getText().toString()))
                    username.setError("Please enter your username");
                if(TextUtils.isEmpty(password.getText().toString()))
                    password.setError("please enter your password");
                // TODO: send the data to the server to check the login

                // TODO: if the credentials are correct, the save the session, and take the user to the main activity
//                editor.putString("isLoggedIn", "true");
//                editor.apply();
//
//                intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                // kill the activity so when the back button clicked it does not return to the login page again.
//                finish();

            }
        });

        // set onClickListener on signup text view, to take the user to the sign up page
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to SignupActivity.java
                intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                // kill the activity so when the back button clicked it does not return to the login page again.
                finish();
            }
        });
    }

}