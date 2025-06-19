package com.example.recipes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipes.data.local.entitiesRecipeList.IngredientsEntity
import com.example.recipes.data.local.entitiesRecipeList.MyRecipesEntity
import com.example.recipes.data.local.entitiesRecipeList.RecipeEntity
import com.example.recipes.data.local.entitiesRecipeList.RemoteKeys
import com.example.recipes.data.local.entitiesRecipeList.StepsEntity

@Database(
    entities = [RecipeEntity::class, IngredientsEntity::class, StepsEntity::class, RemoteKeys::class, MyRecipesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun remoteKeyDao(): RemoteKeysDao
    abstract fun ingredientsDao(): IngredientsDao
    abstract fun stepsDao(): StepsDao
    abstract fun myRecipesDao(): MyRecipesDao

}