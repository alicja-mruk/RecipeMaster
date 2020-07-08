package com.recipemaster.presenter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
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
import com.karumi.dexter.listener.single.PermissionListener
import com.recipemaster.contract.HomeContract
import com.recipemaster.model.json.JsonParser
import com.recipemaster.model.repository.shared_preferences.SharedPreferencesManager
import com.recipemaster.model.repository.shared_preferences.SharedPreferencesManagerImpl
import com.recipemaster.model.repository.user.UserClient
import com.recipemaster.presenter.RecipeDetailsPresenter.Companion.NOT_LOGGED
import com.recipemaster.presenter.RecipeDetailsPresenter.Companion.PERMISSION_DENIED
import com.recipemaster.view.RecipeDetailsActivity
import org.json.JSONObject


class HomePresenter(
    _view: HomeContract.View?,
    _client: UserClient,
    _shared_preferences : SharedPreferencesManager
) : HomeContract.Presenter {

    private var view: HomeContract.View? = _view
    private val client: UserClient = _client
    private val sharedPreferencesManager: SharedPreferencesManager = _shared_preferences

    private lateinit var callbackManager: CallbackManager
    private var permissionNeeds: List<String> =
        listOf("public_profile")


    init {
        view?.setOnClickListeners()
        view?.setGetTheRecipeButtonToNotEnabled()
    }

    override fun dropView() {
        view = null
    }

    override fun openRecipeDetailsActivity() {
        if(SharedPreferencesManagerImpl.isLoggedIn()){
            val intent = Intent(view?.getContext(), RecipeDetailsActivity::class.java)
            view?.getContext()?.startActivity(intent)
        }else{
            view?.showToast(NOT_LOGGED)
        }

    }

    override fun tryLoginToFacebook() {
        requestAudioPermissions()
    }

    override fun requestAudioPermissions() {
        Dexter.withContext(view?.getContext())
            .withPermission(Manifest.permission.RECORD_AUDIO)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    logIntoFacebook()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    view?.showToast(PERMISSION_DENIED)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    view?.showToast(PERMISSION_AUDIO_RATIONALE)
                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if(callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return
        }
    }

    override fun logIntoFacebook() {
        callbackManager = CallbackManager.Factory.create()
        val loginManager = LoginManager.getInstance()


        loginManager.logInWithReadPermissions(view as Activity, permissionNeeds)
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    if (AccessToken.getCurrentAccessToken() == null) {
                        sharedPreferencesManager.setIsLoggedIn(false)
                        view?.setGetTheRecipeButtonToNotEnabled()
                        view?.showToast(NO_DATA_RECEIVED)
                    }else{
                        client.requestUserData(userDataCallback)
                        sharedPreferencesManager.setIsLoggedIn(true)
                        view?.setGetTheRecipeButtonToEnabled()
                        view?.showToast(LOGIN_SUCCEED)
                    }
                }

                override fun onCancel() {
                    if (AccessToken.getCurrentAccessToken() == null) {
                        sharedPreferencesManager.setIsLoggedIn(false)
                        view?.setGetTheRecipeButtonToNotEnabled()
                        view?.showToast(LOGIN_CANCELED)
                    }
                    else{
                        client.requestUserData(userDataCallback)
                        sharedPreferencesManager.setIsLoggedIn(true)
                        view?.setGetTheRecipeButtonToEnabled()
                        view?.showToast(LOGIN_CANCELED)
                    }
                }

                override fun onError(error: FacebookException?) {
                    view?.setGetTheRecipeButtonToNotEnabled()
                    sharedPreferencesManager.setIsLoggedIn(false)
                    view?.showToast(LOGIN_ERROR)
                }
            })
    }

    private var userDataCallback = object : HomeContract.OnResponseCallback {
        override fun onResponse(json: JSONObject?) {
            Log.d("RESPONSE: ", json.toString())
            parseJsonResponse(json)
        }

    }

    override fun parseJsonResponse(json: JSONObject?){
        val jsonParser = JsonParser(json)
        val userName = jsonParser.parseName()
        val userId = jsonParser.parseUserId()
        val profilePictureUrl = jsonParser.createProfilePictureUrl()

        sharedPreferencesManager.setCurrentUserId(userId)
        sharedPreferencesManager.setCurrentUserName(userName)
        sharedPreferencesManager.setCurrentUserPhotoUrl(profilePictureUrl)
    }

    companion object {
        const val PERMISSION_AUDIO_RATIONALE = "You need to give access to the audio"
        const val LOGIN_CANCELED = "Logging has been canceled"
        const val LOGIN_ERROR = "Error has occured"
        const val LOGIN_SUCCEED="Successfully logged in"
        const val NO_DATA_RECEIVED="No data received"
    }


}