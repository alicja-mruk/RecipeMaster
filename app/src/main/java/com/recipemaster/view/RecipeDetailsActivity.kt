package com.recipemaster.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.recipemaster.R
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.network.request.recipe.RecipeClient
import com.recipemaster.model.storage.RecipeDetailsService
import com.recipemaster.presenter.RecipeDetailsPresenter
import com.recipemaster.util.ToastMaker
import com.recipemaster.util.ToastMaker.context
import kotlinx.android.synthetic.main.activity_details.*


class RecipeDetailsActivity : AppCompatActivity(),
    RecipeDetailsContract.View {
    private var presenter: RecipeDetailsContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        context = applicationContext

        presenter = RecipeDetailsPresenter(
            this,
            RecipeClient(),
            RecipeDetailsService()
        )

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.dropView()
    }

    override fun initView() {
        presenter?.getRecipeData()
    }

    override fun getContext(): Context? {
        return this
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateView(recipe: Recipe?) {
        displayRecipe(recipe)
    }

    override fun displayRecipe(recipe: Recipe?) {
        if (recipe == null) return
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

    override fun onSavePhotosClickListeners(photos: List<String>) {
        recipe_image0.setOnClickListener {
            setClickedPictureUrl(photos[0])
        }
        recipe_image1.setOnClickListener {
            setClickedPictureUrl(photos[1])
        }
        recipe_image2.setOnClickListener {
            setClickedPictureUrl(photos[2])
        }
    }

    override fun setClickedPictureUrl(url: String) {
        CLICKED_URL = url
        presenter?.savePicture(url)
    }

    override fun getClickedPictureUrl() : String{
        return CLICKED_URL
    }

    override fun showConfirmDialog() {
        ConfirmDialog(this)
    }

    override fun updateUserName(userName: String?) {
        logged_as.text = this.getString(R.string.logged_as, userName)
    }

    override fun updateUserProfilePicture(profilePicture: String?) {
        runOnUiThread {
            Glide.with(this)
                .asBitmap()
                .load(profilePicture)
                .into(profile_picture)
        }
    }

    override fun updateFooter(userName: String?, photoUrl: String?) {
        updateUserName(userName)
        updateUserProfilePicture(photoUrl)
    }

    companion object {
        fun getContext(): Context? {
            return context
        }
        var CLICKED_URL = ""
    }

}
