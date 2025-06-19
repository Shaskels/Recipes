package com.example.recipes.domain.repository

import com.example.recipes.domain.Recipe

interface MyRecipeRepository {

    suspend fun getAll(): List<Recipe>

    suspend fun deleteRecipe(id: Int)

    suspend fun insertRecipe(recipe: Recipe)

    suspend fun getById(id: Int): Recipe

}