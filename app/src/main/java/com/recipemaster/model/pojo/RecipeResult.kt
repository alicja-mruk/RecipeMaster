package com.recipemaster.model.pojo

import com.google.gson.annotations.SerializedName

data class Recipe(
    var title: String,
    var description: String,
    var ingredients: List<String>,
    var preparing: List<String>,
    @SerializedName(value = "imgs")
    var photos: List<String>
)
