package com.example.recipes.data.local.entitiesMyRecipes

import com.example.recipes.domain.Ingredient
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.Step

//fun MyRecipeWithInfo.toRecipe(): Recipe = Recipe(
//    idInApi = recipeEntity.id,
//    name = recipeEntity.title,
//    image = recipeEntity.image,
//    score = recipeEntity.spoonacularScore,
//    likes = recipeEntity.aggregateLikes,
//    ingredients = ingredients.map { Ingredient(it.name, it.amount, it.unit) },
//    cookTime = recipeEntity.readyInMinutes,
//    instructions = steps.map { Step(it.number, it.step) }
//)