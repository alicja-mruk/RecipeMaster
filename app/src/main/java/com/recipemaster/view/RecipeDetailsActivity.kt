package com.recipemaster.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.recipemaster.R
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.GetRecipeClient
import com.recipemaster.presenter.RecipeDetailsPresenter
import com.recipemaster.util.viewDataProcess.TextBeautify
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
        presenter?.getRecipeData()
    }

    override fun updateView(recipe: Recipe?) {
        displayRecipe(recipe)
    }

    override fun displayRecipe(recipe:Recipe?) {
        if(recipe == null) return
            displayTextFields(recipe)
            displayPhotos(recipe.photos)
    }

    override fun displayTextFields(recipe: Recipe?) {
        if (recipe != null) {
            recipe_title.text = recipe.title
            recipe_description.text = recipe.description
            recipe_ingredients.text = TextBeautify.processIngredients(recipe.ingredients)
            recipe_preparing.text = TextBeautify.processPreparing(recipe.preparing)
        }
    }

    override fun displayPhotos(photos: List<String>) {
        Glide.with(this)
            .load(photos[0])
            .placeholder(R.drawable.placeholder)
            .into(recipe_image0)

        Glide.with(this)
            .load(photos[1])
            .placeholder(R.drawable.placeholder)
            .into(recipe_image1)

        Glide.with(this)
            .load(photos[2])
            .placeholder(R.drawable.placeholder)
            .into(recipe_image2)
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
