package com.trungnguyeen.orderfood.login.presenter;

import android.content.Context;

import com.trungnguyeen.orderfood.data.model.Employee;
import com.trungnguyeen.orderfood.login.model.LoginPresenterListener;
import com.trungnguyeen.orderfood.login.model.LoginModel;
import com.trungnguyeen.orderfood.login.model.SplashPresenterListener;

/**
 * Created by trungnguyeen on 4/2/18.
 */

public class LoginPresenter implements SplashPresenterListener, LoginPresenterListener {

    private Context context;
    private LoginModel loginModel;
    private SplashViewListener splashCallback;
    private LoginViewListener loginCallback;

    public LoginPresenter(Context context, SplashViewListener callback) {
        this.context = context;
        this.splashCallback = callback;
    }

    public LoginPresenter(Context context, LoginViewListener loginCallback) {
        this.context = context;
        this.loginCallback = loginCallback;
    }

    public void receiveHandleCheckLogin() {
        loginModel = new LoginModel(context, (SplashPresenterListener) this);
        loginModel.handleCheckLogin();
    }

    @Override
    public void loggedin() {
        splashCallback.loggedin();
    }

    @Override
    public void noLogin(int responseCode) {
        splashCallback.noLogin(responseCode);
    }


    public void handleLogin(String username, String password) {
        loginModel = new LoginModel(context, (LoginPresenterListener) this);
        loginModel.handleLogin(username, password);
    }

    @Override
    public void onLoginSuccess(Employee employee) {
        loginCallback.onLoginSuccess(employee);
    }

    @Override
    public void onLoginFailed() {
        loginCallback.onLoginFailed();
    }
}
