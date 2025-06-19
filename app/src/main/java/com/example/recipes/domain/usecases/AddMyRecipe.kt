package com.example.recipes.domain.usecases

import com.example.recipes.domain.Recipe
import com.example.recipes.domain.repository.MyRecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddMyRecipe @Inject constructor(private val myRecipeRepository: MyRecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            myRecipeRepository.insertRecipe(recipe)
        }
    }
}