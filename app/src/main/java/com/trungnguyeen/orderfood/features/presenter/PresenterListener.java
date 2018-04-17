package com.trungnguyeen.orderfood.features.presenter;

import com.trungnguyeen.orderfood.data.model.Food;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/12/18.
 */

public interface PresenterListener {
    void onSuccessResponseData(ArrayList<Food> foods);
    void onFailedData();
}
