package com.trungnguyeen.orderfood.table_featrue.presenter;

import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.table_featrue.model.CreateOderModel;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.CreateOrderViewListener;

/**
 * Created by trungnguyeen on 4/26/18.
 */

public class CreateOrderPresenter implements PresenterCreateOrderListener {

    private CreateOderModel mModel;
    private CreateOrderViewListener mListener;

    public CreateOrderPresenter() {
        this.mModel = new CreateOderModel();
        mModel.setmListener(this);
    }

    public void setmListener(CreateOrderViewListener mListener) {
        this.mListener = mListener;
    }

    public void createOrderWithTable(Table table){
        mModel.createOrderWithTable(table);
    }

    public void getOrderWithTable(Table table) {
        mModel.getOrderWithTable(table);
    }

    @Override
    public void getOrderSuccessfuly(Order order) {
        mListener.returnOrderToView(order);
    }

    @Override
    public void getOrderFailure(int error501) {
        mListener.onFailureGetOrder(error501);
    }


}
