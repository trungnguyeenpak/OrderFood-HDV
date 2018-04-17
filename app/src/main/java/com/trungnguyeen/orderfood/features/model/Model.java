package com.trungnguyeen.orderfood.features.model;

import android.content.Context;
import android.util.Log;

import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.data.model.response.FoodListResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.features.presenter.PresenterListener;
import com.trungnguyeen.orderfood.utils.ApiUtils;
import com.trungnguyeen.orderfood.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trungnguyeen on 4/12/18.
 */

public class Model {

    private final static String TAG = Model.class.getSimpleName();
    private Context context;
    private PresenterListener callback;
    private SOService mService = ApiUtils.getSOService();

    public Model(Context context, PresenterListener callback) {
        this.context = context;
        this.callback = callback;
    }

    public void loadDataFromServer() {
        mService.getFoodList().enqueue(new Callback<FoodListResponse>() {
            @Override
            public void onResponse(Call<FoodListResponse> call, Response<FoodListResponse> response) {
                Log.e(TAG, "onResponse: " + response.code());
                FoodListResponse foodListResponse = response.body();
                if (foodListResponse != null){ //response has data
                    if(foodListResponse.getStatus() == Constants.STATUS_REST_DATA){
                        ArrayList<Food> foods = foodListResponse.getFoods();
                        callback.onSuccessResponseData(foods);
                    }
                }
                else{ //response no data
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onFailedData();
                }
            }

            @Override
            public void onFailure(Call<FoodListResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Error " + t.getMessage().toString());
                callback.onFailedData();
            }
        });
    }
}
