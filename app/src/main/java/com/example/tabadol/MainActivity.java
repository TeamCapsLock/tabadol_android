package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // TODO: change this to posts url
    public static final String URL ="https://tabadol1.herokuapp.com/jposts";
    private static final String ALL_USERS = "https://tabadol1.herokuapp.com/jallUsers";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, UserSession.sessionId, Toast.LENGTH_SHORT).show();
        PostAsyncTask postAsyncTask = new PostAsyncTask();
        postAsyncTask.execute();


    }//end onCreate()

    private class PostAsyncTask extends AsyncTask<URL, Void, ArrayList<Post>>{

        @Override
        protected ArrayList<Post> doInBackground(URL... urls) {
            URL url = createUrl(URL);
            String jsonResponse="";
            ArrayList<Post> posts = new ArrayList<>();

            try{
                if(url != null) {
                    jsonResponse = makeHttpRequest(url);
                    posts = getPosts(jsonResponse);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return posts;
        }

        @Override
        protected void onPostExecute(ArrayList<Post> posts) {
            if(posts == null)
                return;

            displayPosts(posts);

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
        private ArrayList<Post> getPosts(String jsonResponse) throws JSONException {
            ArrayList<Post> posts = new ArrayList<>();
            // TODO: create the object from the json response;
            JSONArray jsonPosts = new JSONArray(jsonResponse);
//            Log.v("jsonPosts", jsonPosts.toString());
            String body;
            String category;
            String type;
            Integer weight;
            Boolean available;
            String createdAt;

            for(int i=0; i<jsonPosts.length(); i++){
                body = jsonPosts.getJSONObject(i).getString("body");
                category = jsonPosts.getJSONObject(i).getString("category");
                type = jsonPosts.getJSONObject(i).getString("type");
                weight = jsonPosts.getJSONObject(i).getInt("weight");
                available = jsonPosts.getJSONObject(i).getBoolean("available");
                createdAt = jsonPosts.getJSONObject(i).getString("createdAt");
                posts.add(new Post(body, category, type, weight, available, createdAt));
            }

            return posts;
        }


    } //end PostAsyncTask

    public void displayPosts(ArrayList<Post> posts){
        for(Post post: posts){
            Log.v("post", post.toString());
        }
        PostAdapter postAdapter = new PostAdapter(this, posts);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(postAdapter);
    }




} // end class