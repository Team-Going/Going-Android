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
import com.going.presentation.designsystem.textview.ChartTextView
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.downloadImage
import com.going.presentation.util.initOnBackPressedListener
import com.going.presentation.util.navigateToScreen
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
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

    private fun initRestartBtnClickListener() {
        binding.btnTendencyTestRestart.setOnSingleClickListener {
            navigateToScreen<TendencySplashActivity>(listOf(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnTendencyResultDownload.setOnSingleClickListener {
            downloadImage(viewModel.tendencyId.value)
        }
    }

    private fun initFinishBtnClickListener() {
        binding.btnTendencyResultFinish.setOnSingleClickListener {
            navigateToScreen<DashBoardActivity>(
                listOf(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP,
                    Intent.FLAG_ACTIVITY_NEW_TASK,
                    Intent.FLAG_ACTIVITY_CLEAR_TASK,
                ),
            )
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
                downloadImage(viewModel.tendencyId.value)
            } else {
                toast(getString(R.string.profile_image_download_error))
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 200
    }
}
