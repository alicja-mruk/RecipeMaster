package com.recipemaster.contract

import android.content.Context
import com.recipemaster.model.pojo.Recipe

interface RecipeDetailsContract {
    interface View {
        fun initView()
        fun updateView(recipe: Recipe?)
        fun displayRecipe(recipe:Recipe?)
        fun displayTextFields(recipe:Recipe?)
        fun displayPhotos(photos : List<String>)
        fun showLoadingError(errorMessage: String?)
        fun onSavePhotosClickListeners(photos: List<String>)
        fun callSavePicture(url : String)
        fun showConfirmDialog()
        fun requestPermissions()
    }

    interface Presenter {
        fun dropView()
        fun savePicture(url : String)
        fun getRecipeData()
        fun formatIngredients(ingredients : List<String>) : String
        fun formatPreparing(preparing : List<String>) : String
    }

    interface Model {
        fun savePictureIntoStorage(url : String)
    }

    interface OnResponseCallback {
        fun onResponse(recipe: Recipe)
        fun onError(errorMessage: String?)
    }

}