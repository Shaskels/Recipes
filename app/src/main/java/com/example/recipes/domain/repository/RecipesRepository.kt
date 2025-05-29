package com.example.recipes.domain.repository

import com.example.recipes.domain.Recipe

interface RecipesRepository {

    suspend fun getRecipesFromRemote(): List<Recipe>
}