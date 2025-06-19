package com.example.recipes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.data.local.entitiesRecipeList.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * FROM ${RemoteKeys.TABLE_NAME} WHERE id = :recipeId ORDER BY nextKey ASC")
    suspend fun remoteKeysRecipeId(recipeId: Int): RemoteKeys?

    @Query("DELETE FROM ${RemoteKeys.TABLE_NAME}")
    suspend fun clearRemoteKeys()
}