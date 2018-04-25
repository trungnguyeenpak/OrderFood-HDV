package com.trungnguyeen.orderfood.bill_feature.presenter;

import android.content.Context;

import com.trungnguyeen.orderfood.bill_feature.model.Model;
import com.trungnguyeen.orderfood.table_featrue.presenter.PresenterThanhToanListener;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.ThanhToanListener;

/**
 * Created by trungnguyeen on 4/25/18.
 */

public class Presenter implements PresenterThanhToanListener {

    private Context context;
    private Model mModel;
    private ThanhToanListener mThanhToan;

    public Presenter(Context context) {
        this.context = context;
        this.mModel = new Model(context);
    }

    public void setmThanhToan(ThanhToanListener mThanhToan) {
        this.mThanhToan = mThanhToan;
    }

    public void thanhToanWithOrderId(int id) {
        mModel.setmPresenterListener(this);
        mModel.thanhToanTienWithOrderId(id);
    }

    @Override
    public void sendTongTien(int tongTien) {
        mThanhToan.showBill(tongTien);
    }

    @Override
    public void sendError(int code) {
        mThanhToan.showError(code);
    }
}

