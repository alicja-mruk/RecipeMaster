package com.recipemaster.model.json

import com.recipemaster.model.repository.shared_preferences.SaveUserData
import org.json.JSONObject

object ProcessJsonData {
    fun parseUserFacebookData(json: JSONObject?){
        val jsonParser = JsonParser(json)
        val userName = jsonParser.parseName()
        val userId = jsonParser.parseUserId()
        val profilePictureUrl = jsonParser.createProfilePictureUrl()
       saveUserDataIntoSharedPreferences(userId, userName, profilePictureUrl)
    }
    private fun saveUserDataIntoSharedPreferences(userId : String?, userName : String?, profilePictureUrl : String?){
        SaveUserData.saveUserFacebookData(userId, userName, profilePictureUrl)
    }
}