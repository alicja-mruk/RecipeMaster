package com.recipemaster.presenter

import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.network.request.recipe.IRecipeClient
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.shared_preferences.SharedPreferencesManager
import com.recipemaster.model.storage.RecipeDetailsService
import com.recipemaster.util.MessageCallback
import com.recipemaster.util.Permissions
import com.recipemaster.util.processData.TextFormater
import com.recipemaster.view.RecipeDetailsActivity


class RecipeDetailsPresenter(
    _view: RecipeDetailsContract.View?,
    _clientI: IRecipeClient,
    _storage_client: RecipeDetailsService
) : RecipeDetailsContract.Presenter {

    private var view: RecipeDetailsContract.View? = _view
    private val networkClientI: IRecipeClient = _clientI
    private val storageClient: RecipeDetailsService = _storage_client


    init {
        view?.initView()
    }

    override fun dropView() {
        view = null
    }

    override fun savePicture(url: String) {
        if (!SharedPreferencesManager.areStoragePermissions()) {
            requestPermissions()
        }
        if (SharedPreferencesManager.areStoragePermissions()) {
            try {
                view?.showConfirmDialog()
            } catch (e: Exception) {
                e.printStackTrace()
                view?.showToast(MessageCallback.SAVING_ERROR)
            }
        } else {
            view?.showToast(MessageCallback.PERMISSION_DENIED)
        }
    }

    override fun getRecipeData() {
        //todo check network connection
        networkClientI.getRecipe(callback)
        callUpdateFooterView()
    }


    override fun formatIngredients(ingredients: List<String>): String {
        return TextFormater.processIngredients(ingredients)
    }

    override fun formatPreparing(preparing: List<String>): String {
        return TextFormater.processPreparing(preparing)
    }

    override fun callUpdateFooterView() {
        val name = SharedPreferencesManager.getCurrentUserName()
        val userPhotoUrl = SharedPreferencesManager.getCurrentUserPhotoUrl()
        view?.updateUserName(name)
        view?.updateUserProfilePicture(userPhotoUrl)
    }

    override fun requestPermissions() {
        if (!SharedPreferencesManager.areStoragePermissions()) {
            Dexter.withContext(RecipeDetailsActivity.getContext())
                .withPermissions(Permissions.storagePermissions)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (allPermissionsGranted(report)) {
                            SharedPreferencesManager.setStoragePermissions(true)
                        } else {
                            view?.showToast(
                                MessageCallback.PERMISSION_DENIED
                            )
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {

                        view?.showToast(
                            MessageCallback.PERMISSION_RATIONALE
                        )

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
}