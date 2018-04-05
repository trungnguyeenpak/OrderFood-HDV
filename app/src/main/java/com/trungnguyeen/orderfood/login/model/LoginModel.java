package com.trungnguyeen.orderfood.login.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.trungnguyeen.orderfood.data.model.Employee;
import com.trungnguyeen.orderfood.data.model.SingletonUser;
import com.trungnguyeen.orderfood.data.model.response.EmployeeResponse;
import com.trungnguyeen.orderfood.data.remote.SOService;
import com.trungnguyeen.orderfood.utilities.ApiUtils;
import com.trungnguyeen.orderfood.utilities.Constants;

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

        int user_token = preferences.getInt(Constants.USER_TOKEN, 0);
        Log.i("TAG", "handleCheckLogin: " + user_token);
        if (user_token != 0){ //Neu da dang nhap, get thong tin user tu server
            service.getUserInfo(user_token).enqueue(new Callback<EmployeeResponse>() {
                @Override
                public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                    if(response.isSuccessful()){
                        EmployeeResponse employeeResponse = response.body();
                        Employee employee = employeeResponse.getData();
                        mUser.setEmployee(employee);
                        splashCallback.loggedin();
                        Log.i(TAG, "onResponse: " + employee);
                    }
                }

                @Override
                public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    splashCallback.noLogin();
                }
            });
        }
        else{
            splashCallback.noLogin();
        }

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
                        writeUserToken(employee.getId());
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

    private void writeUserToken(int userToken) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                Constants.KEY_PREFERENCES, context.MODE_PRIVATE).edit();
        editor.putInt(Constants.USER_TOKEN, userToken);
        editor.apply();
        Log.i(TAG, "readUserToken: ghi thanh cong " + userToken);
    }

}

