package com.trungnguyeen.orderfood.table_featrue.model;

import android.util.Log;

import com.trungnguyeen.orderfood.data.model.Employee;
import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.data.model.SingletonUser;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.data.model.response.OrderResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.table_featrue.presenter.PresenterCreateOrderListener;
import com.trungnguyeen.orderfood.utils.ApiUtils;
import com.trungnguyeen.orderfood.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trungnguyeen on 4/26/18.
 */

public class CreateOderModel {

    private PresenterCreateOrderListener mListener;
    private SOService mService = ApiUtils.getSOService();
    private Employee mUser = SingletonUser.getInstance().getEmployee();
    private final  static String TAG = CreateOderModel.class.getSimpleName();

    public CreateOderModel() {
    }

    public void setmListener(PresenterCreateOrderListener mListener) {
        this.mListener = mListener;
    }

    public void createOrderWithTable(Table table) {
        Log.i(TAG, "createOrderWithTable: employee + " + mUser.getLastName());
        mService.createOrderWithTable(mUser.getId(), table.getId()).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus() == Constants.CREATE_SUCCESSFUL){
                        Order order = response.body().getOrder();
                        Log.i(TAG, "onResponse: created orderid " + order.getId());
                        mListener.getOrderSuccessfuly(order);
                    }
                    else{
                        Log.e(TAG, "onResponse: " + response.body().getMessage());
                        mListener.getOrderFailure(Constants.ERROR_501);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                mListener.getOrderFailure(Constants.ERROR_501);
            }
        });
    }

    public void getOrderWithTable(Table table) {
        mService.getOrderWithTableId(table.getId()).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().getStatus() == Constants.STATUS_REST_DATA){
                        Order order = response.body().getOrder();
                        //Log.e("ThanhToan", "posts loaded from API" + order.getOrderDetailList().size());
                        mListener.getOrderSuccessfuly(order);
                    }
                    else {
                        int statusCode  = response.code();
                        Log.e("ThanhToan", "onResponse: " + statusCode);
                        // handle request errors depending on status code
                        mListener.getOrderFailure(Constants.ERROR_501);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("ThanhToan", "error loading from API");
                Log.e("ThanhToan", "onFailure: " + t.getMessage() );
                mListener.getOrderFailure(Constants.ERROR_501);
            }
        });
    }
}
