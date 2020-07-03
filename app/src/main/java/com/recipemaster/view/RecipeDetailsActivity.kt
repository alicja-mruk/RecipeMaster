package com.recipemaster.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.recipemaster.R
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.GetRecipeClient
import com.recipemaster.presenter.RecipeDetailsPresenter
import kotlinx.android.synthetic.main.activity_details.*

class RecipeDetailsActivity : AppCompatActivity(), RecipeDetailsContract.View {
    private  var presenter : RecipeDetailsContract.Presenter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        presenter = RecipeDetailsPresenter(this, GetRecipeClient())

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.dropView()
    }

    override fun initView() {
        Log.d("Details activity", "init details ")
        presenter?.getRecipeData()
    }

    override fun updateView(recipe: Recipe?) {
        displayRecipe(recipe)
    }

    override fun displayRecipe(recipe:Recipe?) {

        recipe_title.text = recipe?.title
        recipe_description.text = recipe?.description

        //todo parse array like its shown at the example -> get array and add space between/ or numbers
        recipe_ingredients.text = recipe?.ingredients.toString()
        recipe_preparing.text = recipe?.preparing.toString()

        Glide.with(this)
            .load(recipe?.photos?.get(0))
            .placeholder(R.drawable.placeholder)
            .into(recipe_image0)

        Glide.with(this)
            .load(recipe?.photos?.get(1))
            .placeholder(R.drawable.placeholder)
            .into(recipe_image1)
    }

    override fun showLoadingError(errorMessage: String?) {
        if (errorMessage != null) {
            showToast(errorMessage)
        }
    }

    override fun getContext(): Context {
        return this
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
