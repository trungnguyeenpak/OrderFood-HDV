package com.trungnguyeen.orderfood.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by trungnguyeen on 4/22/18.
 */

public class Order implements Serializable{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("employee")
    @Expose
    private Employee employee;
    @SerializedName("diningTable")
    @Expose
    private Table diningTable;
    @SerializedName("orderDetailList")
    @Expose
    private List<OrderDetailList> orderDetailList = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Order() {
    }

    /**
     *
     * @param id
     * @param diningTable
     * @param status
     * @param orderDetailList
     * @param employee
     * @param date
     */
    public Order(Integer id, String date, Integer status, Employee employee, Table diningTable, List<OrderDetailList> orderDetailList) {
        super();
        this.id = id;
        this.date = date;
        this.status = status;
        this.employee = employee;
        this.diningTable = diningTable;
        this.orderDetailList = orderDetailList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Table getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(Table diningTable) {
        this.diningTable = diningTable;
    }

    public List<OrderDetailList> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailList> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
