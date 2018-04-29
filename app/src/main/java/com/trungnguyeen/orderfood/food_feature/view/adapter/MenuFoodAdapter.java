package com.trungnguyeen.orderfood.food_feature.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Food;
import com.trungnguyeen.orderfood.food_feature.view.interfaces.OnClickFoodItem;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/26/18.
 */

public class MenuFoodAdapter extends RecyclerView.Adapter<MenuFoodAdapter.MenuItemViewHolder> {

    private Context context;
    private ArrayList<Food> foods;
    private OnClickFoodItem mListener;

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public void setmListener(OnClickFoodItem mListener) {
        this.mListener = mListener;
    }

    @Override
    public MenuFoodAdapter.MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemview = LayoutInflater.from(this.context)
                .inflate(R.layout.item_menu_food, parent, false);

        return new MenuItemViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MenuFoodAdapter.MenuItemViewHolder holder, int position) {
        Food item = foods.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }


    public class MenuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgFood;
        TextView name;
        TextView price;

        public MenuItemViewHolder(View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.img_food);
            name = itemView.findViewById(R.id.tv_food_name);
            price = itemView.findViewById(R.id.tv_food_price);
            itemView.setOnClickListener(this);
        }

        public void bindView(Food food){
            name.setText(food.getName());
            price.setText(String.format("Giá: " + food.getPrice().intValue() + "k"));
            Glide.with(context)
                    .load(food.getImage())
                    .into(imgFood);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition(), foods.get(getAdapterPosition()));
        }
    }
}
