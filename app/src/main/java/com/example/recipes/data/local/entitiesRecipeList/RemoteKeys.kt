package com.example.recipes.data.local.entitiesRecipeList

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipes.data.local.entitiesRecipeList.RemoteKeys.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RemoteKeys(
    @PrimaryKey
    val id: Int,
    val recipeId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
)
{
    companion object{
        const val TABLE_NAME = "remote_keys"
    }
}