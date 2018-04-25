package com.trungnguyeen.orderfood.bill_feature.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.OrderDetailList;

import java.util.List;

/**
 * Created by trungnguyeen on 4/25/18.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BilViewHolder>{

    private List<OrderDetailList> orderDetailLists;

    @Override
    public BillAdapter.BilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bill_recycler_item, parent, false);

        return new BilViewHolder(view);
    }

    public void setOrderDetailLists(List<OrderDetailList> orderDetailLists) {
        this.orderDetailLists = orderDetailLists;
    }

    @Override
    public void onBindViewHolder(BillAdapter.BilViewHolder holder, int position) {
        OrderDetailList orderDetail = orderDetailLists.get(position);
        holder.bindView(orderDetail);
    }

    @Override
    public int getItemCount() {
        return orderDetailLists.size();
    }


    public class BilViewHolder extends RecyclerView.ViewHolder{

        TextView tvFoodName;
        TextView tvFoodPrice;
        TextView tvQuality;
        TextView tvMoney;

        public BilViewHolder(View itemView) {
            super(itemView);

            tvFoodName = itemView.findViewById(R.id.tv_foodname_bill);
            tvFoodPrice = itemView.findViewById(R.id.tv_gia_bill);
            tvQuality = itemView.findViewById(R.id.tv_sluong_bill);
            tvMoney = itemView.findViewById(R.id.tv_thanhTien_bill);
        }

        void bindView(OrderDetailList orderDetailList){
            tvFoodName.setText(orderDetailList.getFood().getName());
            tvFoodPrice.setText(String.format("Giá: " + orderDetailList.getFood().getPrice().intValue()));
            tvQuality.setText(String.format("Số lượng: " + orderDetailList.getQuantity()));
            Integer thanhtien = (int) (orderDetailList.getQuantity() * orderDetailList.getFood().getPrice());
            tvMoney.setText(String.format("Thành tiền: %s", thanhtien.toString()));
        }
    }
}
