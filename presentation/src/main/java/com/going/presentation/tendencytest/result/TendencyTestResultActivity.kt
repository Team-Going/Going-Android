package com.going.presentation.tendencytest.result

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestResultBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class TendencyTestResultActivity :
    BaseActivity<ActivityTendencyTestResultBinding>(R.layout.activity_tendency_test_result) {
    private val viewModel by viewModels<TendencyTestResultViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindTendencyInfo()

        initFinishBtnClickListener()
        initRestartBtnClickListener()
    }

    private fun bindTendencyInfo() {
        binding.tvFirstDescriptionTitle.text = viewModel.mockList[0].title
        binding.tvFirstDescriptionFirstText.text = setBulletPoint(viewModel.mockList[0].first)
        binding.tvFirstDescriptionSecondText.text = setBulletPoint(viewModel.mockList[0].second)
        binding.tvFirstDescriptionThirdText.text = setBulletPoint(viewModel.mockList[0].third)

        binding.tvSecondDescriptionTitle.text = viewModel.mockList[1].title
        binding.tvSecondDescriptionFirstText.text = setBulletPoint(viewModel.mockList[1].first)
        binding.tvSecondDescriptionSecondText.text = setBulletPoint(viewModel.mockList[1].second)
        binding.tvSecondDescriptionThirdText.text = setBulletPoint(viewModel.mockList[1].third)

        binding.tvThirdDescriptionTitle.text = viewModel.mockList[2].title
        binding.tvThirdDescriptionFirstText.text = setBulletPoint(viewModel.mockList[2].first)
        binding.tvThirdDescriptionSecondText.text = setBulletPoint(viewModel.mockList[2].second)
        binding.tvThirdDescriptionThirdText.text = setBulletPoint(viewModel.mockList[2].third)
    }

    private fun setBulletPoint(text: String): SpannableString {
        val string = SpannableString(text)
        string.setSpan(BulletSpan(), 0, text.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return string
    }

    private fun initFinishBtnClickListener() {
        binding.btnTendencyTestResultFinish.setOnSingleClickListener {
            // 페이지 이동
        }
    }

    private fun initRestartBtnClickListener() {
        binding.btnTendencyTestResultRestart.setOnSingleClickListener {
            // 다시 시작
        }
    }
}
