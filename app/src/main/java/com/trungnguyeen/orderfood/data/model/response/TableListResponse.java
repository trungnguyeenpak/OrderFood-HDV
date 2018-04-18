package com.trungnguyeen.orderfood.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trungnguyeen.orderfood.data.model.Table;

import java.util.ArrayList;

/**
 * Created by trungnguyeen on 4/18/18.
 */

public class TableListResponse {
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
    private ArrayList<Table> tables = null;
    /**
     * No args constructor for use in serialization
     *
     */
    public TableListResponse() {
    }

    /**
     *
     * @param message
     * @param result
     * @param status
     * @param tables
     */
    public TableListResponse(Boolean result, Integer status, String message, ArrayList<Table> tables) {
        super();
        this.result = result;
        this.status = status;
        this.message = message;
        this.tables = tables;
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

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }
}
