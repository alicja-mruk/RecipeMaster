package com.recipemaster.model.repository.shared_preferences

interface SharedPreferencesManager {

    fun isLoggedIn(): Boolean
    fun setIsLoggedIn(state : Boolean)
    fun getCurrentUserId(): String?

    fun setCurrentUserId(_userId: String?)

    fun getCurrentUserName(): String?

    fun setCurrentUserName(_userName: String?)
    fun getCurrentUserPhotoUrl() : String?
    fun setCurrentUserPhotoUrl(_userPhotoUrl : String?)

}