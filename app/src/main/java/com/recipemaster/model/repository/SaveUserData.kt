package com.recipemaster.model.repository

object SaveUserData {
    fun saveUserFacebookData(
        userId: String?,
        userName: String?,
        profilePictureUrl: String?
    ) {
        com.recipemaster.model.repository.Repository.setCurrentUserId(
            userId
        )
        com.recipemaster.model.repository.Repository.setCurrentUserName(
            userName
        )
        com.recipemaster.model.repository.Repository.setCurrentUserPhotoUrl(
            profilePictureUrl
        )
    }
}