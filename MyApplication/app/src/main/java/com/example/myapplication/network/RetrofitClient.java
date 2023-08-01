package com.example.myapplication.network;

import com.example.myapplication.R;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static volatile RetrofitClient mInstance;
    private static final String BASE_URL = "http://192.168.233.152:8080/";
    private Retrofit retrofit;

    private RetrofitClient(){

    }

    public static RetrofitClient getInstance() {
        if(mInstance == null){
            synchronized (RetrofitClient.class){
                if(mInstance == null){
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    public <T> T getService(Class<T> tClass){
        return getRetrofit().create(tClass);
    }

    private synchronized Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
