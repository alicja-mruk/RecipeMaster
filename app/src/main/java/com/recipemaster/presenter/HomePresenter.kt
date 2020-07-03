package com.recipemaster.presenter

import android.content.Intent
import android.util.Log
import com.recipemaster.contract.HomeContract
import com.recipemaster.view.RecipeDetailsActivity

class HomePresenter (
    _view: HomeContract.View?
//    _client : HomeRepository
) : HomeContract.Presenter{
    private var view: HomeContract.View? = _view
//    private var client: HomeRepository = _client

    init {
        view?.initView()
    }

    override fun dropView() {
        view = null
    }

    override fun openRecipeDetailsActivity() {
        Log.d("Details", "Clicking at details icon")
        val intent = Intent(view?.getContext(), RecipeDetailsActivity::class.java )
        view?.getContext()?.startActivity(intent)
    }

    override fun loginToFacebook() {
        Log.d("Facebook", "Clicking at facebook icon")
    }

}