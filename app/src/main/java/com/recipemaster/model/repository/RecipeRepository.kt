package com.recipemaster.model.repository

import com.recipemaster.contract.RecipeDetailsContract

interface RecipeRepository {
    fun getRecipe(onResponseCallback: RecipeDetailsContract.OnResponseCallback)
}