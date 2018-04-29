package com.trungnguyeen.orderfood.food_feature.view.interfaces;

import com.trungnguyeen.orderfood.data.model.OrderDetailList;

import java.util.List;

/**
 * Created by trungnguyeen on 4/29/18.
 */

public interface ShowOrderFood {
    void updateOrderFood(List<OrderDetailList> orderDetailList);
    void showErrorWithCode(int error501);
}
