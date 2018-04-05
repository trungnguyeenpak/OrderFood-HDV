package com.trungnguyeen.orderfood.data.model;

/**
 * Created by trungnguyeen on 4/5/18.
 */

public class SingletonUser {
    private static SingletonUser instance = null;
    private Employee employee;

    private SingletonUser(){}

    public static SingletonUser getInstance(){
        if(instance == null){
            instance = new SingletonUser();
        }
        return instance;
    }

    public Employee getEmployee() {

        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
