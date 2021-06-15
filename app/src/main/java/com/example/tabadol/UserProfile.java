package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    TextView usernameTv, ratingTv, followingTv, followersTv, nameTv, bioTv, skillsTv, emailTv, phoneTv, ratingTitleTv, followingTitleTv,followersTitleTv;
    ImageView image;
    ListView listView ;

    User loggedInUser = null;
    MyRoutes  myRoutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle(R.string.user_profile);

        usernameTv = findViewById(R.id.username_user_profile_act);
        ratingTv = findViewById(R.id.rating_user_profile_act);
        followingTv = findViewById(R.id.following_user_profile_act);
        followersTv = findViewById(R.id.followers_user_profile_act);
        nameTv = findViewById(R.id.fullname_user_profile_act);
        bioTv = findViewById(R.id.bio_user_profile_act);
        skillsTv = findViewById(R.id.skills_user_profile_act);
        emailTv = findViewById(R.id.email_user_profile_act);
        phoneTv = findViewById(R.id.phone_user_profile_act);
        image = findViewById(R.id.image_user_profile_act);
        listView = findViewById(R.id.list_view_posts_of_user_User_profile_act);
        ratingTitleTv = findViewById(R.id.ratingTitle_user_profile_act);
        followingTitleTv = findViewById(R.id.following_title_user_profile_act);
        followersTitleTv = findViewById(R.id.followers_title_user_profile_act);

        myRoutes = MyRoutes.getMyRoutesInstanse(this);
        myRoutes.setUser(null);

        Intent userIntent = getIntent();
        long id = userIntent.getLongExtra("id",0);
        if(id == 0)
            myRoutes.getLoggedInUser();

        else
            myRoutes.getUser(id);


        displayUserDetails();

       setGoToFollowingListener(followingTv);
       setGoToFollowingListener(followingTitleTv);

       setGoToFollowersListener(followersTv);
       setGoToFollowersListener(followersTitleTv);
    }

    private  void  displayUserDetails() {

        if(loggedInUser == null){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loggedInUser = myRoutes.getUser2();
//                    Log.v("##########", loggedInUser+"");
                    displayUserDetails();
                }
            }, 500);

//            Log.v("##########", "before return display");
           return;
        }

        usernameTv.setText(loggedInUser.getUsername());
        ratingTv.setText(loggedInUser.getRating().toString());
        followingTv.setText(loggedInUser.getNumberOfFollowing().toString());
        followersTv.setText(loggedInUser.getNumberOfFollowers().toString());
        nameTv.setText(loggedInUser.getFirstname()+" "+loggedInUser.getLastname());
        bioTv.setText(loggedInUser.getBio());
        skillsTv.setText(loggedInUser.getSkills());
        emailTv.setText(loggedInUser.getEmail());
        phoneTv.setText(loggedInUser.getPhone());

        usernameTv.setVisibility(View.VISIBLE);
        ratingTv.setVisibility(View.VISIBLE);
        followingTv.setVisibility(View.VISIBLE);
        followersTv.setVisibility(View.VISIBLE);
        nameTv.setVisibility(View.VISIBLE);
        bioTv.setVisibility(View.VISIBLE);
        skillsTv.setVisibility(View.VISIBLE);
        emailTv.setVisibility(View.VISIBLE);
        phoneTv.setVisibility(View.VISIBLE);
        image.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        followersTitleTv.setVisibility(View.VISIBLE);
        followingTitleTv.setVisibility(View.VISIBLE);
        ratingTitleTv.setVisibility(View.VISIBLE);

        List<Post> posts = loggedInUser.getPosts();


        Post_User_Profile_Adapter post_user_profile_adapter = new Post_User_Profile_Adapter(this,posts);
        listView.setAdapter(post_user_profile_adapter);


        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.male_icon);
        requestOptions.error(R.drawable.male_icon);

        Glide.with(UserProfile.this)
                .load(loggedInUser.getImage())
                .apply(requestOptions)
                .circleCrop()
                .into(image);


    }
    public void setGoToFollowingListener(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent followingListIntent = new Intent(UserProfile.this, FollowingUser.class);
                String name = usernameTv.getText().toString();
                followingListIntent.putExtra("username",name);
                startActivity(followingListIntent);
            }
        });
    }

    public void setGoToFollowersListener(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent followersListIntent = new Intent(UserProfile.this, FollowersList.class);
                String name = usernameTv.getText().toString();
                followersListIntent.putExtra("username",name);
                startActivity(followersListIntent);
            }
        });
    }



}