package com.recipemaster.view

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.recipemaster.R
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.model.pojo.Recipe
import com.recipemaster.model.repository.GetRecipeClient
import com.recipemaster.model.storage.RecipeDetailsService
import com.recipemaster.presenter.RecipeDetailsPresenter
import com.recipemaster.util.ToastMaker
import com.recipemaster.util.viewDataProcess.TextFormater
import kotlinx.android.synthetic.main.activity_details.*

class RecipeDetailsActivity : AppCompatActivity(), PermissionListener, RecipeDetailsContract.View {
    private  var presenter : RecipeDetailsContract.Presenter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        context = applicationContext

        presenter = RecipeDetailsPresenter(this, GetRecipeClient(), RecipeDetailsService())

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
        //paskudne url w view...
        _url = url
        requestPermissions()
    }

    override fun showConfirmDialog() {
        ConfirmDialog(this, _url, presenter)
    }

    override fun requestPermissions() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(this)
            .check()
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        showConfirmDialog()
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        ToastMaker.showToast(PERMISION_RATIONALE)
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        ToastMaker.showToast(DENIED)
    }

    companion object{
        private var context: Context? = null
        private var _url : String=""
        fun getContext(): Context? {
            return context
        }
        const val PERMISION_RATIONALE="Access to the storage is needed to save the picture"
        const val DENIED = "Permission denied"
    }


}
