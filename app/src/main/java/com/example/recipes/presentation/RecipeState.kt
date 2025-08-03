package com.example.recipes.presentation

import com.example.recipes.domain.entity.Recipe

sealed class RecipeState {
    data object Loading : RecipeState()

    data object Error : RecipeState()

    data class Success(val recipe: Recipe) : RecipeState()
}