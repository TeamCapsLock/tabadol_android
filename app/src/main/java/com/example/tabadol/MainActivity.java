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
        setContentView(R.layout.activity_main);

        MyRoutes myRoutes = MyRoutes.getMyRoutesInstanse(this);
//        myRoutes.getPosts();
        myRoutes.getUser(2);
//        displayPosts();

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