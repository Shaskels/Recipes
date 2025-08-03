package com.example.recipes.data.local.entitiesRecipeList

import com.example.recipes.domain.entity.Ingredient
import com.example.recipes.domain.entity.Recipe
import com.example.recipes.domain.entity.Step

fun RecipeWithInfo.toRecipe(): Recipe = Recipe(
    idInApi = recipeEntity.idInApi,
    name = recipeEntity.title,
    image = recipeEntity.image,
    score = recipeEntity.spoonacularScore,
    likes = recipeEntity.aggregateLikes,
    ingredients = ingredients.map { Ingredient(it.name, it.amount, it.unit) },
    cookTime = recipeEntity.readyInMinutes,
    instructions = steps.map { Step(it.number, it.step) }
)
