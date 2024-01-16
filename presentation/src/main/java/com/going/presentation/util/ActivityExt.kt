package com.going.presentation.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.going.presentation.R
import com.going.presentation.tendency.result.TendencyResultActivity
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.ui.extension.toast
import java.io.File
import java.io.FileOutputStream

fun Activity.downloadImage(number: Int) {
    val downloadPath = "/Download/"
    val downloadImageName = "img_tendency_result%s.png"

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            TendencyResultActivity.PERMISSION_REQUEST_CODE,
        )
    } else {
        val imageBitmap: Bitmap = BitmapFactory.decodeResource(
            resources,
            UserTendencyResultList[number].downloadImage,
        )
        val imageFileName =
            downloadImageName.replace("%s", number.toString())

        val uploadFolder = Environment.getExternalStoragePublicDirectory(downloadPath)
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs()
        }

        val imageFile = File(uploadFolder, imageFileName)

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
