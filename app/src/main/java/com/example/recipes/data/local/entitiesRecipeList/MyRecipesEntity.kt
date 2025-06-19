package com.example.recipes.data.local.entitiesRecipeList

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myRecipes")
data class MyRecipesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "recipeId")
    val recipeId: Int,
)