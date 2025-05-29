package com.example.recipes.presentation

import com.example.recipes.domain.Recipe

sealed class RecipeListState {
    data object Loading : RecipeListState()

    data object Error : RecipeListState()

    data class Success(val recipes: List<Recipe>) : RecipeListState()
}