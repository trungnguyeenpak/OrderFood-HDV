package com.trungnguyeen.orderfood.data.model.request;

/**
 * Created by trungnguyeen on 4/5/18.
 */

public class LoginRequest {

    private String userName;
    private String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginRequest() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\"userName\" : \""+ userName +"\", \"password\" : \""+ password +"\"}";
    }

}
