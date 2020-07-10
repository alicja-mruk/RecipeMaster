package com.recipemaster.model.repository.shared_preferences

object SaveData {
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