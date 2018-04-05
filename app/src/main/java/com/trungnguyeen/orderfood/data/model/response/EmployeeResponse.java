package com.trungnguyeen.orderfood.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trungnguyeen.orderfood.data.model.Employee;

/**
 * Created by trungnguyeen on 4/5/18.
 */

public class EmployeeResponse {
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
    private Employee data;

    /**
     * No args constructor for use in serialization
     *
     */
    public EmployeeResponse() {
    }

    /**
     *
     * @param message
     * @param result
     * @param status
     * @param data
     */
    public EmployeeResponse(Boolean result, Integer status, String message, Employee data) {
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

    public Employee getData() {
        return data;
    }

    public void setData(Employee data) {
        this.data = data;
    }
}
