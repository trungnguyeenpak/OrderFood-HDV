package com.trungnguyeen.orderfood.food_feature.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.trungnguyeen.orderfood.food_feature.view.fragment.MenuFragment;
import com.trungnguyeen.orderfood.food_feature.view.fragment.OrderFragment;

/**
 * Created by trungnguyeen on 11/2/17.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    public final static int FRAGMENT_COUNT = 2;
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                //TODO: add fragment one
                return new MenuFragment();
            case 1:
                //TODO: add fragment two
                return new OrderFragment();
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
