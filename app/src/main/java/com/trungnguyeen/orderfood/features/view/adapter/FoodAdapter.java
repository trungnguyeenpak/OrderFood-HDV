package com.trungnguyeen.orderfood.features.view.adapter;

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

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/7/18.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private ArrayList<Food> data;

    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.food_recycler_item, parent, false);

        return new FoodViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(FoodAdapter.FoodViewHolder holder, int position) {

        Food item = data.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Food> data) {
        this.data = data;
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private TextView fooName;
        private TextView foodPrice;
        private ImageView foodImage;

        FoodViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            fooName = itemView.findViewById(R.id.tv_food_name);
            foodPrice = itemView.findViewById(R.id.tv_food_price);
            foodImage = itemView.findViewById(R.id.img_food);
        }

        void bindView(Food food) {
            fooName.setText(food.getName());
            foodPrice.setText(String.format("%s VND", food.getPrice()));
//            if (food.getImage() != "photo.png"){
//                Glide.with(foodImage)
//                        .load(food.getImage())
//                        .into(foodImage);
//                Toast.makeText(context, "photo.png", Toast.LENGTH_SHORT).show();
//            }
//            else{
                Glide.with(context)
                        .load(R.drawable.placeholder_food)
                        .into(foodImage);

//            }



        }
    }
}
