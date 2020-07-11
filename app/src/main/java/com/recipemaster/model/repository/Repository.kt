package com.recipemaster.model.repository

import org.json.JSONObject

object Repository : IRepository {
    private var isConnected : Boolean = false
    private var json : JSONObject? = null
    private var givenStoragePermissions : Boolean = false
    private var userId : String?=""
    private var userName: String?= ""
    private var userPhotoUrl : String? = ""

    override fun setUserData(_json: JSONObject?) {
        com.recipemaster.model.repository.Repository.json = _json
    }

    override fun getUserData(): JSONObject? {
        return com.recipemaster.model.repository.Repository.json
    }


    override fun isLoggedIn(): Boolean {
        return com.recipemaster.model.repository.Repository.json !=null
    }

    override fun setConnectionState(_isConnected: Boolean) {
        com.recipemaster.model.repository.Repository.isConnected = _isConnected
    }

    override fun isConnected(): Boolean {
        return com.recipemaster.model.repository.Repository.isConnected
    }

    override fun areStoragePermissions(): Boolean {
       return com.recipemaster.model.repository.Repository.givenStoragePermissions
    }

    override fun setStoragePermissions(_storagePermissions: Boolean) {
        com.recipemaster.model.repository.Repository.givenStoragePermissions = _storagePermissions
    }

    override fun getCurrentUserId(): String? {
        return com.recipemaster.model.repository.Repository.userId
    }

    override fun setCurrentUserId(_userId: String?) {
        com.recipemaster.model.repository.Repository.userId = _userId
    }

    override fun getCurrentUserName(): String? {
        return com.recipemaster.model.repository.Repository.userName
    }

    override fun setCurrentUserName(_userName: String?) {
      com.recipemaster.model.repository.Repository.userName = _userName
    }

    override fun getCurrentUserPhotoUrl(): String? {
        return com.recipemaster.model.repository.Repository.userPhotoUrl
    }

    override fun setCurrentUserPhotoUrl(_userPhotoUrl: String?) {
        com.recipemaster.model.repository.Repository.userPhotoUrl = _userPhotoUrl
    }

}