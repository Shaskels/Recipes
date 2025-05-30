package com.example.recipes.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.recipes.data.local.StepsEntity.Companion.ID_COLUMN_NAME
import com.example.recipes.data.local.StepsEntity.Companion.RECIPE_ID_COLUMN_NAME
import com.example.recipes.data.local.StepsEntity.Companion.TABLE_NAME


@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(entity = RecipeEntity::class,
        parentColumns = arrayOf(ID_COLUMN_NAME),
        childColumns = arrayOf(RECIPE_ID_COLUMN_NAME),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE,
    )]
)
data class StepsEntity(
    @PrimaryKey
    @ColumnInfo(ID_COLUMN_NAME)
    val id: Int,
    @ColumnInfo(RECIPE_ID_COLUMN_NAME)
    val recipeId: Int,
    @ColumnInfo("name")
    val number: Int,
    @ColumnInfo("step")
    val step: String,
){
    companion object{
        const val TABLE_NAME = "Steps"
        const val ID_COLUMN_NAME = "id"
        const val RECIPE_ID_COLUMN_NAME = "recipeId"
    }
}