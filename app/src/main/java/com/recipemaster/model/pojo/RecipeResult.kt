package com.recipemaster.model.pojo

import com.google.gson.annotations.SerializedName

data class Recipe(
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val preparing: List<String>,
    @SerializedName(value = "imgs")
    val photos: List<String>
)
