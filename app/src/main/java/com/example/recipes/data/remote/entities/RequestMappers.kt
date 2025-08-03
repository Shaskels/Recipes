package com.example.recipes.data.remote.entities

import com.example.recipes.domain.entity.Ingredient
import com.example.recipes.domain.entity.Recipe
import com.example.recipes.domain.entity.Step

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