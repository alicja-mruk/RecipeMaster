package com.recipemaster.view

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.recipemaster.R
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.util.ToastMaker
import com.recipemaster.view.RecipeDetailsActivity.Companion.CLICKED_URL


class ConfirmDialog (
    private val context:Context
){
    private val builder = AlertDialog.Builder(context)
    init{
        showDialog()
    }

    private fun showDialog(){

        builder.setTitle(context.getString(R.string.dialog_title))
        builder.setPositiveButton(YES){dialog, which ->
           ToastMaker.showToast(context.getString(R.string.picture_saved))
            dialog.dismiss()
        }
        builder.setNegativeButton(NO){dialog,which ->
            ToastMaker.showToast(context.getString(R.string.picture_not_saved))
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    companion object{
        const val YES: String = "Yes"
        const val NO: String = "No"
    }

}