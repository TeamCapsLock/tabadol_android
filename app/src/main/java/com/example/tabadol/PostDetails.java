package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

public class PostDetails extends AppCompatActivity {

    TextView detailsBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        detailsBody=findViewById(R.id.details_body);
        detailsBody.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        Post post = intent.getParcelableExtra("post");
        User user = intent.getParcelableExtra("user");
        Log.v("%%%%%%%%%%%%%%", post.toString());
        Log.v("@@@@@@@@@@@@@@@", user.toString());


    }
}