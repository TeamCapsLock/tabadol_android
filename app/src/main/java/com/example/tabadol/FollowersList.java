package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.User;

import java.util.ArrayList;

public class FollowersList extends AppCompatActivity {

    ListView listView ;
    MyRoutes myRoutes;


    ArrayList<User> FollowersList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_list);

        listView = findViewById(R.id.followers_list_listView);
        myRoutes = MyRoutes.getMyRoutesInstanse(this);


        myRoutes.getFollowersList(myRoutes.getUsername());

        displayFollowersDetails();

    }

    private  void  displayFollowersDetails() {
        if(FollowersList == null){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FollowersList = myRoutes.getFollowersList2();
                    displayFollowersDetails();
                }
            }, 500);
            return;
        }



        ArrayList<User> followersList = FollowersList;

        FollowersAdapter followersAdapter = new FollowersAdapter(this,FollowersList);

        listView.setAdapter(followersAdapter);


    }
}