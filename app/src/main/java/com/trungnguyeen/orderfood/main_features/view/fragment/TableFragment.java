package com.trungnguyeen.orderfood.main_features.view.fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.main_features.presenter.Presenter;
import com.trungnguyeen.orderfood.main_features.view.adapter.TableAdapter;
import com.trungnguyeen.orderfood.main_features.view.interfaces.TableItemClickListener;
import com.trungnguyeen.orderfood.main_features.view.interfaces.TableViewListener;
import com.trungnguyeen.orderfood.utils.ConnectivityChangeReceiver;
import com.trungnguyeen.orderfood.utils.NetworkListener;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/3/18.
 */

public class TableFragment extends Fragment implements NetworkListener, TableItemClickListener, TableViewListener {

    //Variable for control
    private RecyclerView mRecylerViewTable;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvNoConnect;

    //Variable of dev
    private TableAdapter tableAdapter;
    private ArrayList<Table> tableList;
    private Presenter mPresenter;
    public final static String TAG = TableFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, null);
        Log.i(TAG, "onCreateView: TableFragment");

        //Dang ky lang nghe network
        getActivity().registerReceiver(new ConnectivityChangeReceiver(this),
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        tableList = new ArrayList<>();
        mPresenter = new Presenter(getContext(), this);
        tvNoConnect = view.findViewById(R.id.tv_table_no_connect);
        requestDataFromServer();
        setupRecyclerView(view);
        setupSwipeRefreshLayout(view);

        Log.i(TAG, "onCreateView: FoodFragment");
        return view;
    }

    private void setupRecyclerView(View view) {
        mRecylerViewTable = view.findViewById(R.id.rvTable);
        int numberColums = 3; //number of column gridlayout manager
        mRecylerViewTable.setLayoutManager(new GridLayoutManager(getContext(), numberColums));
        tableAdapter = new TableAdapter();
        tableAdapter.setData(tableList);
        tableAdapter.setItemListener(this);
        mRecylerViewTable.setAdapter(tableAdapter);
    }

    private void requestDataFromServer() {
        mPresenter.requestTableData();
    }

    private void setupSwipeRefreshLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_table_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadRecyclerView();
            }
        });
    }

    private void reloadRecyclerView() {
        Toast.makeText(getContext(), "Reload data", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
        mPresenter.requestTableData();
    }

    @Override
    public void connected() {
    }

    @Override
    public void onSuccessResponseData(ArrayList<Table> tables) {
        tvNoConnect.setVisibility(View.GONE);
        mRecylerViewTable.setVisibility(View.VISIBLE);
        this.tableList = tables;
        Log.i(TAG, "onSuccessResponseData: datasize " + this.tableList.size());
        tableAdapter.setData(this.tableList);
        tableAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedData() {
        mRecylerViewTable.setVisibility(View.GONE);
        tvNoConnect.setVisibility(View.VISIBLE);
    }

    @Override
    public void notConnected() {
        Toast.makeText(getContext(), getResources().
                        getText(R.string.not_connect_network),
                Toast.LENGTH_SHORT).show();
        mRecylerViewTable.setVisibility(View.GONE);
        tvNoConnect.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + tableAdapter.getItem(position) + ", which is at cell position " + position);
        //Init bottom sheet fragment and show it
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getActivity().getSupportFragmentManager(),
                bottomSheetFragment.getTag());
        bottomSheetFragment.setSelectedTable(tableAdapter.getItem(position));
    }
}
