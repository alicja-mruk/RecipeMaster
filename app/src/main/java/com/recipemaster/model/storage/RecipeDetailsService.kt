package com.recipemaster.model.storage

import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.os.Message
import android.widget.Toast
import com.recipemaster.contract.RecipeDetailsContract
import com.recipemaster.view.RecipeDetailsActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.io.File


class RecipeDetailsService : RecipeDetailsContract.Model {
    private val applicationContext = RecipeDetailsActivity.getContext()

    override fun savePictureIntoStorage(url: String) {
        val directory = File(Environment.DIRECTORY_PICTURES)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val downloadManager: DownloadManager =
            applicationContext?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(url)

        val request = RequestUriClient(url, uri, directory).makeRequest()

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                var msg: String? = ""
                val lastMsg = ""
                val messageStatus = MessageStatus(url, directory, status)
                msg = messageStatus.showStatus()
                if (messageStatus.hasStatusChanged(lastMsg, msg)) {
                    Observable.just(true)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                        }
                }
                cursor.close()
            }
        }).start()
    }
}