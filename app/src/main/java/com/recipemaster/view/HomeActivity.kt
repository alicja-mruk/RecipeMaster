package com.recipemaster.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.recipemaster.R
import com.recipemaster.contract.HomeContract
import com.recipemaster.model.repository.user.UserClient
import com.recipemaster.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_main.*



class HomeActivity : AppCompatActivity() , HomeContract.View{
    private  var presenter : HomeContract.Presenter ? = null
//    private lateinit var progressbar : Dialog
    private lateinit var callbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFacebookSDK()
        setContentView(R.layout.activity_main)
        presenter = HomePresenter(this, UserClient())

    }

    override fun initView() {
        setOnClickListeners()
    }

    override fun initFacebookSDK() {
        FacebookSdk.sdkInitialize(getContext())
        FacebookSdk.setApplicationId(getContext().resources?.getString(R.string.facebook_app_id))
    }

    override fun getContext(): Context {
        return this
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

    override fun callOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
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


