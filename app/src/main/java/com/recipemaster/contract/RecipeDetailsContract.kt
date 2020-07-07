package com.recipemaster.contract

import android.content.Context
import android.content.Intent
import com.facebook.CallbackManager
import com.karumi.dexter.MultiplePermissionsReport
import com.recipemaster.model.pojo.Recipe

interface RecipeDetailsContract {
    interface View {
        fun initView()
        fun showToast(message:String)
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
        fun requestPermissions(permissions : List<String>)
        fun allPermissionsGranted(report: MultiplePermissionsReport) : Boolean

    }

    interface Model {
        fun savePictureIntoStorage(url : String)
    }

    interface OnResponseCallback {
        fun onResponse(recipe: Recipe)
        fun onError(errorMessage: String?)
    }

}