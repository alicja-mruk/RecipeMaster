package com.recipemaster.model.repository.shared_preferences

interface SharedPreferencesManager {

    fun isLoggedIn(): Boolean
    fun setIsLoggedIn(state : Boolean)
    fun areStoragePermissions() : Boolean
    fun setStoragePermissions(_storagePermissions : Boolean)
    fun getCurrentUserId(): String?

    fun setCurrentUserId(_userId: String?)

    fun getCurrentUserName(): String?

    fun setCurrentUserName(_userName: String?)
    fun getCurrentUserPhotoUrl() : String?
    fun setCurrentUserPhotoUrl(_userPhotoUrl : String?)

}