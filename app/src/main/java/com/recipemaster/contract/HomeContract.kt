package com.recipemaster.contract

import android.content.Context

interface HomeContract {
    interface View{
        fun initView()
        fun getContext() : Context
        fun showToast(message  : String)
        fun setOnClickListeners()
    }

    interface Presenter {
        fun dropView()
        fun openRecipeDetailsActivity()
        fun loginToFacebook()
    }

}