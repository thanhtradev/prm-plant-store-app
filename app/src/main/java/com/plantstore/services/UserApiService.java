package com.plantstore.services;

import com.plantstore.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApiService {
    @FormUrlEncoded
    @POST("app/auth/login")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("app/auth/register")
    Call<User> register(@Field("username") String username, @Field("password") String password);

}
