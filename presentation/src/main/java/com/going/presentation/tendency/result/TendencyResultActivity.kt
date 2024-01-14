package com.going.presentation.tendency.result

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityTendencyResultBinding
import com.going.presentation.tendency.ttest.TendencyTestActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class TendencyResultActivity :
    BaseActivity<ActivityTendencyResultBinding>(R.layout.activity_tendency_result) {
    private val viewModel by viewModels<TendencyResultViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getuserInfo()
        observeUserInfoState()
        initRestartBtnClickListener()
        initSaveImgBtnClickListener()
        initFinishBtnClickListener()
    }

    private fun getuserInfo() {
        viewModel.getUserInfoState()
    }

    private fun observeUserInfoState() {
        viewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach
                is UiState.Success -> bindTendencyInfo(state.data.result)
                is UiState.Failure -> toast(state.msg)
                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun bindTendencyInfo(number: Int) {
        with(binding) {
            tvTendencyTestResultTitle.text = getString(R.string.tendency_test_result_title, "찐두릅")

            viewModel.mockTendencyResult[number].apply {
                imgTendencyTestResult.setImageResource(resultImage)
                tvTendencyTestResultType.text = profileTitle
                tvTendencyTestResultSubType.text = profileSubTitle

                tvTendencyTestResultTag1.text = getString(R.string.tag, tags[0])
                tvTendencyTestResultTag2.text = getString(R.string.tag, tags[1])
                tvTendencyTestResultTag3.text = getString(R.string.tag, tags[2])

                tvFirstDescriptionTitle.text = profileBoxInfo[0].title
                tvFirstDescriptionFirstText.text =
                    setBulletPoint(profileBoxInfo[0].first)
                tvFirstDescriptionSecondText.text =
                    setBulletPoint(profileBoxInfo[0].second)
                tvFirstDescriptionThirdText.text =
                    setBulletPoint(profileBoxInfo[0].third)

                tvSecondDescriptionTitle.text =
                    profileBoxInfo[1].title
                tvSecondDescriptionFirstText.text =
                    setBulletPoint(profileBoxInfo[1].first)
                tvSecondDescriptionSecondText.text =
                    setBulletPoint(profileBoxInfo[1].second)
                tvSecondDescriptionThirdText.text =
                    setBulletPoint(profileBoxInfo[1].third)

                tvThirdDescriptionTitle.text = profileBoxInfo[2].title
                tvThirdDescriptionFirstText.text =
                    setBulletPoint(profileBoxInfo[2].first)
                tvThirdDescriptionSecondText.text =
                    setBulletPoint(profileBoxInfo[2].second)
                tvThirdDescriptionThirdText.text =
                    setBulletPoint(profileBoxInfo[2].third)
            }
        }
    }

    private fun setBulletPoint(text: String): SpannableString {
        val string = SpannableString(text)
        string.setSpan(BulletSpan(10), 0, text.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return string
    }

    private fun initRestartBtnClickListener() {
        binding.btnTendencyTestRestart.setOnSingleClickListener {
            navigateToTendencyTestScreen()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnTendencyResultDownload.setOnSingleClickListener {
            startImageDownload()
        }
    }

    private fun initFinishBtnClickListener() {
        binding.btnTendencyResultFinish.setOnSingleClickListener {
            navigateToDashBoardScreen()
        }
    }

    private fun navigateToTendencyTestScreen() {
        Intent(this, TendencyTestActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun navigateToDashBoardScreen() {
        Intent(this, DashBoardActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun startImageDownload() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE,
            )
        } else {
            saveImageToGallery(resources)
        }
    }

    private fun saveImageToGallery(resources: Resources) {
        val imageBitmap: Bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.img_tendency_result_ari,
        )
        val imageFileName = DOWNLOAD_IMAGE_NAME
        val path = DOWNLOAD_PATH

        val uploadFolder = Environment.getExternalStoragePublicDirectory(path)
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs()
        }

        val imageFile = File(uploadFolder, imageFileName)

        val outputStream = FileOutputStream(imageFile)
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        scanFile(imageFile, "image/jpeg")

        toast(getString(R.string.profile_image_download_success))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startImageDownload()
            } else {
                toast(getString(R.string.profile_image_download_error))
            }
        }
    }

    private fun scanFile(file: File, mimeType: String) {
        MediaScannerConnection.scanFile(
            this,
            arrayOf(file.absolutePath),
            arrayOf(mimeType),
            null,
        )
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 200
        const val DOWNLOAD_PATH = "/Download/"
        const val DOWNLOAD_IMAGE_NAME = "img_tendency_result.png"
    }
}
