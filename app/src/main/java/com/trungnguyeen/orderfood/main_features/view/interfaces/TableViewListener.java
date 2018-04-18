package com.trungnguyeen.orderfood.main_features.view.interfaces;

import com.trungnguyeen.orderfood.data.model.Table;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/18/18.
 */

public interface TableViewListener {
    void onSuccessResponseData(ArrayList<Table> foods);
    void onFailedData();
}
