package com.trungnguyeen.orderfood.features.presenter;

import android.content.Context;

import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.features.model.Model;
import com.trungnguyeen.orderfood.features.view.interfaces.FoodViewListener;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/12/18.
 */

public class Presenter implements PresenterListener {

    private Context context;
    private Model mModel;
    private FoodViewListener callback;

    public Presenter(Context context, FoodViewListener callback) {
        this.context = context;
        this.mModel = mModel;
        this.callback = callback;
    }

    public void requestData(){
        mModel = new Model(this.context, this);
        mModel.loadDataFromServer();
    }

    @Override
    public void onSuccessResponseData(ArrayList<Food> foods) {
        callback.onSuccessResponseData(foods);
    }

    @Override
    public void onFailedData() {
        callback.onFailedData();
    }
}
