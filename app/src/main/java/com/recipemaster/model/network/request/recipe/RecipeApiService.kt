package com.recipemaster.model.network.request.recipe

import com.recipemaster.model.pojo.Recipe
import retrofit2.Call
import retrofit2.http.GET


interface RecipeApiService {
     @GET("/test/info.php")
        fun getRecipe(): Call<Recipe>
}
