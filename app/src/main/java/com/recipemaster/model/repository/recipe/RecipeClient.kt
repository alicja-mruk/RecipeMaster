package com.recipemaster.model.repository.recipe

import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.network.request.RecipeApiService
import com.recipemaster.model.network.request.ServiceBuilder
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.util.UrlReplacer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeClient : RecipeRepository {
    override fun getRecipe(onResponseCallback: RecipeDetailsContract.OnResponseCallback) {

        val request  = ServiceBuilder.buildService(RecipeApiService::class.java)
        val call = request.getRecipe()

        call.enqueue(object: Callback<Recipe> {
            override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                if (response.isSuccessful) {

                    val recipe: Recipe = response.body()!!
                    recipe.photos = UrlReplacer(recipe.photos).replace()
                    onResponseCallback.onResponse(recipe)
                }
            }

            override fun onFailure(call: Call<Recipe>, t: Throwable) {
                onResponseCallback.onError(t.message)
            }

        })
    }

}