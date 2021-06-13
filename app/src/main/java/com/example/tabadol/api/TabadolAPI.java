package com.example.tabadol.api;

import java.net.ResponseCache;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;


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
}
