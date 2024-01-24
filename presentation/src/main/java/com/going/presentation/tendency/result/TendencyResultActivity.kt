package com.going.presentation.tendency.result

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityTendencyResultBinding
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.downloadImage
import com.going.presentation.util.initOnBackPressedListener
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.setBulletPoint
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TendencyResultActivity :
    BaseActivity<ActivityTendencyResultBinding>(R.layout.activity_tendency_result) {

    private val viewModel by viewModels<TendencyResultViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserInfo()
        observeUserInfoState()
        initRestartBtnClickListener()
        initSaveImgBtnClickListener()
        initFinishBtnClickListener()
        initOnBackPressedListener()
    }

    private fun getUserInfo() {
        viewModel.getUserInfoState()
    }

    private fun observeUserInfoState() {
        viewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> bindTendencyInfo(state.data.name, state.data.result)
                is UiState.Failure -> toast(state.msg)
                else -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun bindTendencyInfo(name: String, number: Int) {
        with(binding) {
            tvTendencyTestResultTitle.text = getString(R.string.tendency_test_result_title, name)

            UserTendencyResultList[number].apply {
                imgTendencyTestResult.setImageResource(resultImage)
                tvTendencyTestResultType.text = profileTitle
                tvTendencyTestResultSubType.text = profileSubTitle

                tvTendencyTestResultTag1.text = getString(R.string.tag, tags[0])
                tvTendencyTestResultTag2.text = getString(R.string.tag, tags[1])
                tvTendencyTestResultTag3.text = getString(R.string.tag, tags[2])

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
        binding.btnTendencyTestRestart.setOnSingleClickListener {
            navigateToTendencySplashScreen()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnTendencyResultDownload.setOnSingleClickListener {
            downloadImage(viewModel.tendencyId.value ?: 0)
        }
    }

    private fun initFinishBtnClickListener() {
        binding.btnTendencyResultFinish.setOnSingleClickListener {
            navigateToDashBoardScreen()
        }
    }

    private fun navigateToTendencySplashScreen() {
        Intent(this, TendencySplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun navigateToDashBoardScreen() {
        Intent(this, DashBoardActivity::class.java).apply {
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
                downloadImage(viewModel.tendencyId.value ?: 0)
            } else {
                toast(getString(R.string.profile_image_download_error))
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 200
    }
}
