package com.example.recipes.domain.usecases

import androidx.paging.PagingData
import com.example.recipes.domain.entity.Recipe
import com.example.recipes.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRecipes @Inject constructor(private val recipesRepository: RecipesRepository) {
    operator fun invoke(query: String): Flow<PagingData<Recipe>>{
        return recipesRepository.getRecipesStream(query)
    }
}