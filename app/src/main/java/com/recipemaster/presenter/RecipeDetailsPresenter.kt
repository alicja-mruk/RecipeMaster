package com.recipemaster.presenter

import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.RecipeRepository
import com.recipemaster.model.storage.RecipeDetailsService
import com.recipemaster.util.ToastMaker
import com.recipemaster.util.viewDataProcess.TextFormater
import com.recipemaster.view.RecipeDetailsActivity


class RecipeDetailsPresenter(
    _view: RecipeDetailsContract.View?,
    _client: RecipeRepository,
    _storage_client : RecipeDetailsService
)
    :RecipeDetailsContract.Presenter{

    private var view: RecipeDetailsContract.View? = _view
    private var networkClient: RecipeRepository = _client
    private var storageClient : RecipeDetailsService = _storage_client

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
    }

    override fun formatIngredients(ingredients : List<String>) : String {
        return TextFormater.processIngredients(ingredients)
    }

    override fun formatPreparing(preparing : List<String>): String {
        return TextFormater.processIngredients(preparing)
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
            view?.updateView(recipe)git
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