
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

//        logoutLocallyForTest();

        myRoutes = MyRoutes.getMyRoutesInstanse(this);






        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean isLoggedIn = myRoutes.isLoggedIn();

                if(! isLoggedIn) {
                     Intent intent;
                     intent = new Intent(SplashActivity.this, LoginActivity.class);
                     startActivity(intent);
                     finish();
                }

            }
        }, 4000);
    }

    public void logoutLocallyForTest(){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.clear();

        myEdit.commit();
    }

}