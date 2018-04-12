package com.trungnguyeen.orderfood.features.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trungnguyeen.orderfood.R;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/7/18.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private ArrayList<String> data;

    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.food_recycler_item, parent, false);

        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodAdapter.FoodViewHolder holder, int position) {

        String str = data.get(position);
        holder.bindView(str);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public FoodViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }

        public void bindView(String str) {
            tvName.setText(str);
        }
    }
}
