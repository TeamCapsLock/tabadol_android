<<<<<<< HEAD
=======

>>>>>>> 917b144301735216e1732c179dce02f19cf5bcf5
package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

<<<<<<< HEAD
import com.example.tabadol.PostDetails;
=======
import com.example.tabadol.api.MyRoutes;
>>>>>>> 917b144301735216e1732c179dce02f19cf5bcf5

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
<<<<<<< HEAD
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
=======
                Intent intent;
                if( currentPassword != null && currentUsername != null) {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                }
                else{
                     intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
>>>>>>> 917b144301735216e1732c179dce02f19cf5bcf5
                startActivity(intent);
                finish();

            }
        }, 4000);
    }

}