package com.plantstore.services;

import com.plantstore.models.api.CreateOrderRequest;
import com.plantstore.models.api.GetListOrderResponse;
import com.plantstore.models.api.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApiService {
    @GET("order/get-by-user/{id}")
    Call<GetListOrderResponse> getOrdersByUserId(@Path("id") Long id);

    // Create order
    @POST("order/create")
    Call<OrderResponse>  createOrder(@Body CreateOrderRequest createOrderRequest);
}
