package com.example.recipes.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithInfo (
    @Embedded
    val recipeEntity: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientsEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val steps: List<StepsEntity>
)