package com.example.BackEnd;


import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static final String Kakao_Book_Search = "https://dapi.kakao.com";
    private static final String REST_API_KEY = "KakaoAK 87b525d50415af244a730e056f94461d";
    static Retrofit mRetrofit;
    private static Context mContext;

    public static Retrofit getRetrofit2() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.addHeader("Authorization", REST_API_KEY);
             //   requestBuilder.addHeader("Authorization", REST_API_KEY);
                return chain.proceed(requestBuilder.build());
            }
        });
        httpClient.addInterceptor(logging);


        mRetrofit = new Retrofit.Builder()
                .baseUrl(Kakao_Book_Search)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return mRetrofit;
    }

}
