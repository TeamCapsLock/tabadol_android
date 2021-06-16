
package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.example.tabadol.api.MyRoutes;


public class SplashActivity extends AppCompatActivity {

    MyRoutes myRoutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        myRoutes = MyRoutes.getMyRoutesInstanse(this);




        String currentUsername = myRoutes.getUsernameFormSharedPreferences();
        String currentPassword = myRoutes.getPasswordFormSharedPreferences();

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                Intent intent;
                if( currentPassword != null && currentUsername != null) {
//                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                }
                else{
                     intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();

            }
        }, 4000);
    }

}