package com.example.recipes.di

import android.content.Context
import androidx.room.Room
import com.example.recipes.data.local.RecipeDao
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.data.local.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(context, RecipeDatabase::class.java, "recipe_db").build()
    }

    @Provides
    fun provideRecipeDao(recipeDatabase: RecipeDatabase): RecipeDao{
        return recipeDatabase.recipeDao()
    }

    @Provides
    fun provideRemoteKeysDao(recipeDatabase: RecipeDatabase): RemoteKeysDao{
        return recipeDatabase.remoteKeyDao()
    }
}