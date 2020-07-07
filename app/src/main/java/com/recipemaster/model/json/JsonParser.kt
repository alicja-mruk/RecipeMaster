package com.recipemaster.model.json

import org.json.JSONObject

class JsonParser (private val jsonObject: JSONObject?){

    fun parseName() : String? {
       return jsonObject?.getString("name")
    }

    fun parseUserId():String?{
       return jsonObject?.getString("id")
    }

    fun createProfilePictureUrl() : String?{
        return "$FACEBOOK_URL${parseUserId()}/picture?type=large&height=50&width=50"
    }

    companion object{
        const val FACEBOOK_URL = "http://graph.facebook.com/"
    }
}