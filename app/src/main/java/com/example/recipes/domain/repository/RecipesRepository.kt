package com.example.recipes.domain.repository

import androidx.paging.PagingData
import com.example.recipes.domain.entity.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getRecipesStream(query: String): Flow<PagingData<Recipe>>

    suspend fun getRecipeById(idInApi :Int): Recipe
}