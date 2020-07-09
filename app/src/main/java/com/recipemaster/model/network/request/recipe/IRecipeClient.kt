package com.recipemaster.model.network.request.recipe

import com.recipemaster.contract.RecipeDetailsContract

interface IRecipeClient {
    fun getRecipe(onResponseCallback: RecipeDetailsContract.OnResponseCallback)
}