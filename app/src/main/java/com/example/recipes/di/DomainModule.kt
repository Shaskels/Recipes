package com.example.recipes.di

import com.example.recipes.data.repository.RecipesRepositoryImpl
import com.example.recipes.domain.repository.RecipesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindRecipesRepository(recipesRepositoryImpl: RecipesRepositoryImpl): RecipesRepository
}