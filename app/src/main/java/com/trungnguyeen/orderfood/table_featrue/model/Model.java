package com.trungnguyeen.orderfood.table_featrue.model;//package com.trungnguyeen.orderfood.main_features.model;

import android.content.Context;
import android.util.Log;

import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.data.model.response.BillResponse;
import com.trungnguyeen.orderfood.data.model.response.OrderResponse;
import com.trungnguyeen.orderfood.data.model.response.TableListResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.table_featrue.presenter.PresenterThanhToanListener;
import com.trungnguyeen.orderfood.table_featrue.presenter.TablePresenterListener;
import com.trungnguyeen.orderfood.table_featrue.presenter.PresenterTinhTienListener;
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
    private TablePresenterListener tablePresenterListener;
    private PresenterTinhTienListener presenterTinhTienListener;
    private PresenterThanhToanListener thanhToanListener;

    private SOService mService = ApiUtils.getSOService();

    public Model(Context context) {
        this.context = context;
    }

    public void setTablePresenterListener(TablePresenterListener callback) {
        this.tablePresenterListener = callback;
    }

    public void setPresenterTinhTienListener(PresenterTinhTienListener callback){
        this.presenterTinhTienListener = callback;
    }

    public void setThanhToanListener(PresenterThanhToanListener thanhToanListener) {
        this.thanhToanListener = thanhToanListener;
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
                        tablePresenterListener.onSuccessResponseTableData(tables);
                    }
                }
                else{ //response no data
                    try {
                        Log.e(TAG, "onResponse: getTableList: " + response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tablePresenterListener.onFailedTableData(Constants.NO_TABLE);
                }
            }

            @Override
            public void onFailure(Call<TableListResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Error " + t.getMessage().toString());
                tablePresenterListener.onFailedTableData(Constants.NO_TABLE);
            }
        });
    }

    public void requestOrderWithTableID(int tableid) {
        Log.e(TAG, "requestOrderWithTableID: " + tableid);
        mService.getOrderWithTableId(tableid).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().getStatus() == Constants.STATUS_REST_DATA){
                        Order order = response.body().getOrder();
                        //Log.e("ThanhToan", "posts loaded from API" + order.getOrderDetailList().size());
                        presenterTinhTienListener.sendOrderFoodToView(order);
                    }
                }else {
                    int statusCode  = response.code();
                    Log.e("ThanhToan", "onResponse: " + statusCode);
                    // handle request errors depending on status code
                    presenterTinhTienListener.sendErrorToView(Constants.ERROR_501);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("ThanhToan", "error loading from API");
                Log.e("ThanhToan", "onFailure: " + t.getMessage() );
                presenterTinhTienListener.sendErrorToView(Constants.ERROR_501);
            }
        });
    }

    public void requestThanhToanWithOrderid(int id){
        mService.thanhToanTienWithOrderId(id).enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().getStatus() == Constants.STATUS_REST_DATA){
                        int tongTien = response.body().getData();
                        thanhToanListener.sendTongTien(tongTien);
                    }
                }else {
                    int statusCode  = response.code();
                    Log.e("ThanhToan", "onResponse: " + statusCode);
                    thanhToanListener.sendError(Constants.ERROR_501);
                }
            }

            @Override
            public void onFailure(Call<BillResponse> call, Throwable t) {
                Log.e("ThanhToan", "error loading from API");
                Log.e("ThanhToan", "onFailure: " + t.getMessage() );
                thanhToanListener.sendError(Constants.ERROR_501);
            }
        });
    }
}
