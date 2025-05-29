package com.example.recipes.domain

data class Recipe (
    val name: String,
    val image: String,
    val score: Double,
    val likes: Int,
    val ingredients: List<Ingredient>,
    val cookTime: Int,
    val category: String,
    val instructions: List<Step>,
)