package com.recipemaster.model.storage

import android.app.DownloadManager
import java.io.File

class MessageStatus(
    private val url: String,
    private val directory: File,
    private val status: Int
) {
    fun showStatus(): String? {
        return when (status) {
            DownloadManager.STATUS_FAILED ->        FAILED
            DownloadManager.STATUS_PAUSED ->        PAUSED
            DownloadManager.STATUS_PENDING ->       PENDING
            DownloadManager.STATUS_RUNNING ->       RUNNING
            DownloadManager.STATUS_SUCCESSFUL ->    SUCCESSFUL + " in $directory" + File.separator + url.substring(
                url.lastIndexOf("/") + 1
            )
            else -> NOTHING_TO_DOWNLOAD
        }
    }

    fun hasStatusChanged(newMessage:String, lastMessage : String?) : Boolean {
        return newMessage!=lastMessage
    }

    companion object {
        const val FAILED = "Download has been failed, please try again"
        const val PAUSED = "Paused"
        const val PENDING = "Pending"
        const val RUNNING = "Downloading..."
        const val SUCCESSFUL = "Image downloaded successfully"
        const val NOTHING_TO_DOWNLOAD = "There's nothing to download"

    }
}