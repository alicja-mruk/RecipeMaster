package com.recipemaster.presenter

import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.RecipeRepository
import com.recipemaster.model.storage.RecipeDetailsService
import com.recipemaster.util.viewDataProcess.TextFormater


class RecipeDetailsPresenter(
    _view: RecipeDetailsContract.View?,
    _client: RecipeRepository,
    _storage_client : RecipeDetailsService
)
    :RecipeDetailsContract.Presenter {

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

    private var callback = object : RecipeDetailsContract.OnResponseCallback {
        override fun onResponse(recipe: Recipe) {
            view?.updateView(recipe)

        }

        override fun onError(errorMessage: String?) {
            view?.showLoadingError(errorMessage)
        }
    }

}