package com.trungnguyeen.orderfood.utils;

import com.trungnguyeen.orderfood.data.remote.RetrofitClient;
import com.trungnguyeen.orderfood.data.remote.SOService;

/**
 * Created by trungnguyeen on 4/5/18.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://192.168.1.192:8080/OrderFoodRESTful/";

    public static SOService getSOService() {
        return RetrofitClient.getRetrofitClient(BASE_URL).create(SOService.class);
    }
}
