package com.example.recipes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.recipes.data.local.entitiesRecipeList.MyRecipesEntity
import com.example.recipes.data.local.entitiesRecipeList.RecipeEntity
import com.example.recipes.data.local.entitiesRecipeList.RecipeWithInfo

@Dao
interface MyRecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: MyRecipesEntity)

    @Query("DELETE FROM myRecipes WHERE recipeId = :id")
    suspend fun delete(id: Int)

    @Query("SELECT Recipes.id, Recipes.idInApi, Recipes.image, Recipes.title, " +
            "Recipes.readyInMinutes, Recipes.likes, Recipes.score, Recipes.lastUpdate FROM Recipes " +
            "INNER JOIN myRecipes ON Recipes.idInApi = myRecipes.recipeId")
    suspend fun getAll(): List<RecipeEntity>

    @Transaction
    @Query("SELECT * FROM Recipes WHERE idInApi = :id")
    suspend fun getById(id: Int): RecipeWithInfo

}