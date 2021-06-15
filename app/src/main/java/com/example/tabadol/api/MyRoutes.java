package com.example.tabadol.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabadol.HomeActivity;
import com.example.tabadol.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRoutes {

    private String jwt = null;
    private Map<String, String> headers;
    private TabadolAPI tabadolAPI;
    private MyRetrofit retrofit;
    private Context context;
    private String username;
    private String password;
    private User user = null;
    private ArrayList<Post> posts = null;
    private ArrayList<User> allUsers = null;
    private ArrayList<User> followingList = null;
    private ArrayList<User> followersList = null;

    private static MyRoutes myRoutesInstanse = null;

   private MyRoutes(Context context){
       this.retrofit = MyRetrofit.getInstance();
       this.tabadolAPI = retrofit.getApi();
       this.context = context;
       username = getUsernameFormSharedPreferences();
       password = getPasswordFormSharedPreferences();
       jwt = getJwtFormSharedPreferences();
       if(jwt != null){
            getPosts();
            getAllUsers();
       }



   }

    public void setUser(User user) {
        this.user = user;
    }

    public static MyRoutes getMyRoutesInstanse(Context context){
       if (myRoutesInstanse == null)
           myRoutesInstanse = new MyRoutes(context);

       return myRoutesInstanse;
   }


    public void logout(){
        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.clear();

        myEdit.commit();
    }

    public  void getJWT_token(String username, String password){
        Call<AuthenticationResponse> call = tabadolAPI.getJWT_token(new AuthenticationRequest(username,password));
        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
                Log.v("HTTP_Request: ","JWT token: "+response.body().getJwt());


                    jwt = response.body().getJwt();


                    Intent homeIntent = new Intent(context, HomeActivity.class);

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

                    myEdit.commit();

                    if(posts == null){
                        getPosts();
                        getAllUsers();
                    }


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        context.startActivity(homeIntent);
                    }
                }, 1500);


            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Log.e("HTTP_Request: ","code: "+t.getMessage());

                if(t.getMessage().equals("timeout"))
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getJWT_token(username, password);
                        }
                    }, 500);
                    return;
                }
                else{
                    Toast.makeText(context,"username or password is wrong!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void getPosts(){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<List<Post>> call = tabadolAPI.getPosts(headers);


        call.enqueue(new Callback<List<Post>>() {

            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                Log.v("HTTP_Request: ","code: "+response.code());
                MyRoutes.this.posts = (ArrayList<Post>) response.body();
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
                MyRoutes.this.user = response.body();
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

    public void getUser(long id) {
        jwt = getJwtFormSharedPreferences();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + jwt);

        Call<User> call = tabadolAPI.getUser(headers, id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                Log.v("HTTP_Request: ", "code: " + response.code());


                Log.v("HTTP_Request: ", "response : " + response.body().toString());
                MyRoutes.this.user = response.body();

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

    //
    public void getAllUsers(){

        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<List<User>> call = tabadolAPI.getAllUsers(headers);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
//                Log.v("HTTP_Request: ","code: "+response.body());

                MyRoutes.this.allUsers = (ArrayList<User>) response.body();
                Log.v("HTTP_Request: ","all users: "+allUsers.toString());

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAllUsers();
                    }
                }, 500);
                return;
            }


        });

    }

    public void getFollowingList(String username){

        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<List<User>> call = tabadolAPI.getFollowingList(headers, username);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
//                Log.v("HTTP_Request: ","code: "+response.body());

                MyRoutes.this.followingList = (ArrayList<User>) response.body();
                Log.v("HTTP_Request: ","following list : "+followingList.toString());

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(MyRoutes.this.username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFollowingList(username);
                    }
                }, 500);
                return;

            }
        });


    }
    public void getFollowersList(String username){

        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call call = tabadolAPI.getFollowersList(headers, username);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.v("HTTP_Request: ","code: "+response.code());
//                Log.v("HTTP_Request: ","code: "+response.body());

                MyRoutes.this.followersList = (ArrayList<User>) response.body();
                Log.v("HTTP_Request: ","followers list : "+followersList.toString());

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(MyRoutes.this.username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFollowersList(username);
                    }
                }, 500);
                return;


            }
        });

    }
    public void  getReceivedOffers(){

        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<List<ReceivedOffers>> call = tabadolAPI.getReceivedOffers(headers);

        call.enqueue(new Callback<List<ReceivedOffers>>() {
            @Override
            public void onResponse(Call<List<ReceivedOffers>> call, Response<List<ReceivedOffers>> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
                Log.v("HTTP_Request: ","code: "+response.body());

            }

            @Override
            public void onFailure(Call<List<ReceivedOffers>> call, Throwable t) {
    Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getReceivedOffers();
                    }
                }, 500);
                return;

            }
        });

    }
    public void getSentOffers(){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<List<SentOffers>> call = tabadolAPI.getSentOffers(headers);
        call.enqueue(new Callback<List<SentOffers>>() {
            @Override
            public void onResponse(Call<List<SentOffers>> call, Response<List<SentOffers>> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
                Log.v("HTTP_Request: ","code: "+response.body());


            }

            @Override
            public void onFailure(Call<List<SentOffers>> call, Throwable t) {
     Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSentOffers();
                    }
                }, 500);
                return;

            }
        });

    }

    public void getFinishedOffers(){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);
        Call<List<FinishedOffers>> call = tabadolAPI.getFinishedOffers(headers);

        call.enqueue(new Callback<List<FinishedOffers>>() {
            @Override
            public void onResponse(Call<List<FinishedOffers>> call, Response<List<FinishedOffers>> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
                Log.v("HTTP_Request: ","code: "+response.body());

            }

            @Override
            public void onFailure(Call<List<FinishedOffers>> call, Throwable t) {
    Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFinishedOffers();
                    }
                }, 500);
                return;


            }
        });




    }

    public void signup(SignupForm formFields){

//
//        headers = new HashMap<>();
//        jwt = getJwtFormSharedPreferences();
//
//        headers.put("Authorization", "Bearer "+jwt);



       Call<ResponseJson> call = tabadolAPI.signup(formFields);

       call.enqueue(new Callback<ResponseJson>() {
           @Override
           public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
               Log.v("HTTP_Request: ","code: "+response.code());
               ResponseJson resJson = response.body();
//               Log.v("HTTP_Request: ","response : "+resJson.getMessage());

               if(response.isSuccessful()){
                   Toast.makeText(context,"Registered Successfully!",Toast.LENGTH_LONG).show();
                   Intent loginIntent = new Intent(context, LoginActivity.class);
                   context.startActivity(loginIntent);
               }
               else{
                   Toast.makeText(context,"Email or Username is already exist!",Toast.LENGTH_LONG).show();
               }

           }

           @Override
           public void onFailure(Call<ResponseJson> call, Throwable t) {

                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                      signup(formFields);
                   }
               }, 500);
               return;

           }
       });
    }

    public void EditProfile(String firstname, String lastname, String skills, String bio, String phone, String image){

        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<User> call = tabadolAPI.EditProfile(headers, new EditProfileForm(firstname, lastname, skills, bio, phone, image));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.v("HTTP_Request: ", "code: " + response.code());
                Log.v("HTTP_Request: ", "response : " + response.body().toString());


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(MyRoutes.this.username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EditProfile(firstname, lastname, skills, bio, phone, image);
                    }
                }, 500);
                return;


            }
        });




    }

    public void FollowUser(String username){

        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<ResponseJson> call = tabadolAPI.FollowUser(headers, username);
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
                       FollowUser(username);
                    }
                }, 500);
                return;

            }
        });

    }


    public void UnfollowUser(String username){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);
        Call<ResponseJson> call = tabadolAPI.UnfollowUser(headers, username);
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
                       UnfollowUser(username);
                    }
                }, 500);
                return;

            }
        });

    }

    public void RateUser(String username,int rateValue){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<ResponseJson> call = tabadolAPI.RateUser(headers, username, new RateUser(rateValue));
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
                        RateUser(username, rateValue);
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
       return this.posts;
    }

//////
    public ArrayList<User> getAllUsers2(){
        return this.allUsers;
    }

    public ArrayList<User> getFollowingList2(){
        return this.followingList;
    }

    public ArrayList<User> getFollowersList2(){
        return this.followersList;
    }

    public String getUsername() {
        return username;
    }

    public User getUser2(){
       return this.user;
    }
}
