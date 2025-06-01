package com.example.recipes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.data.local.entities.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * FROM ${RemoteKeys.TABLE_NAME} WHERE recipeId = :recipeId")
    suspend fun remoteKeysRecipeId(recipeId: Int): RemoteKeys?

    @Query("DELETE FROM ${RemoteKeys.TABLE_NAME}")
    suspend fun clearRemoteKeys()
}