package com.trungnguyeen.orderfood.food_feature.presenter;

import com.trungnguyeen.orderfood.data.model.Food;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/26/18.
 */

public interface SendDataToMenuFoodView {
    void sendDataToMenuView(ArrayList<Food> foods);
    void sendErrorToView(int error501);
}
