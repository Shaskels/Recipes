package com.example.recipes.domain.usecases

import com.example.recipes.domain.Recipe
import com.example.recipes.domain.repository.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllRecipes @Inject constructor(private val recipesRepository: RecipesRepository) {
    suspend operator fun invoke(): List<Recipe>{
        return withContext(Dispatchers.IO){
            recipesRepository.getRecipesFromRemote()
        }
    }
}