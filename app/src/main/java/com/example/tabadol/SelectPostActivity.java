package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.tabadol.adapters.PostSellectAdapter;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

import java.util.List;

public class SelectPostActivity extends AppCompatActivity {

    public static long destinationPostId = -1;
    public static long sourcePostId = -1;
    public static LinearLayout confirmMessageLayout;
    public static ListView postsListView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_post);

        Intent postDetailsIntent = getIntent();
        destinationPostId = postDetailsIntent.getLongExtra("id",-1);

        Button confirmOffer = findViewById(R.id.confirmOfferButton_Select_Post_activity);
        Button cancelOffer = findViewById(R.id.cancelOfferButton_Select_Post_activity);

        confirmMessageLayout = findViewById(R.id.linearLayout_confirm_cancel_offer_select_offer_activity);
        postsListView = findViewById(R.id.listView_posts_select_post_activity);

        Intent postIntent = getIntent();
        destinationPostId = postIntent.getLongExtra("id",-1);




        setTitle("Select A Post To Exchange");
        MyRoutes myRoutes = MyRoutes.getMyRoutesInstanse(this);
        User loggedInUser = myRoutes.getUser2();

        List<Post> posts = loggedInUser.getPosts();

        PostSellectAdapter postSellectAdapter = new PostSellectAdapter(this,posts);

        postsListView.setAdapter(postSellectAdapter);


        confirmOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRoutes.exchangeOffer(destinationPostId,sourcePostId);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myRoutes.getSentOffers();
                    }
                },100);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       Intent offersIntent = new Intent(SelectPostActivity.this, OffersActivity.class);
                       startActivity(offersIntent);
                    }
                },2000);

            }
        });

        cancelOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmMessageLayout.setVisibility(View.INVISIBLE);
                postsListView.setVisibility(View.VISIBLE);
            }
        });



    }
}