package com.example.recipes.di

import com.example.recipes.data.repository.MyRecipeRepositoryImpl
import com.example.recipes.data.repository.RecipeRepositoryImpl
import com.example.recipes.domain.repository.MyRecipeRepository
import com.example.recipes.domain.repository.RecipesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindRecipesRepository(recipesRepositoryImpl: RecipeRepositoryImpl): RecipesRepository

    @Binds
    fun bindMyRecipeRepository(myRecipeRepositoryImpl: MyRecipeRepositoryImpl): MyRecipeRepository
}