package com.recipemaster.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.recipemaster.R
import com.recipemaster.contract.HomeContract
import com.recipemaster.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() , HomeContract.View{
    private  var presenter : HomeContract.Presenter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = HomePresenter(this)

    }

    override fun initView() {
        Log.d("init", "init view")
        setOnClickListeners()
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
            presenter?.loginToFacebook()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.dropView()
    }
}
