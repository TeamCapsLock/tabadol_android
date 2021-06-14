package com.example.tabadol.api;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRoutes {

    String jwt = null;
    Map<String, String> headers;
    TabadolAPI tabadolAPI;
    MyRetrofit retrofit;
    Context context;
    String username;
    String password;
    ArrayList<Post> posts;
   public MyRoutes(Context context){
       this.retrofit = MyRetrofit.getInstance();
       this.tabadolAPI = retrofit.getApi();
       this.context = context;
       username = getUsernameFormSharedPreferences();
       password = getPasswordFormSharedPreferences();
   }


    public  void getJWT_token(String username, String password){
        Call<AuthenticationResponse> call = tabadolAPI.getJWT_token(new AuthenticationRequest(username,password));

        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
                Log.v("HTTP_Request: ","JWT token: "+response.body().getJwt());
                jwt = response.body().getJwt();


                // Storing data into SharedPreferences
                SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);

                // Creating an Editor object to edit(write to the file)
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                // Storing the key and its value as the data fetched from edittext
                myEdit.putString("jwt", jwt);
                myEdit.putString("username", username);
                myEdit.putString("password", password);
                MyRoutes.this.username = username;
                MyRoutes.this.password = password;



                // Once the changes have been made,
                // we need to commit to apply those changes made,
                // otherwise, it will throw an error
                myEdit.commit();


            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Log.e("HTTP_Request: ","code: "+t.getMessage());

            }
        });

    }


    public ArrayList<Post> getPosts(){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<List<Post>> call = tabadolAPI.getPosts(headers);


        call.enqueue(new Callback<List<Post>>() {

            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                Log.v("HTTP_Request: ","code: "+response.code());
                posts = (ArrayList<Post>) response.body();
                Log.v("HTTP_Request: ","all Post: "+posts.toString());

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPosts();
                    }
                }, 500);
                return;
            }
        });
        return posts;
    }

    public  void getLoggedInUser(){

        jwt = getJwtFormSharedPreferences();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer "+jwt);
        Call<User> call = tabadolAPI.getLoggedInUser(headers);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
                Log.v("HTTP_Request: ","code: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("HTTP_Request: ",t.getMessage());

                getJWT_token(username,password);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getLoggedInUser();
                    }
                }, 500);
                return;
            }
        });
    }
    public  void getUser(long id) {
        jwt = getJwtFormSharedPreferences();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + jwt);

        Call<User> call = tabadolAPI.getUser(headers, id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                Log.v("HTTP_Request: ", "code: " + response.code());


                Log.v("HTTP_Request: ", "response : " + response.body().toString());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("HTTP_Request: ", "Request failed.. something wrong in your request !  \n" + t.getMessage());
                getJWT_token(username, password);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getUser(id);
                    }
                }, 500);
                return;
            }
        });


    }

    public  void testPostJson(String username, String password){
        jwt = getJwtFormSharedPreferences();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + jwt);
        Call<ResponseJson> call = tabadolAPI.testPostJson(headers, new AuthenticationRequest(username,password));

        call.enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                Log.v("HTTP_Request: ","code: "+response.code());


                ResponseJson resJson = response.body();
                Log.v("HTTP_Request: ","response : "+resJson.getMessage());
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());

                getJWT_token(MyRoutes.this.username, MyRoutes.this.password);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testPostJson(username, password);
                    }
                }, 500);
                return;

            }
        });
    }


        public String getJwtFormSharedPreferences(){
        SharedPreferences sh = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
        jwt = sh.getString("jwt",null);
        return jwt;
    }

    public String getUsernameFormSharedPreferences(){
        SharedPreferences sh = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
        username = sh.getString("username",null);
        return username;
    }

    public String getPasswordFormSharedPreferences(){
        SharedPreferences sh = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
        password = sh.getString("password",null);
        return password;
    }

    public ArrayList<Post> getPost2(){
       return posts;
    }


}
