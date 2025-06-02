package com.example.recipes.data.remote


import com.example.recipes.BuildConfig
import com.example.recipes.data.remote.entities.RecipeRequest
import com.example.recipes.data.remote.entities.RecipesRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

    @GET ("complexSearch?&apiKey=${BuildConfig.CONSUMER_KEY}")
    suspend fun getRecipes(@Query("query") query: String, @Query("offset") page: Int, @Query("number") itemsPerPage: Int): RecipesRequest

    @GET ("{id}/information?apiKey=${BuildConfig.CONSUMER_KEY}&addTasteData=true")
    suspend fun getRecipeById(@Path("id") id: Int): RecipeRequest
}