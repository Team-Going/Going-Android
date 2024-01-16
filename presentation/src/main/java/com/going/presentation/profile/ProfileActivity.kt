package com.going.presentation.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.activity.viewModels
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
            this.downloadImage(profileViewModel.profileId.value ?: 0) { text ->
                toast(text)
            }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnProfileBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun navigateToTendencySplashScreen() {
        Intent(this, TendencySplashActivity::class.java).apply {
            finishAffinity()
            startActivity(this)
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
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.downloadImage(profileViewModel.profileId.value ?: 0) { text ->
                    toast(text)
                }
            } else {
                toast(getString(R.string.profile_image_download_error))
            }
        }
    }
}
