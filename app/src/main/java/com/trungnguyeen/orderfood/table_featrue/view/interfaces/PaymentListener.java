package com.trungnguyeen.orderfood.table_featrue.view.interfaces;

import com.trungnguyeen.orderfood.data.model.Order;

/**
 * Created by trungnguyeen on 4/23/18.
 */

public interface PaymentListener {
    void showOrderFood(Order order);
    void showError(int error);
}
