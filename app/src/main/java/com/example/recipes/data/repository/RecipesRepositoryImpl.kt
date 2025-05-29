package com.example.recipes.data.repository

import com.example.recipes.data.remote.datasource.RemoteDataSource
import com.example.recipes.data.remote.entities.toRecipe
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.repository.RecipesRepository
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): RecipesRepository {
    override suspend fun getRecipesFromRemote() : List<Recipe>{
        return remoteDataSource.getRecipes().map { it.toRecipe() }
    }
}