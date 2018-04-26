package com.trungnguyeen.orderfood.food_feature.view.interfaces;

import com.trungnguyeen.orderfood.data.model.Food;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/26/18.
 */

public interface ShowMenuFood {
    void updateMenuFoodFormData(ArrayList<Food> foods);
    void showErrorWithCode(int error501);
}
