package com.example.recipes.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.data.remote.RecipeRemoteMediator
import com.example.recipes.data.remote.RecipeService
import com.example.recipes.domain.Ingredient
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.Step
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepository(
    private val service: RecipeService,
    private val database: RecipeDatabase,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getRecipesStream(): Flow<PagingData<Recipe>> {
        val pagingSourceFactory = { database.recipeDao().getAll() }


        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = RecipeRemoteMediator(
                "query",
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { recipe ->
                Recipe(
                    recipe.recipeEntity.title,
                    recipe.recipeEntity.image,
                    recipe.recipeEntity.spoonacularScore,
                    recipe.recipeEntity.aggregateLikes,
                    recipe.ingredients.map { ing -> Ingredient(ing.name, ing.amount, ing.unit) },
                    recipe.recipeEntity.readyInMinutes,
                    recipe.steps.map { st -> Step(st.number, st.step) })
            }
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}