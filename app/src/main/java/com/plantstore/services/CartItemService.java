package com.plantstore.services;

import com.plantstore.models.api.AddToCartRequest;
import com.plantstore.models.api.CartItemListResponse;
import com.plantstore.models.api.CartItemResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartItemService {
    @GET("api/cart-item/{id}")
    Call<CartItemResponse> getCartItemById(@Path("id") Long id);

    @GET("api/cart-item/list/{ids}")
    Call<CartItemListResponse> getCartItemsByIds(@Path("ids") String ids);

    // add to cart
    @POST("api/cart-item")
    Call<CartItemResponse> addToCart(@Body AddToCartRequest addToCartRequest);

    @DELETE("api/cart-item/{id}")
    Call<Void> deleteCartItem(@Path("id") Long id);

}
