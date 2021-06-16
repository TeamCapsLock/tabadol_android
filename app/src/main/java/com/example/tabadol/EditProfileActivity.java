package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tabadol.api.EditProfileForm;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        EditText firstName,lastName,skills,bio,phone,image;
        firstName = findViewById(R.id.edit_first_name_edit_profile_act);
        lastName = findViewById(R.id.edit_last_name_edit_profile_act);
        skills = findViewById(R.id.edit_skills_edit_profile_act);
        bio = findViewById(R.id.edit_bio_edit_profile_act);
        phone = findViewById(R.id.edit_Phone_edit_profile_act);
        image = findViewById(R.id.edit_Image_url_edit_profile_act);
        Button saveChangesButton = findViewById(R.id.save_edits_button_edit_profile_act);


        Intent userProfileIntent = getIntent();
        User user = userProfileIntent.getParcelableExtra("user");

        firstName.setText(user.getFirstname());
        lastName.setText(user.getLastname());
        skills.setText(user.getSkills());
        bio.setText(user.getBio());
        phone.setText(user.getPhone());
        image.setText(user.getImage());

        setTitle("Edit Profile");
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // validate the phone number
                String pattern = "^07(7|8|9)[0-9]{7}$";
                if(! isValid(pattern, phone.getText().toString().trim())) {
                    phone.setError("invalid phone number");
                    return;
                }


                MyRoutes.getMyRoutesInstanse(EditProfileActivity.this).EditProfile(firstName.getText().toString(), lastName.getText().toString(), skills.getText().toString(),bio.getText().toString(),phone.getText().toString(),image.getText().toString());
                Intent userProfileIntent = new Intent(EditProfileActivity.this, UserProfile.class);
                startActivity(userProfileIntent);
            }
        });


    }
    public boolean isValid(String  regex, String str){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    Intent intent;

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
                        Intent offersIntent = new Intent(EditProfileActivity.this, OffersActivity.class);
                        startActivity(offersIntent);
                    }
                }, 1500);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }







}