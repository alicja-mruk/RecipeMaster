package com.recipemaster.model.repository.recipe

import com.recipemaster.contract.RecipeDetailsContract

interface RecipeRepository {
    fun getRecipe(onResponseCallback: RecipeDetailsContract.OnResponseCallback)
}