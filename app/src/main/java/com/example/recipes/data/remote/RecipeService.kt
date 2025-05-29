package com.example.recipes.data.remote

import com.example.recipes.BuildConfig
import com.example.recipes.data.remote.entities.RecipesRequest
import retrofit2.http.GET

interface RecipeService {

    @GET ("complexSearch?query=&number=100&apiKey=${BuildConfig.CONSUMER_KEY}")
    suspend fun getRecipes(): RecipesRequest
}