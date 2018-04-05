package com.trungnguyeen.orderfood.utilities;

import com.trungnguyeen.orderfood.data.remote.RetrofitClient;
import com.trungnguyeen.orderfood.data.remote.SOService;

/**
 * Created by trungnguyeen on 4/5/18.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://orderfood1.kilatiron.com";

    public static SOService getSOService() {
        return RetrofitClient.getRetrofitClient(BASE_URL).create(SOService.class);
    }
}
