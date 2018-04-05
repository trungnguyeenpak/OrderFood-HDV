package com.trungnguyeen.orderfood.login.model;

import com.trungnguyeen.orderfood.data.model.Employee;

/**
 * Created by trungnguyeen on 4/5/18.
 */

public interface LoginPresenterListener {
    void onLoginSuccess(Employee employee);
    void onLoginFailed();
}
