package com.example.recipes.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.data.local.entities.IngredientsEntity
import com.example.recipes.data.local.entities.RecipeEntity
import com.example.recipes.data.local.entities.StepsEntity
import com.example.recipes.data.local.entities.toRecipe
import com.example.recipes.data.remote.RecipeRemoteMediator
import com.example.recipes.data.remote.datasource.RemoteDataSource
import com.example.recipes.data.remote.entities.toRecipe
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val database: RecipeDatabase,
) : RecipesRepository {

    override fun getRecipesStream(query: String): Flow<PagingData<Recipe>> {

        val dbQuery = "%${query.trim().lowercase()}%"
        val pagingSourceFactory = { database.recipeDao().getAll(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = RecipeRemoteMediator(
                query,
                remoteDataSource,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { recipe ->
                Recipe(
                    recipe.idInApi,
                    recipe.title,
                    recipe.image,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            }
        }
    }

    override suspend fun getRecipeById(idInApi: Int): Recipe {
        val res = database.recipeDao().getByApiId(idInApi)
        if (res.recipeEntity.aggregateLikes != null) {
            return res.toRecipe()
        } else {
            val recipeRequest = remoteDataSource.getRecipeById(idInApi)
            val ingredients = recipeRequest.extendedIngredients.map {
                IngredientsEntity(
                    it.id,
                    res.recipeEntity.id,
                    it.name,
                    it.amount,
                    it.units ?: "",
                )
            }
            val steps = recipeRequest.analyzedInstructions[0].steps.map {
                StepsEntity(
                    recipeId = res.recipeEntity.id,
                    number = it.number,
                    step = it.step,
                )
            }
            database.recipeDao().updateRecipe(
                RecipeEntity(
                    res.recipeEntity.id,
                    res.recipeEntity.idInApi,
                    recipeRequest.image,
                    recipeRequest.title,
                    recipeRequest.readyInMinutes,
                    recipeRequest.aggregateLikes,
                    recipeRequest.spoonacularScore,
                    res.recipeEntity.lastUpdate
                )
            )
            database.ingredientsDao().insertAll(ingredients)
            database.stepsDao().insertAll(steps)
            return recipeRequest.toRecipe()
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}