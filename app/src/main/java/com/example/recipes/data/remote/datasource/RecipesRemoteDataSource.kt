package com.example.recipes.data.remote.datasource

import com.example.recipes.data.remote.entities.RecipeRequest
import com.example.recipes.data.remote.RecipeService
import javax.inject.Inject

class RecipesRemoteDataSource @Inject constructor(private val recipeService: RecipeService) : RemoteDataSource{

    override suspend fun getRecipes(): List<RecipeRequest>{
        return recipeService.getRecipes().results
    }
}