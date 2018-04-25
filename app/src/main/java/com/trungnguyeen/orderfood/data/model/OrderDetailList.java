package com.trungnguyeen.orderfood.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by trungnguyeen on 4/22/18.
 */

public class OrderDetailList implements Serializable{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("food")
    @Expose
    private Food food;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderDetailList() {
    }

    /**
     *
     * @param id
     * @param food
     * @param quantity
     */
    public OrderDetailList(Integer id, Integer quantity, Food food) {
        super();
        this.id = id;
        this.quantity = quantity;
        this.food = food;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
