package com.trungnguyeen.orderfood.table_featrue.presenter;

import com.trungnguyeen.orderfood.data.model.Order;

/**
 * Created by trungnguyeen on 4/23/18.
 */

public interface PresenterTinhTienListener {
    void sendOrderFoodToView(Order order);
    void sendErrorToView(int error);
}
