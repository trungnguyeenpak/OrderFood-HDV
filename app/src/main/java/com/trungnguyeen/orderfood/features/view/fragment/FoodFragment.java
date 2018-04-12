package com.trungnguyeen.orderfood.features.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.features.view.adapter.FoodAdapter;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/3/18.
 */

public class FoodFragment extends Fragment {

    //Variable for control
    private RecyclerView mRecyclerViewFood;
    private SwipeRefreshLayout swipeRefreshLayout;

    //Variable of dev
    private final static String TAG = FoodFragment.class.getSimpleName();
    private FoodAdapter foodAdapter;
    private ArrayList<String> dataList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, null);

        dataList = new ArrayList<>();
        setupRecyclerViewAndSwipeRefresh(view);

        dummyData();
        return view;
    }

    private void setupRecyclerViewAndSwipeRefresh(View view) {
        mRecyclerViewFood = view.findViewById(R.id.rvFood);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewFood.setLayoutManager(linearLayoutManager);
        foodAdapter = new FoodAdapter();
        foodAdapter.setData(dataList);
        mRecyclerViewFood.setAdapter(foodAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadRecyclerView();
            }
        });
    }

    private void reloadRecyclerView() {
        Toast.makeText(getContext(), "Reload RecyclerView", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    private void dummyData() {
        dataList.add("Nguyen Trung");
        dataList.add("Nguyen Vu");
        dataList.add("Nguyen Luc");
        dataList.add("Nguyen Tan");
        dataList.add("Nguyen TAOLAO");
        dataList.add("Nguyen METMOIT");
        dataList.add("Nguyen CHICHI");
        dataList.add("Nguyen Ahuhu");
        dataList.add("Nguyen Ahihi");
        dataList.add("Nguyen Thanh Nien");
        dataList.add("Nguyen Hao Quang");
        dataList.add("Nguyen Lao Tien");
    }

}
