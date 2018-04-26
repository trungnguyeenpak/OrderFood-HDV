package com.trungnguyeen.orderfood.food_feature.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.food_feature.presenter.MenuFoodPresenter;
import com.trungnguyeen.orderfood.food_feature.view.adapter.MenuFoodAdapter;
import com.trungnguyeen.orderfood.food_feature.view.interfaces.ShowMenuFood;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 11/2/17.
 */

public class MenuFragment extends Fragment implements ShowMenuFood {

    //Controls
    private RecyclerView mMenuRecycler;
    private MenuFoodAdapter mMenuFoodAdapter;
    private MenuFoodPresenter mPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView nodata;

    //Dev
    private ArrayList<Food> mFoods;
    private final static String TAG = MenuFragment.class.getSimpleName();
    private boolean isSwipeRefresh = false;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //Todo 1:load data
        mFoods = new ArrayList<>();
        mPresenter = new MenuFoodPresenter();
        mPresenter.fetchMenuFoodFromServer();
        mPresenter.setMenuViewListener(this);

        //Todo 2:Setup controls
        nodata = view.findViewById(R.id.tv_no_data);
        setupMenuRecylerView(view);
        setupSwipeRefreshLayout(view);
        return view;
    }

    private void setupSwipeRefreshLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_menufood);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                isSwipeRefresh = true;
                mPresenter.fetchMenuFoodFromServer();
            }
        });
    }

    private void setupMenuRecylerView(View view) {
        mMenuRecycler = view.findViewById(R.id.recyclerview_menu);
        //Create layout for recyclerview
        mMenuRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //Create adapter for recyclerview
        mMenuFoodAdapter = new MenuFoodAdapter();
        mMenuFoodAdapter.setFoods(mFoods);
        mMenuRecycler.setAdapter(mMenuFoodAdapter);
    }

    @Override
    public void updateMenuFoodFormData(ArrayList<Food> foods) {
        if(isSwipeRefresh == true){
            isSwipeRefresh = !isSwipeRefresh;
            swipeRefreshLayout.setRefreshing(false);
        }

        mMenuFoodAdapter.setFoods(foods);
        mMenuFoodAdapter.notifyDataSetChanged();

        //hidden status no data and show recycler view
        nodata.setVisibility(View.GONE);
        mMenuRecycler.setVisibility(View.VISIBLE);

    }

    @Override
    public void showErrorWithCode(int error501) {
        if(isSwipeRefresh == true){
            isSwipeRefresh = !isSwipeRefresh;
            swipeRefreshLayout.setRefreshing(false);
        }

        //show status no data and hidden recycler view
        nodata.setVisibility(View.VISIBLE);
        mMenuRecycler.setVisibility(View.GONE);
    }
}
