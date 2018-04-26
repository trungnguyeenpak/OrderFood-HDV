package com.trungnguyeen.orderfood.table_featrue.view.interfaces;

import com.trungnguyeen.orderfood.data.model.Table;

/**
 * Created by trungnguyeen on 4/22/18.
 */

public interface BottomSheetItemClick {
    void onClickItemThanhToan(Table table);
    void onClickOrder(Table table);
}