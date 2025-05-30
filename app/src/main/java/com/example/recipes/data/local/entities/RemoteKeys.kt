package com.example.recipes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipes.data.local.RemoteKeys.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RemoteKeys(
    @PrimaryKey
    val recipeId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
)
{
    companion object{
        const val TABLE_NAME = "remote_keys"
    }
}