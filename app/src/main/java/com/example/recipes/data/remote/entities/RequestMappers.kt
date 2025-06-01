package com.example.recipes.data.remote.entities

import com.example.recipes.domain.Recipe

fun RecipeRequest.toRecipe() = Recipe(
    idInApi = id,
    name = title,
    image = image,
    score = spoonacularScore,
    likes = aggregateLikes,
    ingredients = emptyList(),
    cookTime = readyInMinutes,
    instructions = emptyList(),
)