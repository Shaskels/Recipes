package com.example.recipes.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipes.data.local.RecipeEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RecipeEntity (
    @PrimaryKey
    @ColumnInfo(ID_COLUMN_NAME)
    val id: Int,
    val image: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("readyInMinutes")
    val readyInMinutes: Int,
    @ColumnInfo("likes")
    val aggregateLikes: Int,
    @ColumnInfo("score")
    val spoonacularScore: Double,
) {
    companion object{
        const val TABLE_NAME = "Recipes"
        const val ID_COLUMN_NAME = "id"
    }
}