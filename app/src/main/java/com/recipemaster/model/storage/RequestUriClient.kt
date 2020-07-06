package com.recipemaster.model.storage

import android.app.DownloadManager
import android.net.Uri
import java.io.File

class RequestUriClient (private val url : String, private val uri: Uri, private val directory : File){
    fun makeRequest(): DownloadManager.Request {
        return DownloadManager.Request(uri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(url.substring(url.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    url.substring(url.lastIndexOf("/") + 1)
                )
        }
    }
}