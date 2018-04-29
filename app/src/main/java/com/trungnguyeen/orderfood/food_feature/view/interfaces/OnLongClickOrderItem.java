package com.trungnguyeen.orderfood.food_feature.view.interfaces;

import android.view.View;

import com.trungnguyeen.orderfood.data.model.OrderDetailList;

/**
 * Created by trungnguyeen on 4/29/18.
 */

public interface OnLongClickOrderItem {
    void onLongClick(View v, int position, OrderDetailList orderDetail);
}
