package com.trungnguyeen.orderfood.login.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.trungnguyeen.orderfood.data.model.Employee;
import com.trungnguyeen.orderfood.data.model.SingletonUser;
import com.trungnguyeen.orderfood.data.model.response.EmployeeResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.utils.ApiUtils;
import com.trungnguyeen.orderfood.utils.Constants;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trungnguyeen on 4/2/18.
 */

public class LoginModel {

    private final static String TAG = LoginModel.class.getSimpleName();
    private Context context;
    private SplashPresenterListener splashCallback;
    private LoginPresenterListener loginCallback;
    private SOService service = ApiUtils.getSOService();
    private SingletonUser mUser = SingletonUser.getInstance();

    public LoginModel(Context context, SplashPresenterListener splashCallback) {
        this.context = context;
        this.splashCallback = splashCallback;
    }

    public LoginModel(Context context, LoginPresenterListener loginCallback) {
        this.context = context;
        this.loginCallback = loginCallback;
    }

    public void handleCheckLogin() {
        SharedPreferences preferences = context.getSharedPreferences(
                Constants.KEY_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString(Constants.SHARE_PREF_USER, null);
        Log.e("TAG", "handleCheckLogin: " + json);
        if (json == null) {
            splashCallback.noLogin(Constants.NO_LOGIN_CODE);
            return;
        }
        Employee employee = gson.fromJson(json, Employee.class);
        mUser.setEmployee(employee);
        splashCallback.loggedin();
        Log.i(TAG, "onResponse: " + employee);
    }

    public void handleLogin(String username, String password) {
        service.userlogin(username, password).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                Log.e(TAG, "onResponse: " + response.code());
                EmployeeResponse employeeResponse = response.body();
                if (employeeResponse != null) {
                    if (employeeResponse.getStatus() == Constants.STATUS_REST_DATA) {
                        Employee employee = employeeResponse.getData();
                        employee = employeeResponse.getData();
                        saveSharePrefWithEmployee(employee);
                        mUser.setEmployee(employee);
                        loginCallback.onLoginSuccess(employee);
                    } else {
                        loginCallback.onLoginFailed();
                    }
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    loginCallback.onLoginFailed();
                }
            }
            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                loginCallback.onLoginFailed();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void saveSharePrefWithEmployee(Employee employee) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                Constants.KEY_PREFERENCES, context.MODE_PRIVATE).edit();

        Gson gson = new Gson();
        String json = gson.toJson(employee);
        editor.putString(Constants.SHARE_PREF_USER, json);
        editor.apply();
        Log.i(TAG, "readUserToken: ghi thanh cong " + employee);
    }

}

