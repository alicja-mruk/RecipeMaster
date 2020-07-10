package com.recipemaster.model.repository.shared_preferences

import org.json.JSONObject

object SharedPreferencesManager :ISharedPreferencesManager{
    private var isConnected : Boolean = false
    private var json : JSONObject? = null
    private var givenStoragePermissions : Boolean = false
    private var userId : String?=""
    private var userName: String?= ""
    private var userPhotoUrl : String? = ""

    override fun setUserData(_json: JSONObject?) {
        json = _json
    }

    override fun getUserData(): JSONObject? {
        return json
    }


    override fun isLoggedIn(): Boolean {
        return json!=null
    }

    override fun setConnectionState(_isConnected: Boolean) {
        isConnected = _isConnected
    }

    override fun isConnected(): Boolean {
        return isConnected
    }

    override fun areStoragePermissions(): Boolean {
       return givenStoragePermissions
    }

    override fun setStoragePermissions(_storagePermissions: Boolean) {
        givenStoragePermissions = _storagePermissions
    }

    override fun getCurrentUserId(): String? {
        return userId
    }

    override fun setCurrentUserId(_userId: String?) {
        userId = _userId
    }

    override fun getCurrentUserName(): String? {
        return userName
    }

    override fun setCurrentUserName(_userName: String?) {
      userName = _userName
    }

    override fun getCurrentUserPhotoUrl(): String? {
        return userPhotoUrl
    }

    override fun setCurrentUserPhotoUrl(_userPhotoUrl: String?) {
        userPhotoUrl = _userPhotoUrl
    }

}