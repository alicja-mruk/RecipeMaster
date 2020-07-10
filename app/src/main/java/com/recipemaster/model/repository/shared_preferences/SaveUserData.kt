package com.recipemaster.model.repository.shared_preferences

object SaveUserData {
    fun saveUserFacebookData(
        userId: String?,
        userName: String?,
        profilePictureUrl: String?
    ) {
        SharedPreferencesManager.setCurrentUserId(userId)
        SharedPreferencesManager.setCurrentUserName(userName)
        SharedPreferencesManager.setCurrentUserPhotoUrl(profilePictureUrl)
    }
}