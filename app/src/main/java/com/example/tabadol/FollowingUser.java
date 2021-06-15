package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

import java.util.ArrayList;
import java.util.List;

public class FollowingUser extends AppCompatActivity {
    ListView listView ;
    MyRoutes myRoutes;


    ArrayList<User> followingUserList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_user);


        listView = findViewById(R.id.following_list_ListView);
        myRoutes = MyRoutes.getMyRoutesInstanse(this);

        Intent userProfileIntent = getIntent();
        String username = userProfileIntent.getStringExtra("username");
        if( username == null || username.length() ==0)
            username = myRoutes.getUsername();

        myRoutes.getFollowingList(username);

        displayFollowingUserDetails();

    }

    private  void  displayFollowingUserDetails() {
        if(followingUserList == null){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    followingUserList = myRoutes.getFollowingList2();
                    displayFollowingUserDetails();
                }
            }, 500);
            return;
        }



        ArrayList<User> followingUser = followingUserList;


        FollowingUserAdapter followingUserAdapter = new FollowingUserAdapter(this, followingUser);
        listView.setAdapter(followingUserAdapter);


    }
}