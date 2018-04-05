package com.trungnguyeen.orderfood.login.presenter;

import com.trungnguyeen.orderfood.data.model.Employee;

/**
 * Created by trungnguyeen on 4/5/18.
 */

public interface LoginViewListener {
    void onLoginSuccess(Employee employee);
    void onLoginFailed();
}
