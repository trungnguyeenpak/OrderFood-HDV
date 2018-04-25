package com.trungnguyeen.orderfood.table_featrue.presenter;

import android.content.Context;

import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.table_featrue.model.Model;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.LoadTableListener;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.PaymentListener;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.ThanhToanListener;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/12/18.
 */

public class Presenter implements TablePresenterListener, PresenterTinhTienListener, PresenterThanhToanListener {

    private Context context;
    private Model mModel;
    private LoadTableListener loadTableListener;
    private PaymentListener tinhTienListener;
    private ThanhToanListener thanhToanListener;

    public Presenter(Context context) {
        this.context = context;
        this.mModel = new Model(this.context);
    }

    public void setLoadTableCallBack(LoadTableListener callback) {
        this.loadTableListener = callback;
    }

    public void setTinhTienListener(PaymentListener callback){
        this.tinhTienListener = callback;
    }

    public void setThanhToanListener(ThanhToanListener thanhToanListener) {
        this.thanhToanListener = thanhToanListener;
    }

    public void requestTableData() {
        mModel.loadTableDataFromServer();
        mModel.setTablePresenterListener(this);
    }

    @Override
    public void onSuccessResponseTableData(ArrayList<Table> tables) {
        loadTableListener.onSuccessResponseData(tables);
    }

    @Override
    public void onFailedTableData(int errorCode) {
        loadTableListener.onFailedData(errorCode);
    }

    public void requestOrderFoodWithTableId(int tableid){
        mModel.requestOrderWithTableID(tableid);
        mModel.setPresenterTinhTienListener(this);
    }

    @Override
    public void sendOrderFoodToView(Order order) {
        tinhTienListener.showOrderFood(order);
    }

    @Override
    public void sendErrorToView(int error) {
        tinhTienListener.showError(error);
    }

    public void thanhToanWithOrderId(int id) {
        mModel.requestThanhToanWithOrderid(id);
        mModel.setThanhToanListener(this);
    }

    @Override
    public void sendTongTien(int tongTien) {
        thanhToanListener.showBill(tongTien);
    }

    @Override
    public void sendError(int code) {
        thanhToanListener.showError(code);
    }
}
