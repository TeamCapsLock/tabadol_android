package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tabadol.api.AddPostForm;
import com.example.tabadol.api.MyRoutes;

public class AddPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String category;
    private String type;
    private EditText postBody;
    private RadioGroup radioGroup;
    private Spinner spinner;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        postBody = findViewById(R.id.body_add_post_activity);
        radioGroup = findViewById(R.id.radio_group_type);
        spinner = findViewById(R.id.spinner_category_add_post_activity);
        Button addPostButton =  findViewById(R.id.add_post_button_add_post_activity);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        RadioButton productType = findViewById(R.id.radio_product);
        productType.setChecked(true);

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPostButton.setEnabled(false);
                AddPostForm addPostForm = new AddPostForm(postBody.getText().toString(),category,type,0);
                MyRoutes.getMyRoutesInstanse(AddPost.this).addPost(addPostForm.getBody(),addPostForm.getCategory(),addPostForm.getType(),addPostForm.getWeight());
                Intent userProfileIntent = new Intent(AddPost.this, UserProfile.class);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(userProfileIntent);
                    }
                },500);

            }
        });







    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getType(View view){
        boolean isChecked = ((RadioButton)view).isChecked();
        if(view.getId() == R.id.radio_product) {
            type = getResources().getString(R.string.product).toLowerCase();
        }
        else if(view.getId() == R.id.radio_service) {
            type = getResources().getString(R.string.service).toLowerCase();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

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
                        Intent offersIntent = new Intent(AddPost.this, OffersActivity.class);
                        startActivity(offersIntent);
                    }
                }, 1500);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}