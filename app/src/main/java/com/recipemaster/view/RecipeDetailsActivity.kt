package com.recipemaster.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.recipemaster.R
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.appRepository.AppRepositoryImpl
import com.recipemaster.model.repository.recipe.RecipeClient
import com.recipemaster.model.storage.RecipeDetailsService
import com.recipemaster.presenter.RecipeDetailsPresenter
import com.recipemaster.util.ToastMaker
import kotlinx.android.synthetic.main.activity_details.*


class RecipeDetailsActivity : AppCompatActivity(), RecipeDetailsContract.View {
    private  var presenter : RecipeDetailsContract.Presenter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        context = applicationContext

        presenter = RecipeDetailsPresenter(this,
            RecipeClient(), RecipeDetailsService(), AppRepositoryImpl())

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.dropView()
    }

    override fun initView() {
        presenter?.getRecipeData()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateView(recipe: Recipe?) {
        displayRecipe(recipe)
    }

    override fun displayRecipe(recipe:Recipe?) {
        if(recipe == null) return
            displayTextFields(recipe)
            displayPhotos(recipe.photos)
            onSavePhotosClickListeners(recipe.photos)
    }

    override fun displayTextFields(recipe: Recipe?) {
        if (recipe != null) {
            recipe_title.text = recipe.title
            recipe_description.text = recipe.description
            recipe_ingredients.text = presenter?.formatIngredients(recipe.ingredients)
            recipe_preparing.text = presenter?.formatPreparing(recipe.preparing)
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
            ToastMaker.showToast(errorMessage)
        }
    }

    override fun onSavePhotosClickListeners(photos:List<String>) {
        recipe_image0.setOnClickListener{callSavePicture(photos[0])}
        recipe_image1.setOnClickListener{callSavePicture(photos[1])}
        recipe_image2.setOnClickListener{callSavePicture(photos[2])}
        }

    override fun callSavePicture(url:String) {
        CLICKED_URL = url
        requestPermissions()
    }

    override fun showConfirmDialog() {
       ConfirmDialog( this, presenter)
    }

    override fun requestPermissions() {
        val permissions = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        presenter?.requestPermissions(permissions)
    }

    override fun updateFooter(userDataBundle: Bundle) {
        logged_as.text = userDataBundle.getString("USERNAME")
        Glide.with(this)
            .load(userDataBundle.getString("PROFILE_PICTURE"))
            .placeholder(R.drawable.placeholder)
            .into(profile_picture)
    }

    fun callOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object{
        private var context: Context? = null
        var CLICKED_URL : String=" "
        fun getContext(): Context? {
            return context
        }

    }

}
