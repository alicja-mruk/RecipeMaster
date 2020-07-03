package com.recipemaster.presenter

import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.RecipeRepository

class RecipeDetailsPresenter(
    _view: RecipeDetailsContract.View?,
    _client: RecipeRepository
)
    :RecipeDetailsContract.Presenter {

    private var view: RecipeDetailsContract.View? = _view
    private var networkClient: RecipeRepository = _client


    init {
        view?.initView()
    }

    override fun dropView() {
        view = null
    }

    override fun getRecipeData(){
        //todo check network connection
        networkClient.getRecipe(callback)
    }

    override fun getCurrentUserData() {
        TODO("Not yet implemented")
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