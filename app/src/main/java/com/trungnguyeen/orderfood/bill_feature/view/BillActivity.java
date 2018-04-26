package com.trungnguyeen.orderfood.bill_feature.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.bill_feature.presenter.Presenter;
import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.data.model.OrderDetailList;
import com.trungnguyeen.orderfood.table_featrue.view.interfaces.ThanhToanListener;
import com.trungnguyeen.orderfood.utils.Constants;

import java.util.List;

public class BillActivity extends AppCompatActivity {

    //Var controls
    private RecyclerView mRecyclerBill;
    private Toolbar mToolbar;
    private TextView tvTongTien;
    private BillAdapter billAdapter;
    private List<OrderDetailList> orderDetailLists;
    private Presenter mPresenter;
    private TextView btnThanhToan;
    //Var dev
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Intent intent = getIntent();
        if(intent.hasExtra(Constants.ORDER)){
            order = (Order) intent.getSerializableExtra(Constants.ORDER);
        }
        this.orderDetailLists = order.getOrderDetailList();
        mPresenter = new Presenter(this);

        setupControls();
    }

    private void setupControls() {
        tvTongTien = findViewById(R.id.tv_tien_bill);
        btnThanhToan = findViewById(R.id.btn_thanhtoan);

        int tongtien = 0;
        for (int i = 0; i < order.getOrderDetailList().size(); i++){
            tongtien += order.getOrderDetailList().get(i).getQuantity()
                        *order.getOrderDetailList().get(i).getFood().getPrice();
        }
        tvTongTien.setText(String.format("Tổng tiền: " + tongtien + " VND"));

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.thanhToanWithOrderId(order.getId());
                mPresenter.setmThanhToan(new ThanhToanListener() {
                    @Override
                    public void showBill(int tongTien) {
                        showAlearComplete();
                    }

                    @Override
                    public void showError(int error) {
                        Toast.makeText(BillActivity.this, "Thanh toán chưa hoàn tất.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        setupToolbar();
        setupRecyclerViewBil();

    }

    private void showAlearComplete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thanh toán hoàn tất");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();

    }

    private void setupRecyclerViewBil() {
        mRecyclerBill = findViewById(R.id.recyclerview_bill);
        mRecyclerBill.setLayoutManager(new LinearLayoutManager(this));
        billAdapter = new BillAdapter();
        billAdapter.setOrderDetailLists(this.orderDetailLists);
        mRecyclerBill.setAdapter(billAdapter);
        mRecyclerBill.addItemDecoration(new DividerItemDecoration(mRecyclerBill.getContext(),
                DividerItemDecoration.VERTICAL));
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar_bill);
        mToolbar.setTitle(R.string.dang_tinh_tien);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
