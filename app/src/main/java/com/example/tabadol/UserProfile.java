package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.*;

import java.util.List;

public class UserProfile extends AppCompatActivity {
    TextView usernameTv, ratingTv, followingTv, followersTv, nameTv, bioTv, skillsTv, emailTv, phoneTv, ratingTitleTv, followingTitleTv,followersTitleTv;
    ImageView image,coverImage, phone, email;
    ListView listView ;
    View line;
    private Intent intent;
    User loggedInUser = null;
    Button followButton, unfollowButton, rateButton;
    EditText rateValue;
    LinearLayout buttonsLayout ;
    long id;

    User currentUser = null;
    MyRoutes  myRoutes;


    private  boolean isOpen = false;
    private ConstraintSet layout1,layout2;
    private ConstraintLayout constraintLayout;
    private ImageView imageViewPhoto;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle(R.string.user_profile);














//        usernameTv = findViewById(R.id.username_user_profile_act);
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
        followButton = findViewById(R.id.follow_button_user_profile_act);
        unfollowButton = findViewById(R.id.unfollow_button_user_profile_act);
        rateButton = findViewById(R.id.rate_button_user_profile_act);
        rateValue = findViewById(R.id.number_rate_value_user_profile_act);
        buttonsLayout =findViewById(R.id.linearLayout2);
        coverImage =findViewById(R.id.imageView_cover);
        email = findViewById(R.id.email_icon);
        phone = findViewById(R.id.phone_icon);
        line = findViewById(R.id.line);




        myRoutes = MyRoutes.getMyRoutesInstanse(this);
        myRoutes.setUser(null);

        Intent userIntent = getIntent();
        id = userIntent.getLongExtra("id",0);
        if(id == 0)
            myRoutes.getLoggedInUser();

        else
            myRoutes.getUser(id);


        displayUserDetails();

       setGoToFollowingListener(followingTv);
       setGoToFollowingListener(followingTitleTv);

       setGoToFollowersListener(followersTv);
       setGoToFollowersListener(followersTitleTv);

       followButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               myRoutes.FollowUser(currentUser.getUsername());
               followButton.setVisibility(View.INVISIBLE);
               unfollowButton.setVisibility(View.VISIBLE);
               currentUser.setNumberOfFollowers(currentUser.getNumberOfFollowers()+1);
               followersTv.setText(currentUser.getNumberOfFollowers()+"");
           }
       });

       unfollowButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               myRoutes.UnfollowUser(currentUser.getUsername());
               unfollowButton.setVisibility(View.INVISIBLE);
               followButton.setVisibility(View.VISIBLE);
               currentUser.setNumberOfFollowers(currentUser.getNumberOfFollowers()-1);
               followersTv.setText(currentUser.getNumberOfFollowers()+"");
           }
       });

       rateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String rateValueStr = rateValue.getText().toString();
               if(rateValueStr.length() == 0)
                   return;
               int rateValueInt = Integer.parseInt(rateValueStr);
               myRoutes.RateUser(currentUser.getUsername(),rateValueInt);

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       myRoutes.getRatedUsers();
                       Intent selfIntent = new Intent(UserProfile.this, UserProfile.class);
                       selfIntent.putExtra("id",currentUser.getId());
                       startActivity(selfIntent);
                   }
               }, 1000);
           }
       });

    }



    private  void  displayUserDetails() {

        if(currentUser == null){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentUser = myRoutes.getUser2();
//                    Log.v("##########", loggedInUser+"");
                    displayUserDetails();
                }
            }, 500);

//            Log.v("##########", "before return display");
           return;
        }

//        usernameTv.setText(loggedInUser.getUsername());
        setTitle(currentUser.getUsername());
        ratingTv.setText(currentUser.getRating().toString());
        followingTv.setText(currentUser.getNumberOfFollowing().toString());
        followersTv.setText(currentUser.getNumberOfFollowers().toString());
        nameTv.setText(currentUser.getFirstname()+" "+ currentUser.getLastname());
        bioTv.setText(currentUser.getBio());
        skillsTv.setText(currentUser.getSkills());
        emailTv.setText(currentUser.getEmail());
        phoneTv.setText(currentUser.getPhone());

//        usernameTv.setVisibility(View.VISIBLE);
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
        coverImage.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);

        List<Long> myFollowingIds = myRoutes.getMyFollowingIds2();
        List<Long> myRatedIds = myRoutes.getRatedUsers2();


        if( ! currentUser.getUsername().equals( myRoutes.getUsernameFormSharedPreferences())){
            if(!myRatedIds.contains(id)){
                rateButton.setVisibility(View.VISIBLE);
                rateValue.setVisibility(View.VISIBLE);
            }
            if(myFollowingIds.contains(id)){
                unfollowButton.setVisibility(View.VISIBLE);
            }
            else{
                followButton.setVisibility(View.VISIBLE);
            }
        }
        else {
//            editProfileButton.setVisibility(View.VISIBLE);
        }

        List<Post> posts = currentUser.getPosts();


        Post_User_Profile_Adapter post_user_profile_adapter = new Post_User_Profile_Adapter(this,posts);
        listView.setAdapter(post_user_profile_adapter);

//        coverImage
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.male_icon);
        requestOptions.error(R.drawable.male_icon);

        Glide.with(UserProfile.this)
                .load(currentUser.getImage())
                .apply(requestOptions)
                .circleCrop()
                .into(image);

        RequestOptions requestOptions2 =new RequestOptions();
        requestOptions.placeholder(R.drawable.male_icon);
        requestOptions.error(R.drawable.male_icon);

        Glide.with(UserProfile.this)
                .load(currentUser.getImage())
                .apply(requestOptions)
                .circleCrop()
                .into(coverImage);





// user profile xml animation
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();


        constraintLayout = findViewById(R.id.constraint_layout);
        layout2.clone(this, R.layout.profile_expanded);
        layout1.clone(constraintLayout);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isOpen){
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    isOpen = !isOpen;

                }else{
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    isOpen = !isOpen;

                }



            }
        });




    }
    public void setGoToFollowingListener(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent followingListIntent = new Intent(UserProfile.this, FollowingUser.class);
                String name = currentUser.getUsername();
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
                String name = currentUser.getUsername();
                followersListIntent.putExtra("username",name);
                startActivity(followersListIntent);
            }
        });
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(  id == 0 || id == myRoutes.getLoggedInID() ){
            getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_profile_item_menu:
                Intent editProfileIntent = new Intent(UserProfile.this, EditProfileActivity.class);
                editProfileIntent.putExtra("user",currentUser);
                startActivity(editProfileIntent);
                return true;
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
                        Intent offersIntent = new Intent(UserProfile.this, OffersActivity.class);
                        startActivity(offersIntent);
                    }
                }, 1500);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}