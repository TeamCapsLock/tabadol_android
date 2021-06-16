package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
}