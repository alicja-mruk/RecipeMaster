package com.recipemaster.contract

import android.content.Context
import com.karumi.dexter.MultiplePermissionsReport
import com.recipemaster.model.pojo.Recipe

interface RecipeDetailsContract {
    interface View {
        fun initView()
        fun getContext() : Context?
        fun showToast(message:String)
        fun updateView(recipe: Recipe?)
        fun displayRecipe(recipe:Recipe?)
        fun displayTextFields(recipe:Recipe?)
        fun displayPhotos(photos : List<String>)
        fun showLoadingError(errorMessage: String?)
        fun onSavePhotosClickListeners(photos: List<String>)
        fun setClickedPictureUrl(url : String)
        fun getClickedPictureUrl() : String
        fun showConfirmDialog()
        fun updateUserName(userName : String?)
        fun updateUserProfilePicture(profilePicture:String?)
        fun updateFooter(userName:String?, photoUrl : String?)
    }

    interface Presenter {
        fun dropView()
        fun savePicture(url : String)
        fun isGetRecipeAvailable () : Boolean
        fun getRecipeData()
        fun formatIngredients(ingredients : List<String>) : String
        fun formatPreparing(preparing : List<String>) : String
        fun checkPermissions()
        fun requestPermissions()
        fun callModelOnSavePicture()
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