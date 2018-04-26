package com.trungnguyeen.orderfood.food_feature.presenter;

import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.food_feature.model.MenuModel;
import com.trungnguyeen.orderfood.food_feature.view.interfaces.ShowMenuFood;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/26/18.
 */

public class MenuFoodPresenter implements SendDataToMenuFoodView {

    private MenuModel model;
    private ShowMenuFood menuViewListener;

    public MenuFoodPresenter() {
        model = new MenuModel();
    }

    public void setMenuViewListener(ShowMenuFood menuViewListener) {
        this.menuViewListener = menuViewListener;
    }

    public void fetchMenuFoodFromServer() {
        model.fetchMenuFood();
        model.setPresenterListener(this);

    }

    @Override
    public void sendDataToMenuView(ArrayList<Food> foods) {
        menuViewListener.updateMenuFoodFormData(foods);
    }

    @Override
    public void sendErrorToView(int error501) {
        menuViewListener.showErrorWithCode(error501);
    }
}
