package com.recipemaster.view

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.recipemaster.R
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.util.ToastMaker
import com.recipemaster.view.RecipeDetailsActivity.Companion.CLICKED_URL

class ConfirmDialog (
    private val context:Context,
    private val presenter:RecipeDetailsContract.Presenter?
){
    private val builder = AlertDialog.Builder(context)
    init{
        showDialog()
    }

    private fun showDialog(){

        builder.setTitle(context.getString(R.string.dialog_title))
        builder.setPositiveButton(YES){dialog, which ->
           ToastMaker.showToast(context.getString(R.string.picture_saved))
            saveMessageDialog(CLICKED_URL)
        }
        builder.setNegativeButton(NO){dialog,which ->
            ToastMaker.showToast(context.getString(R.string.picture_not_saved))
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun saveMessageDialog(url : String){
        presenter?.savePicture(url)
    }

    companion object{
        const val YES: String = "Yes"
        const val NO: String = "No"
    }

}