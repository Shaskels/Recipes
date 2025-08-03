package com.example.recipes.data.repository

import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.data.local.entitiesRecipeList.MyRecipesEntity
import com.example.recipes.data.local.entitiesRecipeList.IngredientsEntity
import com.example.recipes.data.local.entitiesRecipeList.RecipeEntity
import com.example.recipes.data.local.entitiesRecipeList.StepsEntity
import com.example.recipes.data.local.entitiesRecipeList.toRecipe
import com.example.recipes.data.remote.datasource.RemoteDataSource
import com.example.recipes.data.remote.entities.toRecipe
import com.example.recipes.domain.entity.Recipe
import com.example.recipes.domain.repository.MyRecipeRepository
import javax.inject.Inject

class MyRecipeRepositoryImpl @Inject constructor(private val database: RecipeDatabase, private val remoteDataSource: RemoteDataSource) :
    MyRecipeRepository {
    override suspend fun getAll(): List<Recipe> {
        return database.myRecipesDao().getAll().map {
            Recipe(
                it.idInApi,
                it.title,
                it.image,
                it.spoonacularScore,
                it.aggregateLikes,
                arrayListOf(),
                it.readyInMinutes,
                arrayListOf(),
            )
        }
    }

    override suspend fun deleteRecipe(id: Int) {
        database.myRecipesDao().delete(id)
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        database.myRecipesDao()
            .insert(MyRecipesEntity(recipeId = recipe.idInApi))
    }

    override suspend fun getById(id: Int): Recipe {
        val res = database.myRecipesDao().getById(id)
        if (res.recipeEntity.aggregateLikes != null) {
            return res.toRecipe()
        } else {
            val recipeRequest = remoteDataSource.getRecipeById(id)
            val ingredients = recipeRequest.extendedIngredients.map {
                IngredientsEntity(
                    0,
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
                    res.recipeEntity.lastUpdate,
                )
            )
            database.ingredientsDao().insertAll(ingredients)
            database.stepsDao().insertAll(steps)
            return recipeRequest.toRecipe()
        }
    }
}