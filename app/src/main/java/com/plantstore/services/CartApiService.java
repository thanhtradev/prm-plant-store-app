package com.plantstore.services;

import com.plantstore.models.api.CartResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CartApiService {
    //    /api/cart/{userId}
    @GET("api/cart/{userId}")
    Call<CartResponse> getCartByUserId(@Path("userId") Long userId);

    @DELETE("api/cart/{userId}")
    Call<Void> deleteCartByUserId(@Path("userId") Long userId);
}
