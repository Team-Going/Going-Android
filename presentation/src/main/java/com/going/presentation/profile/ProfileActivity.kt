package com.going.presentation.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
import coil.load
import coil.transform.CircleCropTransformation
import com.going.presentation.R
import com.going.presentation.databinding.ActivityProfileBinding
import com.going.presentation.tendency.result.TendencyResultActivity.Companion.DOWNLOAD_IMAGE_NAME
import com.going.presentation.tendency.result.TendencyResultActivity.Companion.DOWNLOAD_PATH
import com.going.presentation.tendency.result.TendencyResultActivity.Companion.PERMISSION_REQUEST_CODE
import com.going.presentation.tendency.splash.TendencySplashActivity
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
class ProfileActivity :
    BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserInfo()
        observeUserInfoState()
        initRestartBtnClickListener()
        initBackBtnClickListener()
        initSaveImgBtnClickListener()
    }

    private fun getUserInfo() {
        profileViewModel.getUserInfoState()
    }

    private fun observeUserInfoState() {
        profileViewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach
                is UiState.Success -> bindProfileInfo(
                    state.data.name,
                    state.data.intro,
                    state.data.result
                )

                is UiState.Failure -> toast(state.msg)
                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun bindProfileInfo(name: String, intro: String, number: Int) {
        binding.run {
            tvProfileName.text = name
            tvProfileOneLine.text = intro

            val profileImage = when (number) {
                0 -> R.drawable.img_profile_6
                1 -> R.drawable.img_profile_1
                2 -> R.drawable.img_profile_2
                3 -> R.drawable.img_profile_4
                4 -> R.drawable.img_profile_8
                5 -> R.drawable.img_profile_5
                6 -> R.drawable.img_profile_7
                7 -> R.drawable.img_profile_3
                else -> R.drawable.img_profile_1
            }

            ivProfile.load(profileImage) {
                transformations(CircleCropTransformation())

                profileViewModel.mockProfileResult[number].apply {
                    tvProfileType.text = profileTitle
                    tvProfileSubType.text = profileSubTitle

                    tvProfileTag1.text = getString(R.string.tag, tags[0])
                    tvProfileTag2.text = getString(R.string.tag, tags[1])
                    tvProfileTag3.text = getString(R.string.tag, tags[2])

                    tvFirstDescriptionTitle.text = profileBoxInfo[0].title
                    tvFirstDescriptionFirstText.text =
                        setBulletPoint(profileBoxInfo[0].first)
                    tvFirstDescriptionSecondText.text =
                        setBulletPoint(profileBoxInfo[0].second)
                    tvFirstDescriptionThirdText.text = setBulletPoint(profileBoxInfo[0].third)

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
    }

    private fun setBulletPoint(text: String): SpannableString {
        val string = SpannableString(text)
        string.setSpan(BulletSpan(10), 0, text.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return string
    }

    private fun initRestartBtnClickListener() {
        binding.tvProfileRestart.setOnSingleClickListener {
            navigateToTendencySplashScreen()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnProfileDownload.setOnSingleClickListener {
            startImageDownload()
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnProfileBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun navigateToTendencySplashScreen() {
        Intent(this, TendencySplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
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
            saveImageToGallery()
        }
    }

    private fun saveImageToGallery() {
        val imageBitmap: Bitmap = BitmapFactory.decodeResource(
            resources,
            profileViewModel.mockProfileResult[profileViewModel.profileId.value ?: 0].downloadImage,
        )
        val imageFileName =
            DOWNLOAD_IMAGE_NAME.replace("%s", profileViewModel.profileId.value.toString())
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
}
