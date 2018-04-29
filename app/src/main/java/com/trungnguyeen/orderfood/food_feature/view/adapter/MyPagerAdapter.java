package com.trungnguyeen.orderfood.food_feature.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.food_feature.view.fragment.MenuFragment;
import com.trungnguyeen.orderfood.food_feature.view.fragment.OrderFragment;

/**
 * Created by trungnguyeen on 11/2/17.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private Order order;
    public final static int FRAGMENT_COUNT = 2;
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                //TODO: add fragment one
                MenuFragment menuFragment = new MenuFragment();
                menuFragment.setOrder(this.order);
                return menuFragment;
            case 1:
                //TODO: add fragment two
                OrderFragment orderFragment = new OrderFragment();
                orderFragment.setOrder(this.order);
                return orderFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "MENU";
            case 1:
                return "ĐANG ODER";
            default:
                return "";
        }
    }
}
