package com.trungnguyeen.orderfood.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by trungnguyeen on 4/22/18.
 */

public class BillResponse {

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
    private Integer data;

    /**
     * No args constructor for use in serialization
     *
     */
    public BillResponse() {
    }

    /**
     *
     * @param message
     * @param result
     * @param status
     * @param data
     */
    public BillResponse(Boolean result, Integer status, String message, Integer data) {
        super();
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

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

}
