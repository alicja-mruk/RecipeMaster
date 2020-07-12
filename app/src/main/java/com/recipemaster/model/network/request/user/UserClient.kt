package com.recipemaster.model.network.request.user

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.recipemaster.contract.HomeContract
import com.recipemaster.model.repository.Repository
import org.json.JSONObject

class UserClient : IUserClient {
    override fun requestUserData(onResponseCallback: HomeContract.OnResponseCallback) {
        if (!Repository.isLoggedIn()) {
            val request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                object : GraphRequest.GraphJSONObjectCallback {
                    override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                        val json = response!!.jsonObject
                        Repository.setUserData(json)
                        onResponseCallback.onResponse(json)
                    }
                })
            val parameters = Bundle()
            parameters.putString("fields", "id, name, picture")
            request.parameters = parameters
            request.executeAsync()
        }
        else{
            onResponseCallback.onResponse(Repository.getUserData())
        }
    }
}