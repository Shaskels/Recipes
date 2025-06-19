package com.example.recipes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.data.local.entitiesRecipeList.StepsEntity

@Dao
interface StepsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<StepsEntity>)

    @Query("DELETE FROM ${StepsEntity.TABLE_NAME}")
    suspend fun clearRecipes()
}