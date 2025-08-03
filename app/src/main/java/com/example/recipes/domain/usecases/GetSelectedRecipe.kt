package com.example.recipes.domain.usecases

import com.example.recipes.domain.entity.Recipe
import com.example.recipes.domain.repository.RecipesRepository
import javax.inject.Inject

class GetSelectedRecipe @Inject constructor(private val recipesRepository: RecipesRepository) {
    suspend operator fun invoke(id: Int): Recipe {
        return recipesRepository.getRecipeById(id)
    }
}