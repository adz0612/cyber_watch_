package com.example.cyberwatchdog.api;

import com.example.cyberwatchdog.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

@GET("everything")
    Call<News> getNews(

        @Query("q") String q,
        @Query("apiKey") String apiKey


);




}
