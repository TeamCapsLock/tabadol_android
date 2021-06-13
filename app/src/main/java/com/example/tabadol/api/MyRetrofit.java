package com.example.tabadol.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private String base_url;
    private Gson gson;
    private Retrofit retrofit;
    private static MyRetrofit retrofitInstance;



    private MyRetrofit(){
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://tabadol1.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized MyRetrofit getInstance() {
        if (retrofitInstance == null) {
           retrofitInstance= new MyRetrofit();
        }
        return retrofitInstance;
    }

    public TabadolAPI getApi(){
        return retrofit.create(TabadolAPI.class);
    }





}
