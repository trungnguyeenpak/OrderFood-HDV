package com.trungnguyeen.orderfood.main_features.model;

import android.content.Context;
import android.util.Log;

import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.data.model.response.FoodListResponse;
import com.trungnguyeen.orderfood.data.model.response.TableListResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.main_features.presenter.FoodPresenterListener;
import com.trungnguyeen.orderfood.main_features.presenter.TablePresenterListener;
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
    private FoodPresenterListener foodCallback;
    private TablePresenterListener tableCallback;
    private SOService mService = ApiUtils.getSOService();

    public Model(Context context) {
        this.context = context;
    }

    public void setFoodCallback(FoodPresenterListener foodCallback) {
        this.foodCallback = foodCallback;
    }

    public void setTableCallback(TablePresenterListener tableCallback) {
        this.tableCallback = tableCallback;
    }

    public void loadFoodDataFromServer() {
        mService.getFoodList().enqueue(new Callback<FoodListResponse>() {
            @Override
            public void onResponse(Call<FoodListResponse> call, Response<FoodListResponse> response) {
                Log.e(TAG, "onResponse: getFoodList code: " + response.code());
                FoodListResponse foodListResponse = response.body();
                if (foodListResponse != null){ //response has data
                    if(foodListResponse.getStatus() == Constants.STATUS_REST_DATA){
                        ArrayList<Food> foods = foodListResponse.getFoods();
                        foodCallback.onSuccessResponseData(foods);
                    }
                }
                else{ //response no data
                    try {
                        Log.e(TAG, "onResponse: getFoodList: " + response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    foodCallback.onFailedData();
                }
            }

            @Override
            public void onFailure(Call<FoodListResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Error " + t.getMessage().toString());
                foodCallback.onFailedData();
            }
        });
    }


    public void loadTableDataFromServer(){
        mService.getTableList().enqueue(new Callback<TableListResponse>() {
            @Override
            public void onResponse(Call<TableListResponse> call, Response<TableListResponse> response) {
                Log.e(TAG, "onResponse: getTableList code: " + response.code());
                TableListResponse tableListResponse = response.body();
                if (tableListResponse != null){ //response has data
                    //Check status of result
                    if(tableListResponse.getStatus() == Constants.STATUS_REST_DATA){ //status: 202
                        ArrayList<Table> tables = tableListResponse.getTables();
                        tableCallback.onSuccessResponseTableData(tables);
                    }
                }
                else{ //response no data
                    try {
                        Log.e(TAG, "onResponse: getTableList: " + response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tableCallback.onFailedTableData();
                }
            }

            @Override
            public void onFailure(Call<TableListResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Error " + t.getMessage().toString());
                tableCallback.onFailedTableData();
            }
        });
    }
}
