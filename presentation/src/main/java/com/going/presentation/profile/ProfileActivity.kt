package com.going.presentation.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.going.presentation.R
import com.going.presentation.databinding.ActivityProfileBinding
import com.going.presentation.tendency.result.TendencyResultActivity.Companion.PERMISSION_REQUEST_CODE
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.downloadImage
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.setBulletPoint
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
                    state.data.result,
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

            UserTendencyResultList[number].apply {
                ivProfile.load(profileImage) {
                    transformations(CircleCropTransformation())
                }
                tvProfileType.text = profileTitle
                tvProfileSubType.text = profileSubTitle

                tvProfileTag1.text = getString(R.string.tag, tags[0])
                tvProfileTag2.text = getString(R.string.tag, tags[1])
                tvProfileTag3.text = getString(R.string.tag, tags[2])

                tvFirstDescriptionTitle.text = profileBoxInfo[0].title
                tvFirstDescriptionFirstText.text =
                    profileBoxInfo[0].first.setBulletPoint()
                tvFirstDescriptionSecondText.text =
                    profileBoxInfo[0].second.setBulletPoint()
                tvFirstDescriptionThirdText.text =
                    profileBoxInfo[0].third.setBulletPoint()

                tvSecondDescriptionTitle.text =
                    profileBoxInfo[1].title
                tvSecondDescriptionFirstText.text =
                    profileBoxInfo[1].first.setBulletPoint()
                tvSecondDescriptionSecondText.text =
                    profileBoxInfo[1].second.setBulletPoint()
                tvSecondDescriptionThirdText.text =
                    profileBoxInfo[1].third.setBulletPoint()

                tvThirdDescriptionTitle.text = profileBoxInfo[2].title
                tvThirdDescriptionFirstText.text =
                    profileBoxInfo[2].first.setBulletPoint()
                tvThirdDescriptionSecondText.text =
                    profileBoxInfo[2].second.setBulletPoint()
                tvThirdDescriptionThirdText.text =
                    profileBoxInfo[2].third.setBulletPoint()
            }
        }
    }

    private fun initRestartBtnClickListener() {
        binding.tvProfileRestart.setOnSingleClickListener {
            navigateToTendencySplashScreen()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnProfileDownload.setOnSingleClickListener {
            downloadImage(profileViewModel.profileId.value ?: 0)
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnProfileBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun navigateToTendencySplashScreen() {
        Intent(this, TendencySplashActivity::class.java).apply {
            startActivity(this)
            finishAffinity()
        }
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                downloadImage(profileViewModel.profileId.value ?: 0)
            } else {
                toast(getString(R.string.profile_image_download_error))
            }
        }
    }
}
