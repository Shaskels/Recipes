package com.example.recipes.data.remote.entities

import com.google.gson.annotations.SerializedName

data class StepRequest (
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String,
)