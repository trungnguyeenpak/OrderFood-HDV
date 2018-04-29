package com.trungnguyeen.orderfood.food_feature.view.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.OrderDetailList;
import com.trungnguyeen.orderfood.food_feature.view.interfaces.OnLongClickOrderItem;

import java.util.List;

/**
 * Created by trungnguyeen on 4/29/18.
 */

public class OrderFoodAdapter extends RecyclerView.Adapter<OrderFoodAdapter.OrderFoodViewHolder>{

    private Context context;
    private List<OrderDetailList> orderDetailLists;
    private OnLongClickOrderItem mListener;

    public void setmListener(OnLongClickOrderItem mListener) {
        this.mListener = mListener;
    }

    public void setOrderDetailLists(List<OrderDetailList> orderDetailLists) {
        this.orderDetailLists = orderDetailLists;
    }

    @Override
    public OrderFoodAdapter.OrderFoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemview = LayoutInflater.from(this.context)
                .inflate(R.layout.item_order_food, parent, false);

        return new OrderFoodViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(OrderFoodAdapter.OrderFoodViewHolder holder, int position) {
        OrderDetailList item = this.orderDetailLists.get(position);
        holder.bindView(item);

    }

    @Override
    public int getItemCount() {
        if (this.orderDetailLists != null){
            return this.orderDetailLists.size();
        }
        return 0;
    }

    public void removeItem(int position) {
        orderDetailLists.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(OrderDetailList item, int position) {
        orderDetailLists.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class OrderFoodViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView txtFoodName;
        TextView txtSoluong;
        public ConstraintLayout viewForeground;
        public ConstraintLayout viewBackground;

        public OrderFoodViewHolder(View itemView) {
            super(itemView);

            txtFoodName = itemView.findViewById(R.id.tv_foodname_order);
            txtSoluong = itemView.findViewById(R.id.tv_sluong_order);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            viewBackground = itemView.findViewById(R.id.view_background);
            itemView.setOnLongClickListener(this);
        }


        public void bindView(OrderDetailList orderDetailList){
            txtFoodName.setText(orderDetailList.getFood().getName());
            txtSoluong.setText(String.format("Số lượng: " + orderDetailList.getQuantity()));
        }

        @Override
        public boolean onLongClick(View v) {
            mListener.onLongClick(v, getAdapterPosition(), orderDetailLists.get(getAdapterPosition()));
            return true;
        }
    }
}
