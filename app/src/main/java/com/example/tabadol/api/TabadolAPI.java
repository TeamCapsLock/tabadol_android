package com.example.tabadol.api;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface TabadolAPI {




    @GET("jposts")
    Call<List<Post>> getPosts(@HeaderMap Map<String,String> headers);


    @GET("jmyprofile")
    Call<User> getLoggedInUser(@HeaderMap Map<String,String> headers);

    @POST("authenticate")
    Call<AuthenticationResponse> getJWT_token(@Body AuthenticationRequest authenticationRequest);


    @GET("jprofile/{id}")
    Call<User> getUser(@HeaderMap Map<String,String> headers, @Path("id") long id);


    @POST("testpostjson")
    Call<ResponseJson> testPostJson(@HeaderMap Map<String,String> headers, @Body AuthenticationRequest login);


///
    @GET("jallUsers")
    Call<List<User>> getAllUsers(@HeaderMap Map<String,String> headers);

    @GET("jfollowinglist/{username}")
    Call<List<User>> getFollowingList (@HeaderMap Map<String,String> headers, @Path("username") String username);

    @GET("jfollowerslist/{username}")
    Call<List<User>> getFollowersList (@HeaderMap Map<String,String> headers, @Path("username") String username);

    @GET("jreceivedoffers")
    Call<List<ReceivedOffers>> getReceivedOffers(@HeaderMap Map<String,String> headers);

    @GET("jsentoffers")
    Call<List<SentOffers>>  getSentOffers(@HeaderMap Map<String,String> headers);

    @GET("jacceptedoffers")
    Call <List<FinishedOffers>> getFinishedOffers(@HeaderMap Map<String,String> headers);


    @POST("jsignup")
    Call<ResponseJson> signup(@Body SignupForm signup);



    @POST("junfollow/{username}")
    Call<ResponseJson> UnfollowUser(@HeaderMap Map<String,String> headers,@Path("username") String username);

    // *********************************

    @POST("jfollow/{username}")
    Call<ResponseJson> FollowUser(@HeaderMap Map<String,String> headers,@Path("username") String username);

    @POST("jrate/{username}")
    Call<ResponseJson> RateUser(@HeaderMap Map<String,String> headers,@Path("username") String username, @Body RateUser rateValue);
// ******************************************

    @PUT("jedit-profile")
    Call<User> EditProfile(@HeaderMap Map<String,String> headers, @Body EditProfileForm editProfile);


    //
    @POST("jaddPost")
    Call<ResponseJson> addPost(@HeaderMap Map<String,String> headers, @Body AddPostForm addPostForm);

    @POST("jacceptoffer")
    Call<ResponseJson> acceptOffer(@HeaderMap Map<String,String> headers, @Body AcceptOffer acceptOffer);

    @POST("jdeclineoffer")
    Call<ResponseJson> declinedOffer(@HeaderMap Map<String,String> headers, @Body AcceptOffer acceptOffer);

    @POST("jexchange/{id}")
    Call<ResponseJson> exchangeOffer(@HeaderMap Map<String,String> headers,@Path("id") long id, @Body  ExchangeOffer exchange);

@DELETE("jdelete-post/{id}")
    Call<ResponseJson> deletePost(@HeaderMap Map<String,String> headers,@Path("id") long id);

@GET("jratedusers")
Call<RatedUsers> ratedUsers(@HeaderMap Map<String,String> headers);


}
