package com.trungnguyeen.orderfood.food_feature.model;

import android.util.Log;

import com.trungnguyeen.orderfood.data.model.OrderDetailList;
import com.trungnguyeen.orderfood.data.model.response.OrderResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.food_feature.presenter.SendOrderFoodToView;
import com.trungnguyeen.orderfood.utils.ApiUtils;
import com.trungnguyeen.orderfood.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trungnguyeen on 4/29/18.
 */

public class OrderFoodModel {

    private final static String TAG = OrderFoodModel.class.getSimpleName();
    private SendOrderFoodToView presenterListener;
    private SOService mService = ApiUtils.getSOService();

    public OrderFoodModel() {

    }

    public void setmListener(SendOrderFoodToView presenterListener) {
        this.presenterListener = presenterListener;
    }


    public void fetchOrderFood(int orderId) {
        mService.getOrderWithId(orderId).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus() == Constants.STATUS_REST_DATA){
                        List<OrderDetailList> orderDetailList = response.body().getOrder().getOrderDetailList();
                        Log.i(TAG, "onResponse: isSuccessful order");
                        presenterListener.sentOrderFoodToView(orderDetailList);
                    }
                    else{
                        //Request no successful
                        Log.e(TAG, "onResponse: 501");
                        presenterListener.sendErrorToView(Constants.ERROR_501);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                presenterListener.sendErrorToView(Constants.ERROR_501);
            }
        });
    }
}
