package com.example.recipes.data.remote.entities

import com.example.recipes.domain.Ingredient
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.Step

fun RecipeRequest.toRecipe() = Recipe(
    idInApi = id,
    name = title,
    image = image,
    score = spoonacularScore,
    likes = aggregateLikes,
    ingredients = extendedIngredients.map { Ingredient(it.name, it.amount, it.units ?: "") },
    cookTime = readyInMinutes,
    instructions = analyzedInstructions[0].steps.map { Step(it.number, it.step) },
)