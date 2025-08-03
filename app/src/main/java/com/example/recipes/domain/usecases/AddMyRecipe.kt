package com.example.recipes.domain.usecases

import com.example.recipes.domain.entity.Recipe
import com.example.recipes.domain.repository.MyRecipeRepository
import javax.inject.Inject

class AddMyRecipe @Inject constructor(private val myRecipeRepository: MyRecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) {
        myRecipeRepository.insertRecipe(recipe)
    }
}