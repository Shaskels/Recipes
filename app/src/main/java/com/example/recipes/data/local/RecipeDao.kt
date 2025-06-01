package com.example.recipes.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.data.local.entities.RecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

//    @Transaction
//    @Query(
//        "SELECT * FROM ${RecipeEntity.TABLE_NAME}"
//    )
//    fun getAllRecipes(): PagingSource<Int, RecipeWithInfo>

    @Query(
        "SELECT * FROM ${RecipeEntity.TABLE_NAME} WHERE title LIKE :query ORDER BY id ASC"
    )
    fun getAll(query: String): PagingSource<Int, RecipeEntity>

    @Query(
        "SELECT MAX(lastUpdate) FROM ${RecipeEntity.TABLE_NAME}"
    )
    fun getLastUpdate(): Long

    @Query("DELETE FROM ${RecipeEntity.TABLE_NAME}")
    suspend fun clearRecipes()
}