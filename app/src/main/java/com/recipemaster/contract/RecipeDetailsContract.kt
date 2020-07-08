package com.recipemaster.contract

import android.os.Bundle
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
        fun updateUserName(userName : String?)
        fun updateUserProfilePicture(profilePicture:String?)
        fun updateFooter(userName:String?, photoUrl : String?)
    }

    interface Presenter {
        fun dropView()
        fun savePicture(url : String)
        fun getRecipeData()
        fun formatIngredients(ingredients : List<String>) : String
        fun formatPreparing(preparing : List<String>) : String
        fun requestPermissions()
        fun allPermissionsGranted(report: MultiplePermissionsReport) : Boolean
        fun callUpdateFooterView()
    }

    interface Model {
        fun savePictureIntoStorage(url : String)
    }

    interface OnResponseCallback {
        fun onResponse(recipe: Recipe)
        fun onError(errorMessage: String?)
    }

}