package com.recipemaster.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.FacebookSdk
import com.recipemaster.R
import com.recipemaster.contract.HomeContract
import com.recipemaster.model.network.request.user.UserClient
import com.recipemaster.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity(), HomeContract.View {
    private var presenter: HomeContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFacebookSDK()
        setContentView(R.layout.activity_main)
        presenter = HomePresenter(this, UserClient())
        checkInternetConnection()
    }

    override fun initView() {
        setOnClickListeners()
    }

    override fun initFacebookSDK() {
        FacebookSdk.sdkInitialize(this)
        FacebookSdk.setApplicationId(getContext().resources?.getString(R.string.facebook_app_id))
    }

    override fun getContext(): Context {
        return this
    }

    override fun checkInternetConnection() {
        if (presenter?.isInternetConnection()!!) {
            setFacebookButtonToEnabled()
            setGetTheRecipeButtonToEnabled()
        } else {
            setFacebookButtonToDisabled()
            setGetTheRecipeButtonToNotEnabled()
        }

    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun setOnClickListeners() {
        get_recipe_btn.setOnClickListener {
            presenter?.openRecipeDetailsActivity()
        }

        login_facebook_btn.setOnClickListener {
            presenter?.tryLoginToFacebook()
        }

    }

    override fun onBackPressed() {
        checkInternetConnection()
    }


    override fun callOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    override fun setGetTheRecipeButtonToEnabled() {
        get_recipe_btn.setIcon(R.drawable.ic_get_recipe_connected)
        get_recipe_btn.setColorNormalResId(R.color.colorPrimary)
        get_recipe_btn.setColorPressedResId(R.color.colorPrimaryDark)
    }

    override fun setGetTheRecipeButtonToNotEnabled() {
        get_recipe_btn.setIcon(R.drawable.ic_get_recipe_notconnected)
        get_recipe_btn.setColorNormalResId(R.color.light_gray)
        get_recipe_btn.setColorPressedResId(R.color.dark_gray)
    }

    override fun setFacebookButtonToEnabled() {
        login_facebook_btn.setColorNormalResId(R.color.facebook_blue)
        login_facebook_btn.setColorPressedResId(R.color.facebook_blue_dark)
    }

    override fun setFacebookButtonToDisabled() {
        login_facebook_btn.setColorNormalResId(R.color.light_gray)
        login_facebook_btn.setColorPressedResId(R.color.dark_gray)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        presenter?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.dropView()
    }
}


