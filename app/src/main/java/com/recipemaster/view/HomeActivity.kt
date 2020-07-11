package com.recipemaster.view

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.facebook.FacebookSdk
import com.recipemaster.R
import com.recipemaster.contract.HomeContract
import com.recipemaster.model.network.receiver.ConnectivityReceiver
import com.recipemaster.model.network.request.user.UserClient
import com.recipemaster.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity(), HomeContract.View,
    ConnectivityReceiver.ConnectivityReceiverListener{
    private var presenter: HomeContract.Presenter? = null
    private lateinit var progressBarDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFacebookSDK()
        setContentView(R.layout.activity_main)
        presenter = HomePresenter(this, UserClient())

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun initView() {
        setOnClickListeners()
        createProgressDialog()
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

    }

    override fun onBackPressed() {
        finish()
    }

    override fun callOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    override fun setGetTheRecipeButtonToEnabled() {
        get_recipe_btn.setIcon(R.drawable.ic_get_recipe_connected)
        get_recipe_btn.setColorNormalResId(R.color.colorPrimary)
        get_recipe_btn.setColorPressedResId(R.color.colorPrimaryDark)
    }

    override fun setGetTheRecipeButtonToDisabled() {
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

    override fun createProgressDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        progressBarDialog = builder.create()
    }

    override fun showProgressDialog() {
        progressBarDialog.show()
    }

    override fun dismissProgressDialog() {

        progressBarDialog.dismiss()


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

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        presenter?.setConnectionState(isConnected)
        presenter?.setFloatingMenuButtonsBasedOnConnection()
    }

}


