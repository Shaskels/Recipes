package com.example.recipes.data.remote.entities

import com.google.gson.annotations.SerializedName

data class RecipesRequest (
    @SerializedName("results")
    val results: List<RecipeRequest>
)