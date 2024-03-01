package com.going.presentation.profile

import android.Manifest
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
import com.going.presentation.designsystem.textview.ChartTextView
import com.going.presentation.tendency.result.TendencyResultActivity.Companion.PERMISSION_REQUEST_CODE
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.downloadImage
import com.going.presentation.util.navigateToScreenClear
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
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
        initBackBtnClickListener()
        initSaveImgBtnClickListener()
        initProfileEditBtnClickListener()
        initRestartBtnClickListener()
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

                ivProfileBig.load(resultImage)

                tvProfileTag1.text = getString(R.string.tag, tags[0])
                tvProfileTag2.text = getString(R.string.tag, tags[1])
                tvProfileTag3.text = getString(R.string.tag, tags[2])

                with(profileBoxInfo[0]) {
                    setChartInfo(tvChartFirst, title, first, second, third)
                }
                with(profileBoxInfo[1]) {
                    setChartInfo(tvChartSecond, title, first, second, third)
                }
                with(profileBoxInfo[2]) {
                    setChartInfo(tvChartThird, title, first, second, third)
                }

            }
        }
    }

    private fun setChartInfo(
        chart: ChartTextView,
        title: String,
        first: String,
        second: String,
        third: String,
    ) {
        with(chart) {
            setTitle(title)
            setFirstDescription(first)
            setSecondDescription(second)
            setThirdDescription(third)
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnProfileBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnProfileDownload.setOnSingleClickListener {
            downloadImage(profileViewModel.profileId.value)
        }
    }

    private fun initProfileEditBtnClickListener() {
        binding.btnProfileEdit.setOnSingleClickListener {
            ProfileEditActivity.createIntent(
                this,
                binding.tvProfileName.text.toString(),
                binding.tvProfileType.text.toString()
            ).apply {
                startActivity(this)
            }
        }
    }

    private fun initRestartBtnClickListener() {
        binding.btnProfileRestart.setOnSingleClickListener {
            navigateToScreenClear<TendencySplashActivity>()
        }
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
