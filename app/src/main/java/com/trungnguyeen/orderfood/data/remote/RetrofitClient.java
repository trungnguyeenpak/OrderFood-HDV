package com.trungnguyeen.orderfood.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by trungnguyeen on 4/5/18.
 */
//Singleton pattern
public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getRetrofitClient(String baseUrl){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
