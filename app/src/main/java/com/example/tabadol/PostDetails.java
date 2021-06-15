package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class PostDetails extends AppCompatActivity {

    TextView detailsBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        detailsBody=findViewById(R.id.details_body);
        detailsBody.setMovementMethod(new ScrollingMovementMethod());


    }
}