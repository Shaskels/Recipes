package com.example.recipes.presentation

import com.example.recipes.domain.Recipe

sealed class MyRecipesState {
    data object Error:  MyRecipesState()
    data class Updated(val recipes: List<Recipe>): MyRecipesState()
}