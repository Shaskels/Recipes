package com.example.recipes.domain.usecases

import com.example.recipes.domain.Recipe
import com.example.recipes.domain.repository.MyRecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMyRecipes @Inject constructor(private val myRecipeRepository: MyRecipeRepository) {
    suspend operator fun invoke(): List<Recipe>{
        return withContext(Dispatchers.IO) {
            myRecipeRepository.getAll()
        }
    }
}