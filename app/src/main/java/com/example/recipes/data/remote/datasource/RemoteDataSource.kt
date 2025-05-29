package com.example.recipes.data.remote.datasource

import com.example.recipes.data.remote.entities.RecipeRequest

interface RemoteDataSource {

    suspend fun getRecipes(): List<RecipeRequest>
}