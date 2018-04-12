package com.trungnguyeen.orderfood.data.remote;

import com.trungnguyeen.orderfood.data.model.response.EmployeeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by trungnguyeen on 4/5/18.
 */

public interface SOService {

    @FormUrlEncoded
    @POST("auth/login.json?")
    Call<EmployeeResponse> userlogin(@Field("userName") String username,
                                     @Field("password") String password);

    @GET("employee/{userID}.json")
    Call<EmployeeResponse> getUserInfo(@Path("userID") int userID);
}
