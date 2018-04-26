package com.trungnguyeen.orderfood.table_featrue.view.interfaces;

import com.trungnguyeen.orderfood.data.model.Order;

/**
 * Created by trungnguyeen on 4/26/18.
 */

public interface CreateOrderViewListener {
    void returnOrderToView(Order order);
    void onFailureGetOrder(int error501);
}
