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
import com.recipemaster.model.repository.user.UserClient
import com.recipemaster.presenter.RecipeDetailsPresenter.Companion.PERMISSION_DENIED
import com.recipemaster.view.RecipeDetailsActivity
import org.json.JSONObject


class HomePresenter(
    _view: HomeContract.View?,
    _client: UserClient
) : HomeContract.Presenter {

    private var view: HomeContract.View? = _view
    private val client: UserClient = _client

    private lateinit var callbackManager: CallbackManager
    private var permissionNeeds: List<String> =
        listOf("name", "public_profile")
    private lateinit var userDataBundle  : Bundle
    private var isJsonReceived = false

    init {
        view?.setOnClickListeners()
    }

    override fun dropView() {
        view = null
    }

    override fun openRecipeDetailsActivity() {
        Log.d("Details", "Clicking at details icon")
        val intent = Intent(view?.getContext(), RecipeDetailsActivity::class.java)
        view?.getContext()?.startActivity(intent)
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

        LoginManager.getInstance().logInWithReadPermissions(view as Activity, permissionNeeds)

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {

                    if (AccessToken.getCurrentAccessToken() != null) {
                        client.requestUserData(userDataCallback)
                    }
                }

                override fun onCancel() {
                    view?.showToast(LOGIN_CANCELED)
                }

                override fun onError(error: FacebookException?) {
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

        userDataBundle =  bundleOf(
            Pair("USER_ID", userId),
            Pair("USERNAME", userName),
            Pair("PROFILE_PICTURE", profilePictureUrl)
        )
        isJsonReceived = true
        //todo: pack the data into some bundle
    }

    companion object {
        const val PERMISSION_AUDIO_RATIONALE = "You need to give access to the audio"
        const val LOGIN_CANCELED = "Logging has been canceled"
        const val LOGIN_ERROR = "Error has occured"
    }


}