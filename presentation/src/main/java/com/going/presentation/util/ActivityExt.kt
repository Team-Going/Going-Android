package com.going.presentation.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.going.presentation.R
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.ui.extension.toast
import java.io.File
import java.io.FileOutputStream

fun Activity.downloadImage(number: Int) {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 &&
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 200)
    } else {
        val imageBitmap: Bitmap = BitmapFactory.decodeResource(
            resources,
            UserTendencyResultList[number].downloadImage,
        )
        val imageFileName = "img_doorip" + System.currentTimeMillis() + ".jpeg"

        val uploadFolder =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        if (uploadFolder?.exists() == false) {
            uploadFolder.mkdirs()
        }

        val imageFile = File(uploadFolder, imageFileName)

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

fun Activity.openWebView(uri: String) =
    Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
        startActivity(this)
    }

inline fun <reified T : Activity> Activity.navigateToScreen(
    flags: List<Int>? = null,
    isFinish: Boolean = true,
) {
    Intent(this, T::class.java).apply {
        flags?.map {
            addFlags(it)
        }
        startActivity(this)
    }
    if (isFinish) {
        finish()
    }
}
