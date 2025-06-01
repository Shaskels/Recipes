package com.example.recipes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.data.local.entities.IngredientsEntity

@Dao
interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<IngredientsEntity>)

    @Query("DELETE FROM ${IngredientsEntity.TABLE_NAME}")
    suspend fun clearRecipes()
}