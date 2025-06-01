package com.example.recipes.data.remote.entities

import com.google.gson.annotations.SerializedName

data class RecipeRequest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,
    @SerializedName("spoonacularScore")
    val spoonacularScore: Double,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<IngredientsRequest>,
    @SerializedName("analyzedInstructions")
    val analyzedInstructions: List<InstructionRequest>,
)