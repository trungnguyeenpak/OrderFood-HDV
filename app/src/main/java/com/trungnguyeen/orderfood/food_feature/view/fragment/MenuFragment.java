package com.trungnguyeen.orderfood.food_feature.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.data.model.response.ResultResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.food_feature.presenter.MenuFoodPresenter;
import com.trungnguyeen.orderfood.food_feature.view.FoodListActivity;
import com.trungnguyeen.orderfood.food_feature.view.adapter.MenuFoodAdapter;
import com.trungnguyeen.orderfood.food_feature.view.interfaces.OnClickFoodItem;
import com.trungnguyeen.orderfood.food_feature.view.interfaces.ShowMenuFood;
import com.trungnguyeen.orderfood.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trungnguyeen on 11/2/17.
 */

public class MenuFragment extends Fragment implements ShowMenuFood, OnClickFoodItem {

    //Controls
    private RecyclerView mMenuRecycler;
    private MenuFoodAdapter mMenuFoodAdapter;
    private MenuFoodPresenter mPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView nodata;
    private SOService mService = ApiUtils.getSOService();

    //Dev
    private ArrayList<Food> mFoods;
    private final static String TAG = MenuFragment.class.getSimpleName();
    private boolean isSwipeRefresh = false;
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

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
        mMenuFoodAdapter.setmListener(this);
        mMenuRecycler.setAdapter(mMenuFoodAdapter);
        mMenuRecycler.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

    }

    @Override
    public void updateMenuFoodFormData(ArrayList<Food> foods) {
        if (isSwipeRefresh == true) {
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
        if (isSwipeRefresh == true) {
            isSwipeRefresh = !isSwipeRefresh;
            swipeRefreshLayout.setRefreshing(false);
        }

        //show status no data and hidden recycler view
        nodata.setVisibility(View.VISIBLE);
        mMenuRecycler.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v, int position, final Food food) {
//        Toast.makeText(getContext(), "Food name: " + food.getName(), Toast.LENGTH_SHORT).show();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View dialogView = layoutInflater.inflate(R.layout.dialog_order_number, null);
        final TextView txtTitle = dialogView.findViewById(R.id.txtTitle);
        final EditText edtSoluong = dialogView.findViewById(R.id.edtnumberorder);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setPositiveButton("Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int soluong = 1;
                if (!TextUtils.isEmpty(edtSoluong.getText())) {
                    soluong = Integer.parseInt(edtSoluong.getText().toString().trim());
                }
                //Toast.makeText(getContext(),
//                        "orderid: " + order.getId() + "  Food id: " + food.getId() + " soluong: " + soluong,
//                                Toast.LENGTH_SHORT).show();
                mService.addFood(order.getId(), food.getId(), soluong).enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        Log.e(TAG, "onResponse: response code " + response.code());
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 201) {
                                Toast.makeText(getContext(), "Đã order", Toast.LENGTH_SHORT).show();

                                String TABFRAGMENTORDER = ((FoodListActivity)getActivity()).getTABFRAGMENTORDER();

                                OrderFragment orderFragment = (OrderFragment) getActivity()
                                                                .getSupportFragmentManager()
                                                                .findFragmentByTag(TABFRAGMENTORDER);

                                orderFragment.autoUpdateOrderList();

                            } else {
                                Log.e(TAG, "onResponse: " + response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        Log.e(TAG, "onResponse: " + t.getMessage());
                    }
                });
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}


