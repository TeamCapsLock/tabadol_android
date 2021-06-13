package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

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
    public static final String URL ="tabadol.herokuapp.com/jposts";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }//end onCreate()

    private class PostAsyncTask extends AsyncTask<URL, Void, ArrayList<Post>>{

        @Override
        protected ArrayList<Post> doInBackground(URL... urls) {
            URL url = createUrl(URL);
            String jsonResponse="";
            try{
                if(url != null)
                    jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //TODO: change this later
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<Post> posts) {
            if(posts == null)
                return;

            //TODO: display the posts on the screen


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
        private ArrayList<Post> getPosts(String jsonResponse){
            ArrayList<Post> posts = new ArrayList<>();
            // TODO: create the object from the json response;



            return posts;
        }


    } //end PostAsyncTask




} // end class