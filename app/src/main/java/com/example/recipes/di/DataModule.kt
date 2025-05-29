package com.example.recipes.di

import com.example.recipes.data.remote.datasource.RecipesRemoteDataSource
import com.example.recipes.data.remote.datasource.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRemoteDataSource(recipesRemoteDataSource: RecipesRemoteDataSource): RemoteDataSource
}