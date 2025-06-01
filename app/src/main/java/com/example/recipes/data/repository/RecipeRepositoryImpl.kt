package com.example.recipes.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.data.remote.RecipeRemoteMediator
import com.example.recipes.data.remote.RecipeService
import com.example.recipes.data.remote.entities.toRecipe
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val service: RecipeService,
    private val database: RecipeDatabase,
) : RecipesRepository {

    override fun getRecipesStream(query: String): Flow<PagingData<Recipe>> {

        val dbQuery = "%${query.trim().lowercase()}%"
        val pagingSourceFactory = { database.recipeDao().getAll(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = RecipeRemoteMediator(
                query,
                service,
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
                    null)
            }
        }
    }

    override suspend fun getRecipeById(idInApi: Int): Recipe {
        return service.getRecipeById(idInApi).toRecipe()
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}