package com.recipemaster.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
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
        home_layout.setOnTouchListener { v, event ->
            v?.performClick()

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    fab_menu_btn.collapse()
                    unfadeHomeScreen()
                }
            }

            v?.onTouchEvent(event) ?: true
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (!fab_menu_btn.isExpanded) {
                fadeHomeScreen()
            } else {
                unfadeHomeScreen()
            }
        }

        return super.dispatchTouchEvent(event)
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

    override fun fadeHomeScreen() {
        val homeColor: Int = home_layout.solidColor
        home_layout.setBackgroundColor(
            Color.argb(
                128, Color.red(homeColor),
                Color.green(homeColor), Color.blue(homeColor)
            )
        )
        main_image.setImageResource(R.drawable.home_picture_faded)
    }

    override fun unfadeHomeScreen() {
        home_layout.setBackgroundColor(Color.WHITE)
        main_image.setImageResource(R.drawable.home_picture)
    }

}


