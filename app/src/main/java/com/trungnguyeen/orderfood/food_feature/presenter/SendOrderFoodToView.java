package com.trungnguyeen.orderfood.food_feature.presenter;

import com.trungnguyeen.orderfood.data.model.OrderDetailList;

import java.util.List;

/**
 * Created by trungnguyeen on 4/29/18.
 */

public interface SendOrderFoodToView {
    void sentOrderFoodToView(List<OrderDetailList> orderDetailList);
    void sendErrorToView(int error501);

}
