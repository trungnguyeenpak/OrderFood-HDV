package com.trungnguyeen.orderfood.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trungnguyeen.orderfood.data.model.Food;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/12/18.
 */

public class FoodListResponse {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Food> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public FoodListResponse() {
    }

    /**
     *
     * @param message
     * @param result
     * @param status
     * @param data
     */
    public FoodListResponse(Boolean result, Integer status, String message, ArrayList<Food> data) {
        this.result = result;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Food> getFoods() {
        return data;
    }

    public void setData(ArrayList<Food> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return message;
    }
}
