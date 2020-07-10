package com.recipemaster.model.repository.shared_preferences

import org.json.JSONObject

interface ISharedPreferencesManager {
    fun setUserData(_json : JSONObject?)
    fun getUserData() : JSONObject?
    fun isLoggedIn(): Boolean
    fun setConnectionState(_isConnected : Boolean)
    fun isConnected() : Boolean
    fun areStoragePermissions() : Boolean
    fun setStoragePermissions(_storagePermissions : Boolean)
    fun getCurrentUserId(): String?

    fun setCurrentUserId(_userId: String?)

    fun getCurrentUserName(): String?

    fun setCurrentUserName(_userName: String?)
    fun getCurrentUserPhotoUrl() : String?
    fun setCurrentUserPhotoUrl(_userPhotoUrl : String?)

}