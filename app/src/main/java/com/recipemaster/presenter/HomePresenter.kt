package com.recipemaster.presenter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import com.recipemaster.R
import com.recipemaster.contract.HomeContract
import com.recipemaster.model.json.ProcessJsonData
import com.recipemaster.model.network.request.user.UserClient
import com.recipemaster.model.repository.Repository
import com.recipemaster.util.MessageCallback
import com.recipemaster.util.Permissions
import com.recipemaster.view.RecipeDetailsActivity
import org.json.JSONObject


class HomePresenter(
    _view: HomeContract.View?
) : HomeContract.Presenter {

    private var view: HomeContract.View? = _view
    private val client = UserClient ()
    private lateinit var callbackManager: CallbackManager


    init {
        view?.initView()
    }

    override fun dropView() {
        view = null
    }

    override fun getView(): Context? {
        return view?.getContext()
    }

    override fun openRecipeDetailsActivity() {
        setFloatingMenuButtonsBasedOnConnection()
        view?.showProgressDialog()
        if (isConnected()) {
            if (isFacebookConnection()) {
                val intent = Intent(view?.getContext(), RecipeDetailsActivity::class.java)
                view?.getContext()?.startActivity(intent)
                view?.dismissProgressDialog()
            } else {
                view?.setGetTheRecipeButtonToDisabled()
                view?.showToast(MessageCallback.NOT_LOGGED)
            }
        }
        view?.dismissProgressDialog()
    }

    override fun setConnectionState(_isConnected: Boolean) {
       Repository.setConnectionState(_isConnected)
    }

    override fun isConnected(): Boolean {
        return Repository.isConnected()
    }

    override fun isAudioPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            view!!.getContext(),
            Manifest.permission.RECORD_AUDIO
        )
                == PackageManager.PERMISSION_GRANTED)
    }

    override fun setFloatingMenuButtonsBasedOnConnection() {
        if (isConnected()) {
            loggedToFacebookViewUpdate()
            if (isFacebookConnection()) {
                view?.setGetTheRecipeButtonToEnabled()
            } else {
                notLoggedToFacebookViewUpdate()
            }
        } else {
            noConnectionViewUpdate()
        }
    }

    override fun loggedToFacebookViewUpdate() {
        view?.setFacebookButtonToEnabled()
    }

    override fun notLoggedToFacebookViewUpdate() {
        view?.setGetTheRecipeButtonToDisabled()
    }

    override fun noConnectionViewUpdate() {
        view?.showToast(MessageCallback.NO_INTERNET_CONNECTION)
        view?.setFacebookButtonToDisabled()
        view?.setGetTheRecipeButtonToDisabled()
    }

    override fun isFacebookConnection(): Boolean {
        return Repository.isLoggedIn()
    }


    override fun tryLoginToFacebook() {
        if (isConnected()) {
            if (isAudioPermission()) {
                logIntoFacebook()
            } else {
                requestAudioPermissions()
            }
        } else {
            view?.showToast(MessageCallback.NO_INTERNET_CONNECTION)
        }
    }


    override fun requestAudioPermissions() {

        Dexter.withContext(view?.getContext())
            .withPermission(Manifest.permission.RECORD_AUDIO)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    logIntoFacebook()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    view?.showToast(MessageCallback.PERMISSION_DENIED)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    view?.showToast(MessageCallback.PERMISSION_AUDIO)

                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return
        }
    }

    override fun logIntoFacebook() {
        if (Repository.isLoggedIn()) {
            view?.showToast(MessageCallback.ALREADY_LOGGED)

        } else {

            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(
                view as Activity,
                Permissions.facebookCallPermission
            )

            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {

                    override fun onSuccess(loginResult: LoginResult?) {
                        onSuccessFacebookCallback()
                    }

                    override fun onCancel() {
                        onCanceledFacebookCallback()
                    }

                    override fun onError(error: FacebookException?) {
                        onErrorFacebookCallback()
                    }
                })
        }

    }

    override fun onSuccessFacebookCallback() {
        if (AccessToken.getCurrentAccessToken() == null) {
            view?.setGetTheRecipeButtonToDisabled()
            view?.showToast(MessageCallback.NO_DATA_RECEIVED)
        } else {
            client.requestUserData(userDataCallback)
            view?.setGetTheRecipeButtonToEnabled()
            view?.showToast(MessageCallback.LOGIN_SUCCESS)
        }
    }

    override fun onCanceledFacebookCallback() {
        if (AccessToken.getCurrentAccessToken() == null) {
            view?.setGetTheRecipeButtonToDisabled()
            view?.showToast(MessageCallback.LOGIN_CANCELED)
        } else {
            client.requestUserData(userDataCallback)
            view?.setGetTheRecipeButtonToEnabled()
            view?.showToast(MessageCallback.LOGIN_SUCCESS)
        }
    }

    override fun onErrorFacebookCallback() {
        view?.setGetTheRecipeButtonToDisabled()
        view?.showToast(MessageCallback.LOGIN_ERROR)
    }


    private var userDataCallback = object : HomeContract.OnResponseCallback {

        override fun onResponse(json: JSONObject?) {
            parseJsonResponse(json)
            Log.d("FB_RESPONSE: ", json.toString())
        }

        override fun onError(message: String) {
            view?.showToast(message)
        }
    }

    override fun parseJsonResponse(json: JSONObject?) {
        ProcessJsonData.parseUserFacebookData(json)
    }


}