package com.trungnguyeen.orderfood.main_features.view.interfaces;

import com.trungnguyeen.orderfood.data.model.Food;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/12/18.
 */

public interface FoodViewListener {
    void onSuccessResponseData(ArrayList<Food> foods);
    void onFailedData();
}
