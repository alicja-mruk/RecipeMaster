package com.recipemaster.presenter

import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.recipe.RecipeRepository
import com.recipemaster.model.repository.shared_preferences.SharedPreferencesManagerImpl
import com.recipemaster.util.Permissions
import com.recipemaster.model.storage.RecipeDetailsService
import com.recipemaster.util.processData.TextFormater
import com.recipemaster.view.RecipeDetailsActivity


class RecipeDetailsPresenter(
    _view: RecipeDetailsContract.View?,
    _client: RecipeRepository,
    _storage_client : RecipeDetailsService
)
    :RecipeDetailsContract.Presenter{

    private var view: RecipeDetailsContract.View? = _view
    private val networkClient: RecipeRepository = _client
    private val storageClient : RecipeDetailsService = _storage_client


    init {
        view?.initView()
    }

    override fun dropView() {
        view = null
    }

    override fun savePicture(url : String) {
        try{
            storageClient.savePictureIntoStorage(url)
        }catch(e:Exception){
            e.printStackTrace()
            view?.showToast(SAVING_ERROR)
        }

    }

    override fun getRecipeData(){
        //todo check network connection
        networkClient.getRecipe(callback)
        callUpdateFooterView()
    }


    override fun formatIngredients(ingredients : List<String>) : String {
        return TextFormater.processIngredients(ingredients)
    }

    override fun formatPreparing(preparing : List<String>): String {
        return TextFormater.processPreparing(preparing)
    }

    override fun callUpdateFooterView(){
        val name = SharedPreferencesManagerImpl.getCurrentUserName()
        val userPhotoUrl = SharedPreferencesManagerImpl.getCurrentUserPhotoUrl()
        view?.updateUserName(name)
        view?.updateUserProfilePicture(userPhotoUrl)
    }

    override fun requestPermissions() {
        if(SharedPreferencesManagerImpl.areStoragePermissions()){
            view?.showConfirmDialog()
        }else{
            Dexter.withContext(RecipeDetailsActivity.getContext())
                .withPermissions(Permissions.storagePermissions)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if(allPermissionsGranted(report)){
                            SharedPreferencesManagerImpl.setStoragePermissions(true)

                        }
                        else{
                            view?.showToast(PERMISSION_DENIED)
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {
                        view?.showToast(PERMISSION_RATIONALE)
                    }
                }).check()
        }


    }

    private var callback = object : RecipeDetailsContract.OnResponseCallback {
        override fun onResponse(recipe: Recipe) {
            view?.updateView(recipe)
        }

        override fun onError(errorMessage: String?) {
            view?.showLoadingError(errorMessage)
        }
    }

    override fun allPermissionsGranted(report: MultiplePermissionsReport): Boolean {
        return report.areAllPermissionsGranted()
    }

    companion object{
        const val PERMISSIONS_GRANTED = "All permissions have been granted"
        const val PERMISSION_RATIONALE= "Access to the storage is needed to save the picture"
        const val PERMISSION_DENIED = "Permission denied"
        const val NOT_LOGGED = " You need to log in to acces the data!"
        const val SAVING_ERROR = "URL can not be empty!"
    }

}