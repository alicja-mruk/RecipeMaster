package com.recipemaster.contract

import android.content.Context
import android.content.Intent
import com.recipemaster.model.pojo.Recipe
import org.json.JSONObject

interface HomeContract {
    interface View{
        fun initView()
        fun initFacebookSDK()
//        fun initProgressBar()
        fun getContext() : Context
        fun showToast(message  : String)
        fun setOnClickListeners()
        fun callOnActivityResult(requestCode : Int, resultCode:Int, data: Intent)
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
    }
    interface OnResponseCallback {
        fun onResponse(json:JSONObject?)
    }

    interface UserService{
        fun requestUserData()
    }

}