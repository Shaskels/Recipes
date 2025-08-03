package com.example.recipes.domain.usecases

import com.example.recipes.domain.repository.MyRecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteMyRecipe @Inject constructor(private val myRecipeRepository: MyRecipeRepository) {
    suspend operator fun invoke(id: Int) {
        myRecipeRepository.deleteRecipe(id)
    }
}