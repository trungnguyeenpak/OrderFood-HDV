package com.trungnguyeen.orderfood.food_feature.presenter;

import com.trungnguyeen.orderfood.data.model.OrderDetailList;
import com.trungnguyeen.orderfood.food_feature.model.OrderFoodModel;
import com.trungnguyeen.orderfood.food_feature.view.interfaces.ShowOrderFood;

import java.util.List;

/**
 * Created by trungnguyeen on 4/29/18.
 */

public class OrderFoodPresenter implements SendOrderFoodToView {

    private OrderFoodModel mModel;
    private ShowOrderFood mListener;

    public OrderFoodPresenter() {
        this.mModel = new OrderFoodModel();
    }

    public void setmListener(ShowOrderFood mListener) {
        this.mListener = mListener;
    }

    public void fetchOrderFoodFromServer(int orderId) {
        mModel.fetchOrderFood(orderId);
        mModel.setmListener(this);
    }

    @Override
    public void sentOrderFoodToView(List<OrderDetailList> orderDetailList) {
        mListener.updateOrderFood(orderDetailList);
    }

    @Override
    public void sendErrorToView(int error501) {
        mListener.showErrorWithCode(error501);
    }
}
