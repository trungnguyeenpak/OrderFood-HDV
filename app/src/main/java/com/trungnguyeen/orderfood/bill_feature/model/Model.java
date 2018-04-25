package com.trungnguyeen.orderfood.bill_feature.model;

import android.content.Context;
import android.util.Log;

import com.trungnguyeen.orderfood.data.model.response.BillResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.table_featrue.presenter.PresenterThanhToanListener;
import com.trungnguyeen.orderfood.utils.ApiUtils;
import com.trungnguyeen.orderfood.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trungnguyeen on 4/25/18.
 */

public class Model {

    private Context context;
    private PresenterThanhToanListener mPresenterListener;
    private SOService mService = ApiUtils.getSOService();


    public Model(Context context) {
        this.context = context;
    }

    public void setmPresenterListener(PresenterThanhToanListener mPresenterListener) {
        this.mPresenterListener = mPresenterListener;
    }

    public void thanhToanTienWithOrderId(int id) {
        mService.thanhToanTienWithOrderId(id).enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().getStatus() == Constants.STATUS_REST_DATA){
                        int tongTien = response.body().getData();
                        mPresenterListener.sendTongTien(tongTien);
                    }
                }else {
                    int statusCode  = response.code();
                    Log.e("ThanhToan", "onResponse: " + statusCode);
                    mPresenterListener.sendError(Constants.ERROR_501);
                }
            }

            @Override
            public void onFailure(Call<BillResponse> call, Throwable t) {
                Log.e("ThanhToan", "error loading from API");
                Log.e("ThanhToan", "onFailure: " + t.getMessage() );
                mPresenterListener.sendError(Constants.ERROR_501);
            }
        });
    }
}
