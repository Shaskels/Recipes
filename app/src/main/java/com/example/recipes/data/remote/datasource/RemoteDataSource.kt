package com.example.recipes.data.remote.datasource

import com.example.recipes.data.remote.entities.RecipeRequest

interface RemoteDataSource {

    suspend fun getRecipes(query:String, page: Int, itemsPerPage: Int): List<RecipeRequest>

    suspend fun getRecipeById(id: Int): RecipeRequest
}