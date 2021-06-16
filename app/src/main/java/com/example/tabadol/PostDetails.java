package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

public class PostDetails extends AppCompatActivity {

    TextView detailsBody,username,category,type,date;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        detailsBody=findViewById(R.id.details_body);
        username=findViewById(R.id.details_name);
        category=findViewById(R.id.details_category);
        type=findViewById(R.id.details_type);
        imageView=findViewById(R.id.details_profile_image);
        date=findViewById(R.id.details_date);



        detailsBody.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        Post post = intent.getParcelableExtra("post");
        User user = intent.getParcelableExtra("user");
//        Log.v("%%%%%%%%%%%%%%", post.toString());
//        Log.v("@@@@@@@@@@@@@@@", user.toString());
        username.setText(user.getFirstname()+" "+user.getLastname());
        detailsBody.setText(post.getBody());
        category.setText(post.getCategory());
        type.setText(post.getType());
        date.setText(post.getCreatedAt());

        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.male_icon);
        requestOptions.error(R.drawable.male_icon);

        Glide.with(this)
                .load(user.getImage())
                .apply(requestOptions)
                .circleCrop()
                .into(imageView);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }
    Intent intent;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_profile_item_menu:
                Toast.makeText(getApplicationContext(), "edit profile", Toast.LENGTH_SHORT).show();
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
                        Intent offersIntent = new Intent(PostDetails.this, OffersActivity.class);
                        startActivity(offersIntent);
                    }
                }, 1500);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}