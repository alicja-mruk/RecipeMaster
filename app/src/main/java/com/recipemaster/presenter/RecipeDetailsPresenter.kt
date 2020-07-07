package com.recipemaster.presenter

import android.os.Bundle
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.appRepository.AppRepository
import com.recipemaster.model.repository.recipe.RecipeRepository
import com.recipemaster.model.storage.RecipeDetailsService
import com.recipemaster.util.viewDataProcess.TextFormater
import com.recipemaster.view.RecipeDetailsActivity


class RecipeDetailsPresenter(
    _view: RecipeDetailsContract.View?,
    _client: RecipeRepository,
    _storage_client : RecipeDetailsService,
    _app_repository : AppRepository
)
    :RecipeDetailsContract.Presenter{

    private var view: RecipeDetailsContract.View? = _view
    private val networkClient: RecipeRepository = _client
    private val storageClient : RecipeDetailsService = _storage_client
    private val appRepository: AppRepository = _app_repository
    private lateinit var userDataBundle : Bundle

    init {
        view?.initView()
    }

    override fun dropView() {
        view = null
    }

    override fun savePicture(url : String) {
        storageClient.savePictureIntoStorage(url)
    }

    override fun getRecipeData(){
        //todo check network connection
        networkClient.getRecipe(callback)
        callUpdateFooter()
    }

    override fun formatIngredients(ingredients : List<String>) : String {
        return TextFormater.processIngredients(ingredients)
    }

    override fun formatPreparing(preparing : List<String>): String {
        return TextFormater.processIngredients(preparing)
    }

    override fun callUpdateFooter(){
        //todo if data has been updated
       if(true){
           userDataBundle = appRepository.getUserDataBundle()
           view?.updateFooter(userDataBundle)
       }else{
           //todo else some problem with getting data
       }
    }

    override fun requestPermissions(permissions: List<String>) {
        Dexter.withContext(RecipeDetailsActivity.getContext())
            .withPermissions(
                permissions
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if(allPermissionsGranted(report)){
                      view?.showConfirmDialog()
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
        const val PERMISSION_RATIONALE= "Access to the storage is needed to save the picture"
        const val PERMISSION_DENIED = "Permission denied"
    }

}