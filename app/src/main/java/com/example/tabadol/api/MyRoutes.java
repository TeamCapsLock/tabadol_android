package com.example.tabadol.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.tabadol.HomeActivity;
import com.example.tabadol.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private List<Long> myFollowingIds = null;
    private ArrayList<Post> posts = null;
    private ArrayList<User> allUsers = null;
    private ArrayList<User> followingList = null;
    private ArrayList<User> followersList = null;
    private List<Long> ratedUsersids= null;
    private ArrayList<Offer> sentOffers = null;
    private ArrayList<Offer> receivedOffers = null;
    private List<Offer> finishedOffers = null;
    private long loggedInId;
    private  boolean isLoggedIn = false;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    private static MyRoutes myRoutesInstanse = null;

   private MyRoutes(Context context){
       this.retrofit = MyRetrofit.getInstance();
       this.tabadolAPI = retrofit.getApi();
       this.context = context;
       username = getUsernameFormSharedPreferences();
       password = getPasswordFormSharedPreferences();
       jwt = getJwtFormSharedPreferences();
       if(username != null && password != null)
           getJWT_token(username,password);


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

        this.posts = null;
        this.allUsers = null;
        this.ratedUsersids = null;
        this.username = null;
        this.password = null;
        this.user = null;
        this.myFollowingIds = null;
        this.followingList = null;
        this.followersList = null;
        this.sentOffers = null;
        this.receivedOffers = null;
        this.finishedOffers = null;
        this.user = null;
        this.loggedInId = -1;
        this.isLoggedIn = false;

        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public  void getJWT_token(String username, String password){

       if(username == null || password == null)
           return;

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

                    getPosts();
                    getAllUsers();
                    getFollowingList(username);
                    getRatedUsers();
                    getSentOffers();
                    getFinishedOffers();
                    getReceivedOffers();
                    getLoggedInUser();

                    isLoggedIn = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        context.startActivity(homeIntent);
                    }
                }, 4000);


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
        if(jwt == null)
            return;

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
        if(jwt == null)
            return;
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
                loggedInId = user.getId();
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

        if(jwt == null)
            return;

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

        if(jwt == null)
            return;

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
                if(MyRoutes.this.username != null && MyRoutes.this.username.equals(username)){
                    myFollowingIds = response.body().stream().map(u -> u.getId()).collect(Collectors.toList());
                }
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
//     public void signup(String username, String email, String firstname, String lastname, String password, String confirm, String skills, String bio, String phone, String image){

// //
// //        headers = new HashMap<>();
// //        jwt = getJwtFormSharedPreferences();
// //
// //        headers.put("Authorization", "Bearer "+jwt);



//         Call<ResponseJson> call = tabadolAPI.signup(new SignupForm(username,email,firstname,lastname, password,confirm,skills,bio,phone,image));

//         call.enqueue(new Callback<ResponseJson>() {
//             @Override
//             public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
//                 Log.v("HTTP_Request: ","code: "+response.code());
//                 ResponseJson resJson = response.body();
//                 Log.v("HTTP_Request: ","response : "+resJson.getMessage());
//             }

//             @Override
//             public void onFailure(Call<ResponseJson> call, Throwable t) {

//                 Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());

//                 new Handler().postDelayed(new Runnable() {
//                     @Override
//                     public void run() {
//                         signup(username,email,firstname,lastname, password,confirm,skills,bio,phone,image);
//                     }
//                 }, 500);
//                 return;

