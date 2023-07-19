package com.plantstore.services;

import com.plantstore.models.api.LoginRequest;
import com.plantstore.models.api.RegisterRequest;
import com.plantstore.models.api.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("app/auth/login")
    Call<UserResponse> login(@Body LoginRequest loginRequest);

    @POST("app/auth/register")
    Call<UserResponse> register(@Body RegisterRequest registerRequest);
}
