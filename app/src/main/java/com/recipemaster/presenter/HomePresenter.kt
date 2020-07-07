package com.recipemaster.presenter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
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
import com.recipemaster.model.repository.GetUserClient
import com.recipemaster.presenter.RecipeDetailsPresenter.Companion.PERMISSION_DENIED
import com.recipemaster.view.RecipeDetailsActivity
import org.json.JSONObject


class HomePresenter(
    _view: HomeContract.View?,
    _client: GetUserClient
) : HomeContract.Presenter {

    private var view: HomeContract.View? = _view
    private var client: GetUserClient = _client

    private lateinit var callbackManager: CallbackManager
    private var permissionNeeds: List<String> =
        listOf("name", "public_profile")

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
                    Toast.makeText(view?.getContext(), PERMISSION_DENIED, Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    Toast.makeText(
                        view?.getContext(),
                        PERMISSION_AUDIO_RATIONALE,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
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
                    Log.d("FB", "canceled")
                    //some toast
                }

                override fun onError(error: FacebookException?) {
                    Log.d("FB", "error")
                    //some toast
                }
            })
    }

    private var userDataCallback = object : HomeContract.OnResponseCallback {
        override fun onResponse(json: JSONObject?) {
            //view.update view with the given data ..
            //todo update the view with the given data
            //parse data json
            Log.d("RESPONSE: ", "json.toString()")
        }

    }


    companion object {
        const val PERMISSION_AUDIO_RATIONALE = "You need to give access to the audio"
    }


}