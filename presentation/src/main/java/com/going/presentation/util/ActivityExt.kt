package com.going.presentation.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import com.going.presentation.R
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.ui.extension.toast
import java.io.File
import java.io.FileOutputStream

fun Activity.downloadImage(number: Int) {
    val downloadImageName = "img_tendency_result%s.png"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
        AlertDialog.Builder(this)
            .setTitle(R.string.notice)
            .setMessage(R.string.profile_image_permission_error)
            .setCancelable(false)
            .setPositiveButton(
                R.string.okay,
            ) { _, _ ->
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                this.startActivity(intent)
            }
            .setNegativeButton(
                R.string.setting_logout_negative,
            ) { _, _ -> }
            .create()
            .show()
    } else {
        val imageBitmap: Bitmap = BitmapFactory.decodeResource(
            resources,
            UserTendencyResultList[number].downloadImage,
        )
        val imageFileName =
            downloadImageName.replace("%s", number.toString())

        val uploadFolder =
            Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs()
        }

        val imageFile = File(uploadFolder.toString(), imageFileName)

        try {
            val outputStream = FileOutputStream(imageFile)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            MediaScannerConnection.scanFile(
                this,
                arrayOf(imageFile.absolutePath),
                arrayOf("image/jpeg"),
                null,
            )
            toast(getString(R.string.profile_image_download_success))
        } catch (e: Exception) {
            toast(getString(R.string.profile_image_download_error))
        }
    }
}

fun ComponentActivity.initOnBackPressedListener(
    delay: Long = 2000L,
) {
    var backPressedTime = 0L
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() - backPressedTime >= delay) {
                backPressedTime = System.currentTimeMillis()
                toast(getString(R.string.toast_back_pressed))
            } else {
                finish()
            }
        }
    }

    this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
}
