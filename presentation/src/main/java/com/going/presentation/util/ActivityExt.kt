package com.going.presentation.util

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.going.presentation.R
import com.going.presentation.designsystem.snackbar.customSnackBar
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.ui.extension.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Activity.downloadImage(number: Int) {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 &&
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 200)
    } else {
        val imageUrl = UserTendencyResultList[number].downloadImage
        val imageFileName = "img_doorip_${System.currentTimeMillis()}.jpeg"

        // 이미지 저장 폴더
        val uploadFolder =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        // 폴더가 없다면 생성
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs()
        }

        // Coil ImageLoader 인스턴스 생성
        val imageLoader = ImageLoader.Builder(this)
            .build()

        // Coil을 사용하여 이미지 다운로드 및 저장
        ProcessLifecycleOwner.get().lifecycleScope.launch(Dispatchers.IO) {
            val request = ImageRequest.Builder(this@downloadImage)
                .data(imageUrl)
                .build()

            val bitmap = (imageLoader.execute(request) as SuccessResult).drawable.toBitmap()

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
            }

            val contentResolver = contentResolver
            val uri =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let { imageUri ->
                contentResolver.openOutputStream(imageUri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            }
        }

        toast(getString(R.string.profile_image_download_success))
    }
}

fun ComponentActivity.initOnBackPressedListener(
    view: View,
    delay: Long = 2000L,
) {
    var backPressedTime = 0L
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() - backPressedTime >= delay) {
                backPressedTime = System.currentTimeMillis()
                customSnackBar(view, getString(R.string.toast_back_pressed))
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

inline fun <reified T : Activity> Activity.navigateToScreenClear() {
    Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(this)
    }
    finish()
}
