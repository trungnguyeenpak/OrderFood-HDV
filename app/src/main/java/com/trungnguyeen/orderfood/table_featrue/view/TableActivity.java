package com.trungnguyeen.orderfood.table_featrue.view;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.bill_feature.view.BillActivity;
import com.trungnguyeen.orderfood.data.model.Employee;
import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.data.model.SingletonUser;
import com.trungnguyeen.orderfood.data.model.Table;
import com.trungnguyeen.orderfood.login.view.LoginActivity;
import com.trungnguyeen.orderfood.table_featrue.presenter.Presenter;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.BottomSheetItemClick;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.TableItemClickListener;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.LoadTableListener;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.PaymentListener;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.ThanhToanListener;
import com.trungnguyeen.orderfood.utils.ConnectivityChangeReceiver;
import com.trungnguyeen.orderfood.utils.Constants;
import com.trungnguyeen.orderfood.utils.NetworkListener;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity implements NetworkListener, TableItemClickListener, BottomSheetItemClick, LoadTableListener, PaymentListener {

    //Variable for control
    private RecyclerView mRecylerViewTable;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvNoConnect;
    private Toolbar mToolbar;
    private NavigationView nav_left;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView mUsername;
    private BottomSheetFragment bottomSheetFragment;
    private boolean isSwipeRefresh = false;
    private AlertDialog dialog;

    //Variable of dev
    private TableAdapter tableAdapter;
    private ArrayList<Table> tableList;
    private Presenter mPresenter;
    public final static String TAG = TableActivity.class.getSimpleName();
    private Employee mEmployee;
    private SingletonUser mUser = SingletonUser.getInstance();
    private boolean doubleBackToExit = false;
    BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);

        //Dang ky lang nghe network
        mReceiver = new ConnectivityChangeReceiver(this);
        registerReceiver(mReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        getUser();

        tableList = new ArrayList<>();
        mPresenter = new Presenter(this);
        tvNoConnect = findViewById(R.id.tv_table_no_connect);
        requestDataFromServer();
        setupToolbar();
        setupNavigationLeft();
        setupDrawerLayout();
        setupRecyclerView();
        setupSwipeRefreshLayout();
        Log.i(TAG, "onCreateView: FoodFragment");
    }

    private void setupDrawerLayout() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void setupNavigationLeft() {
        nav_left = findViewById(R.id.navigation_bar);
        View hView = nav_left.getHeaderView(0);
        mUsername = hView.findViewById(R.id.userName);
        Log.i(TAG, "setupNavigationLeft: " + mUsername.getText());
        if (mEmployee != null) {
            mUsername.setText(mEmployee.getFullName());
        }

        nav_left.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        Log.i("TAG", "onNavigationItemSelected: Logout");
                        SharedPreferences prefer = getSharedPreferences(Constants.KEY_PREFERENCES, Context.MODE_PRIVATE);
                        prefer.edit().clear().apply();
                        Intent logoutIntent = new Intent(TableActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar_tableacitivy);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void getUser() {
        mEmployee = mUser.getEmployee();
        Log.i(TAG, "onCreate: Employee " + mEmployee.getFullName());
    }

    private void setupRecyclerView() {
        mRecylerViewTable = findViewById(R.id.table_recycler);
        int numberColums = 3; //number of column gridlayout manager
        mRecylerViewTable.setLayoutManager(new GridLayoutManager(this, numberColums));
        tableAdapter = new TableAdapter();
        tableAdapter.setData(tableList);
        tableAdapter.setItemListener(this);
        mRecylerViewTable.setAdapter(tableAdapter);
    }

    private void requestDataFromServer() {
        mPresenter.requestTableData();
        mPresenter.setLoadTableCallBack(this);
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_table_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                isSwipeRefresh = true;
                reloadRecyclerView();
            }
        });
    }

    private void reloadRecyclerView() {
        mPresenter.requestTableData();
        mPresenter.setLoadTableCallBack(this);
    }

    @Override
    public void onBackPressed() {
        Toast toast = Toast.makeText(getBaseContext(), "Please click back again to exit", Toast.LENGTH_SHORT);
        if (doubleBackToExit) {
            super.onBackPressed();
            toast.cancel();
            finish();
        }
        doubleBackToExit = true;
        toast.show();
        new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                doubleBackToExit = false;
            }
        }.start();
    }

    @Override
    public void connected() {
    }

    @Override
    public void notConnected() {
        Toast.makeText(this, getResources().
                        getText(R.string.not_connect_network),
                Toast.LENGTH_SHORT).show();
        mRecylerViewTable.setVisibility(View.GONE);
        tvNoConnect.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mReceiver != null){
            unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + tableAdapter.getItem(position) + ", which is at cell position " + position);
        //Init bottom sheet fragment and show it
        bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(),
                bottomSheetFragment.getTag());
        bottomSheetFragment.setTableID(tableAdapter.getItem(position).getId());
        bottomSheetFragment.setBottomSheetItemClick(this);
    }

    @Override
    public void onSuccessResponseData(ArrayList<Table> tables) {
        if(isSwipeRefresh == true){
            isSwipeRefresh = !isSwipeRefresh;
            swipeRefreshLayout.setRefreshing(false);
        }
        tvNoConnect.setVisibility(View.GONE);
        mRecylerViewTable.setVisibility(View.VISIBLE);
        this.tableList = tables;
        Log.i(TAG, "onSuccessResponseData: datasize " + this.tableList.size());
        tableAdapter.setData(this.tableList);
        tableAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedData(int errorCode) {

        if(dialog != null){
            dialog.cancel();
        }

        if(isSwipeRefresh == true){
            isSwipeRefresh = !isSwipeRefresh;
            swipeRefreshLayout.setRefreshing(false);
        }

        if(errorCode == Constants.NO_TABLE){
            mRecylerViewTable.setVisibility(View.GONE);
            tvNoConnect.setVisibility(View.VISIBLE);
            return;
        }

        if (errorCode == Constants.THANHTOAN_ERROR){
            Toast.makeText(this, R.string.thanh_toan_errorStr, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onClickItemThanhToan(int tableId) {
        if (bottomSheetFragment != null){
            bottomSheetFragment.dismiss();
        }

        //TODO 1: Kiem tra status ban do
        Table table = null;
        //tim ra table co tableid trong listtable
        for(int i = 0; i < tableList.size(); i++){
            if(tableList.get(i).getId() == tableId){
                table = tableList.get(i);
                Log.i(TAG, "onClickItemThanhToan: table_status " + table.getStatus());
                break;
            }
        }

        //TODO 3: Nếu bàn trống, thông báo không dc tính tiền
        if(table.getStatus() == 0){ //Bàn trống chưa có ai ngồi.
            showDialogWithMessage(getResources().getString(R.string.ban_trong));
            return;
        }

        // TODO 2: Nếu bàn đã có người ngồi, chuyển sang màn hình chưa danh sách các món ăn.
        createDialogGoiTinhTien();
        mPresenter.requestOrderFoodWithTableId(tableId);
        mPresenter.setTinhTienListener(this);
    }

    private void showDialogWithMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onClickOrder(int tableId) {
        //TODO Start Order Activity
        Toast.makeText(this, "" + tableId, Toast.LENGTH_SHORT).show();
    }

    public void createDialogGoiTinhTien(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View dialogView = layoutInflater.inflate(R.layout.dialog_loading, null);
        final ProgressBar progressBar = dialogView.findViewById(R.id.progressBar);
        final TextView tvLoading = dialogView.findViewById(R.id.loading);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showOrderFood(final Order order) {
        if(dialog != null){
            dialog.cancel();
        }

        //Todo thanh toan tai ban luon
        if(order.getOrderDetailList() == null){
            //Toast.makeText(this, "Order size: 0", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Số món đã order: 0\nCó muốn thanh toán không?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.thanhToanWithOrderId(order.getId());
                    mPresenter.setThanhToanListener(new ThanhToanListener() {
                        @Override
                        public void showBill(int tongTien) {
                            reloadRecyclerView();
                            Toast.makeText(TableActivity.this, "Hoàn tất thanh toán.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void showError(int error) {
                            Toast.makeText(TableActivity.this, "Thanh toán chưa hoàn tất.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            })
            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                }
            });
            builder.show();
            return;
        }

        //TODO Start Thuc Don Activity
        Intent billIntent = new Intent(TableActivity.this, BillActivity.class);
        billIntent.putExtra(Constants.ORDER, order);
        startActivity(billIntent);
    }

    @Override
    public void showError(int error) {
        if(dialog != null){
            dialog.cancel();
        }

        if(error == Constants.ERROR_501){
            Toast.makeText(this, "Thao tác không thành công.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
        reloadRecyclerView();
    }
}
