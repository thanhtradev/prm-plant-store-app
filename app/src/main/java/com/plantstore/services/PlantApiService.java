package com.plantstore.services;

import com.plantstore.models.api.GetAllPlantResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlantApiService {
    @GET("app/plant/list")
    Call<GetAllPlantResponse> getAllPlants();
}
