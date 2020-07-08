package com.recipemaster.util

import android.content.Context
import android.widget.Toast
import com.recipemaster.view.RecipeDetailsActivity

object ToastMaker{
    var context = RecipeDetailsActivity.getContext()
    fun showToast(message : String ){
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}