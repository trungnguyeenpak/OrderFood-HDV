package com.trungnguyeen.orderfood.food_feature.view.interfaces;

import android.view.View;

import com.trungnguyeen.orderfood.data.model.Food;

/**
 * Created by trungnguyeen on 4/27/18.
 */

public interface OnClickFoodItem {
    void onClick(View v, int position, Food food);
}
