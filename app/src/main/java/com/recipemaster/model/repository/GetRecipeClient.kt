package com.recipemaster.model.repository

import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.network.request.RecipeApiService
import com.recipemaster.model.network.request.ServiceBuilder
import com.recipemaster.model.pojo.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetRecipeClient : RecipeRepository {
    override fun getRecipe(onResponseCallback: RecipeDetailsContract.OnResponseCallback) {

        val request  = ServiceBuilder.buildService(RecipeApiService::class.java)
        val call = request.getRecipe()

        call.enqueue(object: Callback<Recipe> {
            override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                if (response.isSuccessful) {
                    onResponseCallback.onResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Recipe>, t: Throwable) {
                onResponseCallback.onError(t.message)
            }

        })
    }
}