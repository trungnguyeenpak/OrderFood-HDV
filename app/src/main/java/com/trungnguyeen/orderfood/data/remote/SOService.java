package com.trungnguyeen.orderfood.data.remote;

import com.trungnguyeen.orderfood.data.model.response.BillResponse;
import com.trungnguyeen.orderfood.data.model.response.EmployeeResponse;
import com.trungnguyeen.orderfood.data.model.response.FoodListResponse;
import com.trungnguyeen.orderfood.data.model.response.OrderResponse;
import com.trungnguyeen.orderfood.data.model.response.ResultResponse;
import com.trungnguyeen.orderfood.data.model.response.TableListResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by trungnguyeen on 4/5/18.
 */

public interface SOService {

    @FormUrlEncoded
    @POST("auth/login.json?")
    Call<EmployeeResponse> userlogin(@Field("userName") String username,
                                     @Field("password") String password);

    @GET("employee/{userID}.json")
    Call<EmployeeResponse> getUserInfo(@Path("userID") int userID);

    @GET("food/list.json")
    Call<FoodListResponse> getFoodList();

    @GET("dining-table/list.json")
    Call<TableListResponse> getTableList();

    @PUT("order/payment.json")
    Call<BillResponse> thanhToanTienWithOrderId(@Query("id") int tableid);

    @GET("order/diningTable.json")
    Call<OrderResponse> getOrderWithTableId(@Query("diningTableId") int tableid);

    @GET("order/{id}.json")
    Call<OrderResponse> getOrderWithId(@Path("id") int id);

    @FormUrlEncoded
    @POST("order/create.json?")
    Call<OrderResponse> createOrderWithTable(@Field("employeeId") int employeeId,
                                             @Field("diningTableId") int diningTableId);

    @FormUrlEncoded
    @POST("order/{orderId}/detail/add.json?")
    Call<ResultResponse> addFood(@Path("orderId") int orderId,
                                 @Field("foodId") int foodId,
                                 @Field("quantity") int quantity);



    @PUT("order/{orderId}/detail/{orderDetailId}/update.json")
    Call<ResultResponse> updateOrderDetailId(@Path("orderId") int orderId,
                                                @Path("orderDetailId") int orderDetailId,
                                                @Query("foodId") int foodId,
                                                @Query("quantity")int quantity);



    @DELETE("/order/detail/delete.json")
    Call<ResultResponse> deleteFoodInOrder(@Query("id") int orderDetailId);

}
