package com.example.recipes.domain

data class Recipe (
    val idInApi: Int,
    val name: String,
    val image: String,
    val score: Double?,
    val likes: Int?,
    val ingredients: List<Ingredient>?,
    val cookTime: Int?,
    val instructions: List<Step>?,
    var isLiked: Boolean = false,
)