package com.going.presentation.tendencytest.result

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestResultBinding
import com.going.ui.base.BaseActivity

class TendencyTestResultActivity :
    BaseActivity<ActivityTendencyTestResultBinding>(R.layout.activity_tendency_test_result) {
    private val viewModel by viewModels<TendencyTestResultViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvFirstDescriptionTitle.text = viewModel.mockList[0].title
        binding.tvFirstDescriptionFirstText.text = test(viewModel.mockList[0].first)
        binding.tvFirstDescriptionSecondText.text = test(viewModel.mockList[0].second)
        binding.tvFirstDescriptionThirdText.text = test(viewModel.mockList[0].third)

        binding.tvSecondDescriptionTitle.text = viewModel.mockList[1].title
        binding.tvSecondDescriptionFirstText.text = test(viewModel.mockList[1].first)
        binding.tvSecondDescriptionSecondText.text = test(viewModel.mockList[1].second)
        binding.tvSecondDescriptionThirdText.text = test(viewModel.mockList[1].third)

        binding.tvThirdDescriptionTitle.text = viewModel.mockList[2].title
        binding.tvThirdDescriptionFirstText.text = test(viewModel.mockList[2].first)
        binding.tvThirdDescriptionSecondText.text = test(viewModel.mockList[2].second)
        binding.tvThirdDescriptionThirdText.text = test(viewModel.mockList[2].third)
    }

    fun test(text: String): SpannableString {
        val string = SpannableString(text)
        string.setSpan(BulletSpan(), 0, text.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return string
    }
}