//             }
//         });
//     }


    public void  getReceivedOffers(){

        if(jwt == null)
            return;

        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<List<ReceivedOffers>> call = tabadolAPI.getReceivedOffers(headers);

        call.enqueue(new Callback<List<ReceivedOffers>>() {
            @Override
            public void onResponse(Call<List<ReceivedOffers>> call, Response<List<ReceivedOffers>> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
                Log.v("HTTP_Request: ","code: "+response.body());


                List<ReceivedOffers> receivedOffers2 = response.body();
                List<Offer> offers2 = new ArrayList<>();

                for(ReceivedOffers offer: receivedOffers2){
                    Post destPost = offer.getPostHasReceivedTheOffers();

                    for(Post post: offer.getTheReceivedOffersForOnePost()){
                        Post sourcePost = post;
                        Offer newOffer = new Offer(sourcePost, destPost);
                        offers2.add(newOffer);
                    }
                }
                MyRoutes.this.receivedOffers = (ArrayList<Offer>) offers2;

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
        if(jwt == null){
            return;
        }
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);

        Call<List<SentOffers>> call = tabadolAPI.getSentOffers(headers);
        call.enqueue(new Callback<List<SentOffers>>() {
            @Override
            public void onResponse(Call<List<SentOffers>> call, Response<List<SentOffers>> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
                Log.v("HTTP_Request: ","code: "+response.body());
                List<SentOffers> sent = response.body();
                List<Offer> offers2 = new ArrayList<>();

                for(SentOffers s: sent){
                    Post sourcePost = s.getPostThatSentTheOffers();
                    for(Post post: s.getPostsThatReceivedTheSentOffer()){
                        Post destinationPost = post;
                        Offer offer = new Offer(sourcePost, destinationPost);
                        offers2.add(offer);
                    }
                }
                MyRoutes.this.sentOffers = (ArrayList<Offer>) offers2;


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

        if(jwt == null)
            return;


        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();

        headers.put("Authorization", "Bearer "+jwt);
        Call<List<FinishedOffers>> call = tabadolAPI.getFinishedOffers(headers);

        call.enqueue(new Callback<List<FinishedOffers>>() {
            @Override
            public void onResponse(Call<List<FinishedOffers>> call, Response<List<FinishedOffers>> response) {
                Log.v("HTTP_Request: ","code: "+response.code());
                Log.v("HTTP_Request: ","code: "+response.body());

                List<FinishedOffers> currentFinishedOffers = response.body();

                List<Offer> offers2 = new ArrayList<>();

                for ( FinishedOffers offer : currentFinishedOffers){


                    Post destPost = offer.getPostHasReceivedTheOffers();
                    if(destPost != null){
                        for(Post post : offer.getTheReceivedOffersForOnePost()){
                            Post sourcePost = post;
                            Offer newOffer = new Offer(sourcePost, destPost);
                            offers2.add(newOffer);
                        }
                    }
                    else{
                        Post sourcePost2 =  offer.getPostThatSentTheOffers();
                        for(Post post : offer.getPostsThatReceivedTheSentOffer()){
                            Post destPost2 = post;
                            Offer newOffer = new Offer(sourcePost2, destPost2);
                            offers2.add(newOffer);
                        }
                    }

                }
                MyRoutes.this.finishedOffers = offers2;

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

    public void exchangeOffer(long id, long myPostId){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();
        headers.put("Authorization", "Bearer "+jwt);

        Call<ResponseJson> call = tabadolAPI.exchangeOffer(headers, id, new ExchangeOffer(myPostId));
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
                        exchangeOffer(id,myPostId);
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
    public void addPost( String body, String category, String type, Integer weight){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();
        headers.put("Authorization", "Bearer "+jwt);

        Call<ResponseJson> call = tabadolAPI.addPost(headers, new AddPostForm(body,category,type,weight));
        call.enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                Log.v("HTTP_Request: ", "code: " + response.code());
                Log.v("HTTP_Request: ", "response : " + response.body().toString());

            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(MyRoutes.this.username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       addPost(body,category,type,weight);
                    }
                }, 500);
                return;

            }
        });


    }
    public void acceptOffer(long source_id, long destination_id){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();
        headers.put("Authorization", "Bearer "+jwt);

        Call<ResponseJson> call = tabadolAPI.acceptOffer(headers, new AcceptOffer(source_id,destination_id));
        call.enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                Log.v("HTTP_Request: ", "code: " + response.code());

                Log.v("HTTP_Request: ", "response : " + response.body().toString());

            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(MyRoutes.this.username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       acceptOffer(source_id,destination_id);
                    }
                }, 500);
                return;


            }
        });


    }
    public void declinedOffer(long source_id, long destination_id){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();
        headers.put("Authorization", "Bearer "+jwt);

        Call<ResponseJson> call = tabadolAPI.declinedOffer(headers, new AcceptOffer(source_id,destination_id));
        call.enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                Log.v("HTTP_Request: ", "code: " + response.code());
                Log.v("HTTP_Request: ", "response : " + response.body().toString());

            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());
                getJWT_token(MyRoutes.this.username,password);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        declinedOffer(source_id,destination_id);
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
    public void deletePost(long id){
        headers = new HashMap<>();
        jwt = getJwtFormSharedPreferences();
        headers.put("Authorization", "Bearer "+jwt);

        Call<ResponseJson> call = tabadolAPI.deletePost(headers,id);

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
                           deletePost(id);
                        }
                    }, 500);
                    return;

                }
            });
        }
    public void getRatedUsers(){

            if(jwt == null)
                return;

            headers = new HashMap<>();
            jwt = getJwtFormSharedPreferences();
            headers.put("Authorization", "Bearer "+jwt);

            Call<RatedUsers> call = tabadolAPI.ratedUsers(headers);
            call.enqueue(new Callback<RatedUsers>() {
                @Override
                public void onResponse(Call<RatedUsers> call, Response<RatedUsers> response) {
                    Log.v("HTTP_Request: ","code: "+response.code());
                    Log.v("HTTP_Request: ","response : "+response.body());
                    ratedUsersids = response.body().getRatedUsers();


                }
                @Override
                public void onFailure(Call<RatedUsers> call, Throwable t) {
                    Log.e("HTTP_Request: ","Request failed.. something wrong in your request !  \n"+t.getMessage());

                    getJWT_token(MyRoutes.this.username, MyRoutes.this.password);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getRatedUsers();
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
    public List<Long> getRatedUsers2(){
       return this.ratedUsersids;
    }

    public List<Long> getMyFollowingIds2(){
       return this.myFollowingIds;
    }

    public ArrayList<Offer> getSentOffers2(){
        return this.sentOffers;
    }

    public List<Offer> getFinishedOffers2(){
        return this.finishedOffers;
    }

    public List<Offer> getReceivedOffers2(){
        return this.receivedOffers;
    }

    public Long getLoggedInID(){
       return loggedInId;
    }
}
