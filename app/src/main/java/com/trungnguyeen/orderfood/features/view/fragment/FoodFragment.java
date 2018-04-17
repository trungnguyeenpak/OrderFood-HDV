package com.trungnguyeen.orderfood.features.view.fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.features.presenter.Presenter;
import com.trungnguyeen.orderfood.features.view.adapter.FoodAdapter;
import com.trungnguyeen.orderfood.features.view.interfaces.FoodViewListener;
import com.trungnguyeen.orderfood.utils.ConnectivityChangeReceiver;
import com.trungnguyeen.orderfood.utils.NetworkListener;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/3/18.
 */

public class FoodFragment extends Fragment implements FoodViewListener, NetworkListener{

    //Variable for control
    private RecyclerView mRecyclerViewFood;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvNoConnect;

    //Variable of dev
    public final static String TAG = FoodFragment.class.getSimpleName();
    private FoodAdapter foodAdapter;
    private ArrayList<Food> foodList;
    private Presenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, null);
        getActivity().registerReceiver(new ConnectivityChangeReceiver(this),
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        foodList = new ArrayList<>();
        mPresenter = new Presenter(getContext(), this);
        requestDataFromServer();
        tvNoConnect = view.findViewById(R.id.tv_no_connect);
        setupRecyclerView(view);
        setupSwipeRefreshLayout(view);
        Log.i(TAG, "onCreateView: FoodFragment");
        return view;
    }

    private void requestDataFromServer() {
        mPresenter.requestData();
    }

    public void setupSwipeRefreshLayout(View view){
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadRecyclerView();
            }
        });
    }

    private void setupRecyclerView(View view) {
        mRecyclerViewFood = view.findViewById(R.id.rvFood);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewFood.setLayoutManager(linearLayoutManager);
        foodAdapter = new FoodAdapter();
        foodAdapter.setData(foodList);
        mRecyclerViewFood.setAdapter(foodAdapter);
        mRecyclerViewFood.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void reloadRecyclerView() {
        Toast.makeText(getContext(), "Reload RecyclerView", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                mPresenter.requestData();
            }
        }, 3000);
    }

    @Override
    public void onSuccessResponseData(ArrayList<Food> foods) {
        tvNoConnect.setVisibility(View.GONE);
        mRecyclerViewFood.setVisibility(View.VISIBLE);
        this.foodList = foods;
        Log.i(TAG, "onSuccessResponseData: datasize " + this.foodList.size());
        foodAdapter.setData(this.foodList);
        foodAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedData() {
        mRecyclerViewFood.setVisibility(View.GONE);
        tvNoConnect.setVisibility(View.VISIBLE);
    }

    @Override
    public void connected() {

    }

    @Override
    public void notConnected() {
        Toast.makeText(getContext(), getResources().
                        getText(R.string.not_connect_network),
                Toast.LENGTH_SHORT).show();
        mRecyclerViewFood.setVisibility(View.GONE);
        tvNoConnect.setVisibility(View.VISIBLE);
    }

}
