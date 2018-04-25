package com.trungnguyeen.orderfood.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trungnguyeen.orderfood.data.model.Order;

/**
 * Created by trungnguyeen on 4/22/18.
 */

public class OrderResponse {
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
    private Order order;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderResponse() {
    }

    /**
     *
     * @param message
     * @param result
     * @param status
     * @param order
     */
    public OrderResponse(Boolean result, Integer status, String message, Order order) {
        super();
        this.result = result;
        this.status = status;
        this.message = message;
        this.order = order;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
