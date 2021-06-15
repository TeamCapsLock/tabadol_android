package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tabadol.api.MyRoutes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.tabadol.api.Post;
import com.example.tabadol.fragments.PostFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // TODO: change this to posts url
    public static final String URL ="https://tabadol1.herokuapp.com/jposts";
    private static final String ALL_USERS = "https://tabadol1.herokuapp.com/jallUsers";


    private ArrayList<Post> posts = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
//        setContentView(R.layout.activity_main);

        MyRoutes myRoutes = MyRoutes.getMyRoutesInstanse(this);
//        myRoutes.getJWT_token("mhd.sad","1234");
//        myRoutes.getJWT_token("ramayaser66", "1234");
//        myRoutes.getJWT_token("mhd.sad", "1234");
//        myRoutes.getJWT_token("A", "1234");
//        myRoutes.getPosts();
//        myRoutes.getUser(2);
//        displayPosts();

//        myRoutes.getAllUsers();
//        myRoutes.getFollowingList(myRoutes.getUsername());
//        myRoutes.getFollowersList(myRoutes.getUsername());
//        myRoutes.getReceivedOffers();
//        myRoutes.getSentOffers();
//        myRoutes.getFinishedOffers();
//        myRoutes.signup("A","A@A.com","A","A","1234","1234","A","A","0791234567","https://res.cloudinary.com/saify/image/upload/v1539009756/icon.jpg");
//        myRoutes.signup("B","B@B.com","B","B","1234","1234","B","B","0791234567","https://images.thdstatic.com/productImages/72b9aa15-5112-4dff-9639-b68170e00530/svn/house-numbers-address-letters-3582b-b-64_1000.jpg");
//
//            ***************************************
//        *******************************************
//    myRoutes.EditProfile("B","B","B","B","0781234567","https://images.thdstatic.com/productImages/72b9aa15-5112-4dff-9639-b68170e00530/svn/house-numbers-address-letters-3582b-b-64_1000.jpg");
//
//
//            myRoutes.UnfollowUser("A");
//            myRoutes.FollowUser("A");
//            myRoutes.RateUser("mhd.sad", 4);

//        myRoutes.addPost("this is my test post4 ... added from android", "programming","service",2);
       // myRoutes.acceptOffer(7,17);
       // myRoutes.acceptOffer(7,17);
//        myRoutes.acceptOffer(24,23);

//        myRoutes.declinedOffer(21,20);
//        myRoutes.exchangeOffer(23,24);



    }//end onCreate()

    public void displayPosts(){
        if(posts == null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    posts = MyRoutes.getMyRoutesInstanse(MainActivity.this).getPost2();
                    Log.v("##########", posts+"");
                    displayPosts();
                }
            }, 500);
            Log.v("##########", "before return display");
            return;
        }
        PostAdapter postAdapter = new PostAdapter(this, posts);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(postAdapter);
    }




} // end class