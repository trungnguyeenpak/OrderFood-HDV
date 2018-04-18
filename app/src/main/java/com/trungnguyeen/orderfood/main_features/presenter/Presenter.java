package com.trungnguyeen.orderfood.main_features.presenter;

import android.content.Context;

import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.main_features.model.Model;
import com.trungnguyeen.orderfood.main_features.view.interfaces.FoodViewListener;
import com.trungnguyeen.orderfood.main_features.view.interfaces.TableViewListener;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/12/18.
 */

public class Presenter implements FoodPresenterListener, TablePresenterListener {

    private Context context;
    private Model mModel;
    private FoodViewListener foodView_callback;
    private TableViewListener tableView_callback;

    public Presenter(Context context, FoodViewListener foodView_callback) {
        this.context = context;
        this.mModel = mModel;
        this.foodView_callback = foodView_callback;
    }

    public Presenter(Context context, TableViewListener tableView_callback) {
        this.context = context;
        this.mModel = mModel;
        this.tableView_callback = tableView_callback;
    }

    public void requestFoodData(){
        mModel = new Model(this.context);
        mModel.setFoodCallback(this);
        mModel.loadFoodDataFromServer();
    }

    public void requestTableData() {
        mModel = new Model(this.context);
        mModel.setTableCallback(this);
        mModel.loadTableDataFromServer();
    }

    @Override
    public void onSuccessResponseData(ArrayList<Food> foods) {
        foodView_callback.onSuccessResponseData(foods);
    }

    @Override
    public void onFailedData() {
        foodView_callback.onFailedData();
    }

    @Override
    public void onSuccessResponseTableData(ArrayList<Table> tables) {
        tableView_callback.onSuccessResponseData(tables);
    }

    @Override
    public void onFailedTableData() {
        tableView_callback.onFailedData();
    }
}
