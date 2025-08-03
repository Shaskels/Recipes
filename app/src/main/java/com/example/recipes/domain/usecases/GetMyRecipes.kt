package com.example.recipes.domain.usecases

import com.example.recipes.domain.entity.Recipe
import com.example.recipes.domain.repository.MyRecipeRepository
import javax.inject.Inject

class GetMyRecipes @Inject constructor(private val myRecipeRepository: MyRecipeRepository) {
    suspend operator fun invoke(): List<Recipe> {
        return myRecipeRepository.getAll()
    }
}