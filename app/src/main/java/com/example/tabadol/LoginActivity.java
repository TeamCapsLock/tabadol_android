

package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabadol.api.MyRoutes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView signupTextView;
    private Intent intent;
    private boolean valid = true;
    private boolean isLoggedIn = false;
    MyRoutes myRoutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // set the activity tile
//        setTitle(R.string.login);
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        myRoutes = MyRoutes.getMyRoutesInstanse(this);
        String currentUsername = myRoutes.getUsernameFormSharedPreferences();
        String currentPassword = myRoutes.getPasswordFormSharedPreferences();

        if( currentPassword != null && currentUsername != null) {
            myRoutes.getJWT_token(currentUsername, currentPassword);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, 2000);
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
                if(TextUtils.isEmpty(username.getText().toString())) {
                    username.setError("Please enter your username");
                    username.requestFocus();
                    valid = false;
                }

                if(TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("please enter your password");
                    password.requestFocus();
                    valid = false;
                }

                // TODO: send the data to the server to check the login
                if(valid){
                    myRoutes.getJWT_token(username.getText().toString(), password.getText().toString());
                }
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
    } // end onCreate()


}