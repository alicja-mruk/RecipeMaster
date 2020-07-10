package com.recipemaster.model.network.request.user

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.recipemaster.contract.HomeContract
import com.recipemaster.model.repository.shared_preferences.SharedPreferencesManager
import org.json.JSONObject

class UserClient : IUserClient {
    override fun requestUserData(onResponseCallback: HomeContract.OnResponseCallback) {
        if (!SharedPreferencesManager.isLoggedIn()) {
            val request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                object : GraphRequest.GraphJSONObjectCallback {
                    override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                        val json = response!!.jsonObject
                        SharedPreferencesManager.setUserData(json)
                        onResponseCallback.onResponse(json)
                    }
                })
            val parameters = Bundle()
            parameters.putString("fields", "id, name, picture")
            request.parameters = parameters
            request.executeAsync()
        }
        else{
            onResponseCallback.onResponse(SharedPreferencesManager.getUserData())
        }
    }
}