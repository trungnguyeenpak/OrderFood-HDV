package com.trungnguyeen.orderfood.table_featrue.view.interfaces;

import com.trungnguyeen.orderfood.data.model.Table;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/18/18.
 */

public interface LoadTableListener {
    void onSuccessResponseData(ArrayList<Table> tables);
    void onFailedData(int errorCode);
//    void onResponeThanhTien(double tongTien);
}
