package com.trungnguyeen.orderfood.table_featrue.presenter;

import com.trungnguyeen.orderfood.data.model.Order;

/**
 * Created by trungnguyeen on 4/26/18.
 */

public interface PresenterCreateOrderListener {
    void getOrderSuccessfuly(Order order);
    void getOrderFailure(int error501);
}
