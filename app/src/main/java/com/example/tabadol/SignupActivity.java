package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText phone;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button signupButton;
    private TextView loginTextView;
    private Intent intent;
    private String pattern;
    String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // set the title of the activity
        setTitle(R.string.signup);


        // find the views from activity_signup
        firstname = findViewById(R.id.edit_text_firstname);
        lastname = findViewById(R.id.edit_text_lastname);
        username = findViewById(R.id.username_signup);
        phone = findViewById(R.id.edit_text_phone);
        email = findViewById(R.id.edit_text_email);
        password = findViewById(R.id.edit_text_password);
        confirmPassword = findViewById(R.id.edit_text_confirm_password);
        signupButton = findViewById(R.id.button_signup);
        loginTextView = findViewById(R.id.text_login);

        // set gender to default
        gender = getResources().getString(R.string.male).toLowerCase();

        // set onClickListener to login text view to take the user to the login page
        // if he already has an account
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                // kill the activity so when the back button clicked it does not return to the signup page again.
                finish();
            }
        });

        // set onClickListener on the sign up button to check the user input and to register the user

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                // TODO: replace the hard-coded strings with strings.xml strings
                // make sure the user entered all fields
                if(TextUtils.isEmpty(firstname.getText().toString().trim())) {
                    firstname.setError("please enter your first name");
                    valid = false;
                }
                if(TextUtils.isEmpty(lastname.getText().toString().trim())) {
                    lastname.setError("please enter your last name");
                    valid = false;
                }
                if(TextUtils.isEmpty(username.getText().toString().trim())) {
                    username.setError("please enter your username");
                    valid = false;
                }
                if(TextUtils.isEmpty(phone.getText().toString().trim())) {
                    phone.setError("please enter your phone");
                    valid = false;
                }
                if(TextUtils.isEmpty(email.getText().toString().trim().trim())) {
                    email.setError("please enter your email");
                    valid = false;
                }
                if(TextUtils.isEmpty(password.getText().toString().trim())) {
                    password.setError("please enter a password");
                    valid = false;
                }
                if(TextUtils.isEmpty(confirmPassword.getText().toString().trim())) {
                    confirmPassword.setError("please enter password again");
                    valid = false;
                }

                // validate the phone number
                pattern = "^07(7|8|9)[0-9]{7}$";
                if(!isValid(pattern, phone.getText().toString().trim())) {
                    phone.setError("invalid phone number");
                    valid = false;
                }

                // validate email
                pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
                        "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\" +
                        "x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
                        "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]" +
                        "|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|" +
                        "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]" +
                        "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
                if(!isValid(pattern, email.getText().toString().trim())) {
                    email.setError("invalid email");
                    valid = false;

                }

                // validate password
                pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
                if(!isValid(pattern, password.getText().toString().trim())) {
                    password.setError("password is weak");
                    valid = false;
                }

                // check if the two passwords matches

                if(!password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
                    confirmPassword.setError("Passwords don't match");
                    valid = false;
                }


                // here all the enter data is correct and now you can send them to the server
                if(valid) {
                    Toast.makeText(SignupActivity.this, "All correct, gender is: " + gender, Toast.LENGTH_LONG).show();
                    String firstname_ = firstname.getText().toString().trim();
                    String lastname_ = lastname.getText().toString().trim();
                    String username_ = username.getText().toString().trim();
                    String phone_ = phone.getText().toString().trim();
                    String email_ = email.getText().toString().trim();
                    String password_ = password.getText().toString().trim();
                    // gender already stored in gender variable


                }
            }
        });

    } // end onCreate()

    public boolean isValid(String  regex, String str){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public void getGender(View view){
        boolean isChecked = ((RadioButton)view).isChecked();
        if(view.getId() == R.id.radio_button_male) {
            gender = getResources().getString(R.string.male).toLowerCase();
            Toast.makeText(SignupActivity.this, "gender: " + gender, Toast.LENGTH_LONG).show();
        }
        else if(view.getId() == R.id.radio_button_female) {
            gender = getResources().getString(R.string.female).toLowerCase();
            Toast.makeText(SignupActivity.this, "gender: " + gender, Toast.LENGTH_LONG).show();

        }
        // TODO: make sure to have one button checked and remove this line
        else {
            gender = null;
            Toast.makeText(SignupActivity.this, "gender: " + gender, Toast.LENGTH_LONG).show();
        }


    }


}