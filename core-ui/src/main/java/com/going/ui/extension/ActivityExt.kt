package com.going.ui.extension

import android.app.Activity
import androidx.core.content.ContextCompat

fun Activity.setStatusBarColorFromResource(colorResId: Int) {
    val statusBarColor = ContextCompat.getColor(this, colorResId)
    window.statusBarColor = statusBarColor
}