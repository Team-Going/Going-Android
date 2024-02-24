package com.going.presentation.designsystem.snackbar

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.going.presentation.R
import com.going.presentation.databinding.LayoutCustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar

class CustomSnackBar(view: View, private val message: String) {

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", DURATION)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: LayoutCustomSnackbarBinding =
        DataBindingUtil.inflate(inflater, R.layout.layout_custom_snackbar, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.tvSnackbar.text = message
    }

    fun show() {
        snackbar.show()
    }

    companion object {
        private const val DURATION = 1500

        @JvmStatic
        fun make(view: View, message: String) = CustomSnackBar(view, message)
    }
}