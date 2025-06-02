package com.example.recipes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipes.data.local.entities.IngredientsEntity
import com.example.recipes.data.local.entities.RecipeEntity
import com.example.recipes.data.local.entities.RemoteKeys
import com.example.recipes.data.local.entities.StepsEntity

@Database(
    entities = [RecipeEntity::class, IngredientsEntity::class, StepsEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun remoteKeyDao(): RemoteKeysDao
    abstract fun ingredientsDao(): IngredientsDao
    abstract fun stepsDao(): StepsDao
}