package com.recipemaster.model.repository.user

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.recipemaster.contract.HomeContract
import org.json.JSONObject

class UserClient  : UserRepository {
    override fun requestUserData(onResponseCallback: HomeContract.OnResponseCallback) {
        val request = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken(),
            object : GraphRequest.GraphJSONObjectCallback {
                override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
//                    Log.d("fb", `object`.toString())
                    val json = response!!.jsonObject
                    onResponseCallback.onResponse(json)
                }
            })
        val parameters = Bundle()
        parameters.putString("fields", "id, name, picture")
        request.parameters = parameters
        request.executeAsync()
    }
}