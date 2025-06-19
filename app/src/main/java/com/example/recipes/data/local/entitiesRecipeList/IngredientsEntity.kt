package com.example.recipes.data.local.entitiesRecipeList

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.recipes.data.local.entitiesRecipeList.IngredientsEntity.Companion.ID_COLUMN_NAME
import com.example.recipes.data.local.entitiesRecipeList.IngredientsEntity.Companion.RECIPE_ID_COLUMN_NAME
import com.example.recipes.data.local.entitiesRecipeList.IngredientsEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = arrayOf(ID_COLUMN_NAME),
        childColumns = arrayOf(RECIPE_ID_COLUMN_NAME),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE,)]
)
data class IngredientsEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID_COLUMN_NAME)
    val id: Int,
    @ColumnInfo(RECIPE_ID_COLUMN_NAME, index = true)
    val recipeId: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("amount")
    val amount: Double,
    @ColumnInfo("unit")
    val unit: String,
){
    companion object{
        const val TABLE_NAME="Ingredients"
        const val ID_COLUMN_NAME="id"
        const val RECIPE_ID_COLUMN_NAME="recipeId"
    }
}