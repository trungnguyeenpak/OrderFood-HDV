package com.trungnguyeen.orderfood.main_features.presenter;

import com.trungnguyeen.orderfood.data.model.Table;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/18/18.
 */

public interface TablePresenterListener {
    void onSuccessResponseTableData(ArrayList<Table> foods);
    void onFailedTableData();
}
