package com.recipemaster.contract

import android.content.Context
import android.content.Intent
import com.facebook.FacebookException
import com.facebook.login.LoginResult
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
        fun setGetTheRecipeButtonToDisabled()
        fun setFacebookButtonToEnabled()
        fun setFacebookButtonToDisabled()
        fun createProgressDialog()
        fun showProgressDialog()
        fun dismissProgressDialog()

    }

    interface Presenter {
        fun dropView()
        fun getView() : Context?
        fun openRecipeDetailsActivity()
        fun setConnectionState(_isConnected : Boolean)
        fun isConnected () : Boolean
        fun setFloatingMenuButtonsBasedOnConnection()
        fun loggedToFacebookViewUpdate()
        fun notLoggedToFacebookViewUpdate()
        fun noConnectionViewUpdate()
        fun isFacebookConnection() : Boolean
        fun tryLoginToFacebook()
        fun requestAudioPermissions()
        fun logIntoFacebook()
        fun onSuccessFacebookCallback()
        fun onCanceledFacebookCallback()
        fun onErrorFacebookCallback()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun parseJsonResponse(json: JSONObject?)
    }

    interface OnResponseCallback {
        fun onResponse(json: JSONObject?)
        fun onError(message: String)
    }

}