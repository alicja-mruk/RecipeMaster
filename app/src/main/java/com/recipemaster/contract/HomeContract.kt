package com.recipemaster.contract

import android.content.Context
import android.content.Intent
import org.json.JSONObject

interface HomeContract {
    interface View {
        fun initView()
        fun initFacebookSDK()
        fun getContext(): Context
        fun showToast(message: String)
        fun setOnClickListeners()
        fun callOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
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
        fun onSuccessFacebookCallback()
        fun onCanceledFacebookCallback()
        fun onErrorFacebookCallback()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun parseJsonResponse(json: JSONObject?)
        fun saveDataIntoSharedPreferences(userId : String?, userName:String?, profilePictureUrl: String?)
    }

    interface OnResponseCallback {
        fun onResponse(json: JSONObject?)
    }

}