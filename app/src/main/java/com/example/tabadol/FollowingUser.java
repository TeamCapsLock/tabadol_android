package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    Intent intent;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_menu_item:
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.my_profile_item_menu:
                intent = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(intent);
                return true;
            case R.id.logout_item_menu:
                MyRoutes myRoutes = MyRoutes.getMyRoutesInstanse(this);
                myRoutes.logout();
                finish();
                return true;
            case R.id.myOffers_item_menu:
                MyRoutes.getMyRoutesInstanse(this).getSentOffers();
                MyRoutes.getMyRoutesInstanse(this).getReceivedOffers();
                MyRoutes.getMyRoutesInstanse(this).getFinishedOffers();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent offersIntent = new Intent(FollowingUser.this, OffersActivity.class);
                        startActivity(offersIntent);
                    }
                }, 1500);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}