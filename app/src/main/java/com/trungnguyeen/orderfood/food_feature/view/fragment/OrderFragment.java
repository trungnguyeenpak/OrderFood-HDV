package com.trungnguyeen.orderfood.food_feature.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.data.model.OrderDetailList;
import com.trungnguyeen.orderfood.data.model.response.ResultResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.design_supporter.RecyclerItemTouchHelper;
import com.trungnguyeen.orderfood.food_feature.presenter.OrderFoodPresenter;
import com.trungnguyeen.orderfood.food_feature.view.FoodListActivity;
import com.trungnguyeen.orderfood.food_feature.view.adapter.OrderFoodAdapter;
import com.trungnguyeen.orderfood.food_feature.view.interfaces.OnLongClickOrderItem;
import com.trungnguyeen.orderfood.food_feature.view.interfaces.ShowOrderFood;
import com.trungnguyeen.orderfood.utils.ApiUtils;
import com.trungnguyeen.orderfood.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trungnguyeen on 11/2/17.
 */

public class OrderFragment extends Fragment implements ShowOrderFood, OnLongClickOrderItem, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    //Controls
    private RecyclerView mOrderRecycler;
    private OrderFoodAdapter mOrderFoodAdatper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout constraintLayout;

    //Dev
    private Order order;
    private List<OrderDetailList> orderDetailList;
    private OrderFoodPresenter mPresenter;
    private final static String TAG = OrderFragment.class.getSimpleName();
    private boolean isSwipeRefresh = false;
    private SOService mService = ApiUtils.getSOService();

    public OrderFragment() {
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        //Todo 1:load data
        orderDetailList = new ArrayList<>();
        orderDetailList = this.order.getOrderDetailList();
        mPresenter = new OrderFoodPresenter();
        mPresenter.setmListener(this);

        //Todo 2:Setup controls
        setupMenuRecylerView(view);
        setupSwipeRefreshLayout(view);
        setupSwipeControllerToLeft();
        String myTag = getTag();
        ((FoodListActivity) getActivity()).setTABFRAGMENTORDER(myTag);
        return view;
    }

    private void setupSwipeControllerToLeft() {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mOrderRecycler);
    }

    private void setupSwipeRefreshLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_order);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                isSwipeRefresh = true;
                mPresenter.fetchOrderFoodFromServer(order.getId());
            }
        });
    }

    private void setupMenuRecylerView(View view) {
        mOrderRecycler = view.findViewById(R.id.recyclerview_order);
        //Create layout for recyclerview
        mOrderRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //Create adapter for recyclerview
        mOrderFoodAdatper = new OrderFoodAdapter();
        mOrderFoodAdatper.setOrderDetailLists(this.order.getOrderDetailList());
        mOrderFoodAdatper.setmListener(this);
        mOrderRecycler.setAdapter(mOrderFoodAdatper);
        mOrderRecycler.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void updateOrderFood(List<OrderDetailList> orderDetailList) {
        if (isSwipeRefresh) {
            isSwipeRefresh = false;
            swipeRefreshLayout.setRefreshing(isSwipeRefresh);
        }

        this.orderDetailList = orderDetailList;
        Log.i(TAG, "updateOrderFood: " + this.orderDetailList.size());
        mOrderFoodAdatper.setOrderDetailLists(this.orderDetailList);
        mOrderFoodAdatper.notifyDataSetChanged();
        if (orderDetailList == null || orderDetailList.size() == 0) {
            Toast.makeText(getContext(), "Chưa gọi món nào...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrorWithCode(int error501) {
        if (isSwipeRefresh) {
            isSwipeRefresh = false;
            swipeRefreshLayout.setRefreshing(isSwipeRefresh);
        }
        Toast.makeText(getContext(), "Update error", Toast.LENGTH_SHORT).show();
    }

    public void autoUpdateOrderList() {
        mPresenter.fetchOrderFoodFromServer(this.order.getId());
    }

    @Override
    public void onLongClick(View v, int position, final OrderDetailList orderDetail) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View dialogView = layoutInflater.inflate(R.layout.dialog_order_number, null);
        final TextView txtTitle = dialogView.findViewById(R.id.txtTitle);
        final EditText edtSoluong = dialogView.findViewById(R.id.edtnumberorder);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int soluong = 1;
                if (!TextUtils.isEmpty(edtSoluong.getText())) {
                    soluong = Integer.parseInt(edtSoluong.getText().toString().trim());
                }

                mService.updateOrderDetailId(order.getId(),
                        orderDetail.getId(),
                        orderDetail.getFood().getId(),
                        soluong).enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        Log.e(TAG, "onResponse: response code " + response.code());
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == Constants.STATUS_REST_DATA) {
                                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                                mPresenter.fetchOrderFoodFromServer(order.getId());

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

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof OrderFoodAdapter.OrderFoodViewHolder) {
            // backup of removed item for undo purpose
            final OrderDetailList deletedItem = this.orderDetailList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Xóa món ăn này ra khỏi order?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(TAG, "onClick: order detail id " + deletedItem.getId());
                    mService.deleteFoodInOrder(deletedItem.getId()).enqueue(new Callback<ResultResponse>() {
                        @Override
                        public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                            if (response.isSuccessful()){
                                if (response.body().getStatus() == Constants.STATUS_REST_DATA){
                                    Log.i(TAG, "onResponse: Delete order successful");
                                    mOrderFoodAdatper.removeItem(deletedIndex);
                                    mOrderFoodAdatper.notifyDataSetChanged();
                                }
                            }
                            Log.e(TAG, "onResponse: " + response.code());
                            Toast.makeText(getContext(), "Xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                            mOrderFoodAdatper.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ResultResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                        }
                    });
                }
            })
            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mOrderFoodAdatper.notifyDataSetChanged();
                }
            });
            builder.show();
        }
    }
}
