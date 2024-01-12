package com.going.presentation.tendency.result

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.activity.viewModels
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

@AndroidEntryPoint
class TendencyResultActivity :
    BaseActivity<ActivityTendencyResultBinding>(R.layout.activity_tendency_result) {
    private val viewModel by viewModels<TendencyResultViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getuserInfo()
        observeUserInfoState()
        initRestartBtnClickLitener()
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

    private fun initRestartBtnClickLitener() {
        binding.btnTendencyTestRestart.setOnSingleClickListener {
            navigateToTendencyTestScreen()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnTendencyTestResultSave.setOnSingleClickListener {
            toast("추후 업데이트 예정")
        }
    }

    private fun initFinishBtnClickListener() {
        binding.btnTendencyTestResultFinish.setOnSingleClickListener {
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
}
