package com.recipemaster.contract

import android.content.Context
import com.recipemaster.model.pojo.Recipe

interface RecipeDetailsContract {
    interface View {
        fun initView()
        fun updateView(recipe: Recipe?)
        fun displayRecipe(recipe:Recipe?)
        fun showLoadingError(errorMessage: String?)
        fun getContext(): Context
    }

    interface Presenter {
        fun dropView()
        fun getRecipeData()
        fun getCurrentUserData()
    }

    interface Model {
        fun fetchData()
    }

    interface OnResponseCallback {
        fun onResponse(recipe: Recipe)
        fun onError(errorMessage: String?)
    }
}