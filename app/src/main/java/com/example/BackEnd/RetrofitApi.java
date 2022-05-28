package com.example.BackEnd;



import com.example.Model.SearchRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RetrofitApi {

    //kakaotestest

    @GET("/v3/search/book")
    Call<SearchRequest> getSearch(@Query("query") String query, @Query("size") int size, @Query("page") int page);



}
