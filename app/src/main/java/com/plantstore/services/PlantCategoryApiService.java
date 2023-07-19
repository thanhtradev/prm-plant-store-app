package com.plantstore.services;

import com.plantstore.models.api.PlantCategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlantCategoryApiService {
    @GET("app/plant-category/list")
    Call<PlantCategoryResponse> getPlantCategories();
}
