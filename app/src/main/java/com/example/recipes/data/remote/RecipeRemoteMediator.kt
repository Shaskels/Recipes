package com.example.recipes.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.data.local.entities.RecipeEntity
import com.example.recipes.data.local.entities.RemoteKeys
import com.example.recipes.data.remote.datasource.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale
import java.util.concurrent.TimeUnit

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class RecipeRemoteMediator(
    private val query: String,
    private val remoteDataSource: RemoteDataSource,
    private val recipeDatabase: RecipeDatabase,
) : RemoteMediator<Int, RecipeEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val lastUpdate: Long
        coroutineScope {
            val res = async(Dispatchers.IO) {
                recipeDatabase.recipeDao().getLastUpdate()
            }
            lastUpdate = res.await()
        }
        return if (System.currentTimeMillis() - lastUpdate <= cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                Log.d("Mediator", "REFRESH")
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                Log.d("Mediator", "PREPEND")
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                Log.d("Mediator", "APPEND")
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        val apiQuery = query.trim().lowercase(Locale.getDefault())
        try {
            Log.e("Mediator", page.toString())
            val apiResponse =
                remoteDataSource.getRecipes(
                    apiQuery,
                    (page - 1) * state.config.pageSize,
                    state.config.pageSize
                )

            val recipes = apiResponse
            Log.e("Mediator", recipes.size.toString())
            val endOfPaginationReached = recipes.isEmpty()
            recipeDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    recipeDatabase.remoteKeyDao().clearRemoteKeys()
                    recipeDatabase.recipeDao().clearRecipes()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                var ids = (page - 1) * state.config.pageSize - 1
                val recipeEntities =
                    recipes.map {
                        ids += 1
                        RecipeEntity(
                            id = ids,
                            idInApi = it.id,
                            image = it.image,
                            title = it.title,
                            readyInMinutes = null,
                            aggregateLikes = null,
                            spoonacularScore = null,
                            lastUpdate = System.currentTimeMillis()
                        )
                    }
                ids = (page - 1) * state.config.pageSize - 1
                val keys = recipes.map {
                    ids += 1
                    RemoteKeys(recipeId = ids, prevKey = prevKey, nextKey = nextKey)
                }
                recipeDatabase.remoteKeyDao().insertAll(keys)
                recipeDatabase.recipeDao().insertAll(recipeEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("Mediator", e.message.toString())
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecipeEntity>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { recipe ->
                recipeDatabase.remoteKeyDao().remoteKeysRecipeId(recipe.id)
            }

    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecipeEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { recipe ->
                recipeDatabase.remoteKeyDao().remoteKeysRecipeId(recipe.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RecipeEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { recipeId ->
                recipeDatabase.remoteKeyDao().remoteKeysRecipeId(recipeId)
            }
        }
    }
}
