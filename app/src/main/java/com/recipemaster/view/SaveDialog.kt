package com.recipemaster.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.recipemaster.R
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.util.ToastMaker
import java.lang.ClassCastException


class SaveDialog : AppCompatDialogFragment(){
    private lateinit var confirmDialogListener: SaveDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle(activity?.getString(R.string.dialog_title))
        builder.setPositiveButton(YES){dialog, which ->
//            presenter?.callModelOnSavePicture()
            confirmDialogListener.onYesClicked()
            dialog.dismiss()
        }
        builder.setNegativeButton(NO){dialog,which ->
            confirmDialogListener.onNoClicked()
            dialog.dismiss()
        }
        return builder.create()

    }

    interface SaveDialogListener{
        fun onYesClicked()
        fun onNoClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            confirmDialogListener = context as SaveDialogListener
        }
        catch(e : ClassCastException){
            throw ClassCastException("$context must implement SaveDialogListener")
        }
    }

    companion object{
        const val YES: String = "Yes"
        const val NO: String = "No"
    }

}