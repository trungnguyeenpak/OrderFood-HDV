package com.trungnguyeen.orderfood.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by trungnguyeen on 4/29/18.
 */

public class ResultResponse {
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
    private Object data;

    public ResultResponse() {
    }

    public ResultResponse(Boolean result, Integer status, String message, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
