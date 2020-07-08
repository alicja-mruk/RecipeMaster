package com.recipemaster.model.repository.shared_preferences

object SharedPreferencesManagerImpl :SharedPreferencesManager{
    private  var isLogged : Boolean = false
    private var givenStoragePermissions : Boolean = false
    private var userId : String?=""
    private var userName: String?= ""
    private var userPhotoUrl : String? = ""


    override fun isLoggedIn(): Boolean {
        return isLogged
    }

    override fun setIsLoggedIn(state: Boolean) {
        isLogged = state
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