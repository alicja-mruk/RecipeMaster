package com.recipemaster.util

import android.Manifest

object Permissions{

    val storagePermissions = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val facebookCallPermission =  listOf("public_profile")
}