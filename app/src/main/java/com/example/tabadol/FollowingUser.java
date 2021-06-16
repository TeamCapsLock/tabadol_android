package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tabadol.adapters.AllUsersAdapter;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

import java.util.ArrayList;
import java.util.List;

public class FollowingUser extends AppCompatActivity {
    ListView listView ;
    MyRoutes myRoutes;
    TextView emptyMessage;


    ArrayList<User> followingUserList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_user);


        listView = findViewById(R.id.following_list_ListView);
        emptyMessage = findViewById(R.id.emptyFollowing_following_act);

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

        if(followingUserList.isEmpty()){
            emptyMessage.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);

            return;
        }

        ArrayList<User> followingUser = followingUserList;


//        FollowingUserAdapter followingUserAdapter = new FollowingUserAdapter(this, followingUser);
        AllUsersAdapter allUsersAdapter = new AllUsersAdapter(this,followingUser);
        listView.setAdapter(allUsersAdapter);


    }
}