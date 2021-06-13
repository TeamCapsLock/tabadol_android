package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MyProfileActivity extends AppCompatActivity {
    // TODO: find a way to make it dynamic
    private static final String MY_PROFILE_URL = "https://tabadol1.herokuapp.com/jprofile/1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toast.makeText(this, "["+UserSession.sessionId+"]", Toast.LENGTH_SHORT).show();

        ProfileAsyncTask profileAsyncTask = new ProfileAsyncTask();
        profileAsyncTask.execute();
    }

    private class ProfileAsyncTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL url = createUrl(MY_PROFILE_URL);
            String response="";
            if (url != null){
                try {
                    response = makeHttpRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.v("profile", s);
        }
    }
    // create valid url to use later in HTTPRequest
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

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            // set 10 seconds to get the json
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            jsonResponse = readStreamData(inputStream);

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        Log.v("json", jsonResponse);
        return jsonResponse;
    }

    private String readStreamData(InputStream inputStream)throws IOException{
        StringBuilder result = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line=bufferedReader.readLine()) != null){
                result.append(line);
            }
        }
        return result.toString();
    }

    // TODO: create a method that read the json and convert it to posts
    private String getProfile(String jsonResponse) throws JSONException {

        return "";
    }
}