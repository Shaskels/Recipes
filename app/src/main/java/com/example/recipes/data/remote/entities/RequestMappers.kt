package com.example.recipes.data.remote.entities

import com.example.recipes.domain.Recipe

fun RecipeRequest.toRecipe() = Recipe(
    name = title,
    image = image,
    score = 0.0,
    likes = 0,
    ingredients = emptyList(),
    cookTime = 0,
    category = "dinner",
    instructions = emptyList(),
)