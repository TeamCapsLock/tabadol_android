

package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView signupTextView;
    private Intent intent;
    private boolean valid = true;
    private boolean isLoggedIn = false;
    private static final String LOGIN_URL = "https://tabadol1.herokuapp.com/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // set the activity tile
        setTitle(R.string.login);

//
//        if(UserSession.sessionId != null){
//            intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        // find the view in the activity_login.xml page
        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.button_login);
        signupTextView = findViewById(R.id.text_signup);

        // set onClickListener on the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if the user enter the both fields
                if(TextUtils.isEmpty(username.getText().toString())) {
                    username.setError("Please enter your username");
                    valid = false;
                }

                if(TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("please enter your password");
                    valid = false;
                }

                // TODO: send the data to the server to check the login
                if(valid){
                    String params = "username="+username.getText().toString().trim()+"&password="+
                            password.getText().toString().trim();
                    Log.v("params", params);
                    byte[] postData = params.getBytes(StandardCharsets.UTF_8);
                    LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
                    loginAsyncTask.execute(params);



                }

                // TODO: if the credentials are correct, the save the session, and take the user to the main activity
//                editor.putString("isLoggedIn", "true");
//                editor.apply();
//
//                intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                // kill the activity so when the back button clicked it does not return to the login page again.
//                finish();

            }
        });

        // set onClickListener on signup text view, to take the user to the sign up page
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to SignupActivity.java
                intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                // kill the activity so when the back button clicked it does not return to the login page again.
                finish();
            }
        });
    } // end onCreate()


    private class LoginAsyncTask extends AsyncTask<String, Void, InputStreamReader>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected InputStreamReader doInBackground(String... args) {
            String params = args[0];
            int postDataLength = params.length();
            URL url = createUrl(LOGIN_URL);
            HttpURLConnection httpURLConnection = null;
            OutputStreamWriter outputStreamWriter = null;
            try{
                if(url != null) {
                    CookieManager cookieManager = new CookieManager();
                    CookieManager.setDefault(cookieManager);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setInstanceFollowRedirects(false);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; pl; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2");
                    httpURLConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty( "charset", "utf-8");
                    httpURLConnection.setRequestProperty( "Content-Length", Integer.toString(postDataLength));
//                    httpURLConnection.setRequestProperty(
//                            "Cookie","JSESSIONID=" + "DE42C0F1E945D810F4594D62BE41313C");
                    httpURLConnection.setUseCaches( false );
                    outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    outputStreamWriter.write(params);
                    httpURLConnection.connect();
//                    if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
//                        Toast.makeText(LoginActivity.this, "200", Toast.LENGTH_LONG).show();
                    outputStreamWriter.flush();
                    Log.v("code", httpURLConnection.getResponseCode()+"");
                    Log.v("header", httpURLConnection.getHeaderFields().values().toString());
                    if(httpURLConnection.getResponseCode() == 302 && isLocationCorrect(httpURLConnection.getHeaderFields().values().toString())){
                        String result = httpURLConnection.getHeaderFields().values().toString();
                        int index1 = result.indexOf("JSESSIONID=");
                        int index2 = result.indexOf("; Path");
                        UserSession.sessionId = httpURLConnection.getHeaderFields().values().toString().substring(index1+11, index2);
                        isLoggedIn = true;
                    }
                    return new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);

                }

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(InputStreamReader inputStreamReader) {
            if(isLoggedIn){
                intent = new Intent(LoginActivity.this, MyProfileActivity.class);
                startActivity(intent);
                finish();
            }else{
//                LoginActivity loginActivity = new LoginActivity();
//                loginActivity.password.setError("wrong password or username");
//                loginActivity.password.requestFocus();
                //TODO: find a good way rather than a toast message to tell if the credentials are incorrect
                Toast.makeText(LoginActivity.this, "incorrect password or username", Toast.LENGTH_SHORT).show();
            }
//            if(inputStreamReader != null){
//                Toast.makeText(LoginActivity.this, "Not null", Toast.LENGTH_LONG).show();
//                BufferedReader bufferedReader;
//                String line;
//                try {
//                    bufferedReader = new BufferedReader(inputStreamReader);
//                    while ((line = bufferedReader.readLine())!= null){
//                        Log.v("line", line);
//                    }
//                    bufferedReader.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
        }
    }


    private URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }
        return url;
    }

    private boolean isLocationCorrect(String response){
        int index1 = response.indexOf("https");
        String location = response.substring(index1);
        location = location.substring(0, location.indexOf("]"));
        Log.v("Location", location);
        if (location.contains("login"))
            return false;
        return true;
    }



}