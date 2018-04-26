package com.trungnguyeen.orderfood.food_feature.model;

import android.util.Log;

import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.data.model.response.FoodListResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.food_feature.presenter.SendDataToMenuFoodView;
import com.trungnguyeen.orderfood.utils.ApiUtils;
import com.trungnguyeen.orderfood.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trungnguyeen on 4/26/18.
 */

public class MenuModel {

    private final static String TAG = MenuModel.class.getSimpleName();
    private SendDataToMenuFoodView presenterListener;
    private SOService mService = ApiUtils.getSOService();

    public MenuModel() {}


    public void setPresenterListener(SendDataToMenuFoodView presenterListener) {
        this.presenterListener = presenterListener;
    }

    public void fetchMenuFood() {
        mService.getFoodList().enqueue(new Callback<FoodListResponse>() {
            @Override
            public void onResponse(Call<FoodListResponse> call, Response<FoodListResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().getStatus() == Constants.STATUS_REST_DATA){
                        ArrayList<Food> foods = response.body().getFoods();
                        Log.i(TAG, "onResponse: isSuccessful foodsSize " + foods.size());
                        presenterListener.sendDataToMenuView(foods);
                    }
                    else{
                        //Request no successful
                        Log.e(TAG, "onResponse: 501");
                        presenterListener.sendErrorToView(Constants.ERROR_501);
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodListResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                presenterListener.sendErrorToView(Constants.ERROR_501);
            }
        });
    }
}
