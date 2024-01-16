package com.going.presentation.tendency.result

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityTendencyResultBinding
import com.going.presentation.onboarding.signin.SignInActivity
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.downloadImage
import com.going.presentation.util.initOnBackPressedListener
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

    private var backPressedTime: Long = 0

    private val viewModel by viewModels<TendencyResultViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserInfo()
        observeUserInfoState()
        initRestartBtnClickListener()
        initSaveImgBtnClickListener()
        initFinishBtnClickListener()
        this.initOnBackPressedListener()
        //initOnBackPressedListener()
    }

    private fun getUserInfo() {
        viewModel.getUserInfoState()
    }

    private fun observeUserInfoState() {
        viewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach
                is UiState.Success -> bindTendencyInfo(state.data.name, state.data.result)
                is UiState.Failure -> toast(state.msg)
                is UiState.Empty -> return@onEach
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
            navigateToTendencySplashScreen()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnTendencyResultDownload.setOnSingleClickListener {
            this.downloadImage(viewModel.tendencyId.value ?: 0)
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
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.downloadImage(viewModel.tendencyId.value ?: 0)
            } else {
                toast(getString(R.string.profile_image_download_error))
            }
        }
    }

//    private fun initOnBackPressedListener() {
//        val onBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (System.currentTimeMillis() - backPressedTime >= SignInActivity.BACK_INTERVAL) {
//                    backPressedTime = System.currentTimeMillis()
//                    toast(getString(R.string.toast_back_pressed))
//                } else {
//                    finish()
//                }
//            }
//        }
//        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
//    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 200
        const val DOWNLOAD_PATH = "/Download/"
        const val DOWNLOAD_IMAGE_NAME = "img_tendency_result%s.png"
    }
}
