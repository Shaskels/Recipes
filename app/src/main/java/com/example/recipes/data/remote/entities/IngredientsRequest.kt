package com.example.recipes.data.remote.entities

import com.google.gson.annotations.SerializedName

data class IngredientsRequest (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("units")
    val units: String,
)