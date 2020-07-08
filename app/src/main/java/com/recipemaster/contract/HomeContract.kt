package com.recipemaster.contract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.recipemaster.model.pojo.Recipe
import org.json.JSONObject

interface HomeContract {
    interface View{
        fun initView()
        fun initFacebookSDK()
        fun getContext() : Context
        fun showToast(message  : String)
        fun setOnClickListeners()
        fun callOnActivityResult(requestCode : Int, resultCode:Int, data: Intent?)
        fun setGetTheRecipeButtonToEnabled()
        fun setGetTheRecipeButtonToNotEnabled()
//        fun showProgressDialog()
//        fun dismissProgressDialog()

    }

    interface Presenter {
        fun dropView()
        fun openRecipeDetailsActivity()
        fun tryLoginToFacebook()
        fun requestAudioPermissions()
        fun logIntoFacebook()
        fun onActivityResult(requestCode : Int, resultCode:Int, data: Intent?)
        fun parseJsonResponse(json: JSONObject?)

    }
    interface OnResponseCallback {
        fun onResponse(json:JSONObject?)
    }

}