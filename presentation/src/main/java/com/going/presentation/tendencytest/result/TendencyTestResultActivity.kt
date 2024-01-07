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

        initSaveImgBtnClickListener()
        initFinishBtnClickListener()
    }

    private fun bindTendencyInfo() {
        binding.tvTendencyTestResultTitle.text = getString(R.string.tendency_test_result_title, "찐두릅")
        binding.tvTendencyTestResultType.text = viewModel.mockTendencyResult.tendencyTitle
        binding.tvTendencyTestResultSubType.text = viewModel.mockTendencyResult.tendencySubTitle

        binding.tvTendencyTestResultTag1.text = viewModel.mockTendencyResult.tags[0]
        binding.tvTendencyTestResultTag2.text = viewModel.mockTendencyResult.tags[1]
        binding.tvTendencyTestResultTag3.text = viewModel.mockTendencyResult.tags[2]

        binding.tvFirstDescriptionTitle.text = viewModel.mockTendencyResult.tendencyBoxInfo[0].title
        binding.tvFirstDescriptionFirstText.text =
            setBulletPoint(viewModel.mockTendencyResult.tendencyBoxInfo[0].first)
        binding.tvFirstDescriptionSecondText.text =
            setBulletPoint(viewModel.mockTendencyResult.tendencyBoxInfo[0].second)
        binding.tvFirstDescriptionThirdText.text =
            setBulletPoint(viewModel.mockTendencyResult.tendencyBoxInfo[0].third)

        binding.tvSecondDescriptionTitle.text =
            viewModel.mockTendencyResult.tendencyBoxInfo[1].title
        binding.tvSecondDescriptionFirstText.text =
            setBulletPoint(viewModel.mockTendencyResult.tendencyBoxInfo[1].first)
        binding.tvSecondDescriptionSecondText.text =
            setBulletPoint(viewModel.mockTendencyResult.tendencyBoxInfo[1].second)
        binding.tvSecondDescriptionThirdText.text =
            setBulletPoint(viewModel.mockTendencyResult.tendencyBoxInfo[1].third)

        binding.tvThirdDescriptionTitle.text = viewModel.mockTendencyResult.tendencyBoxInfo[2].title
        binding.tvThirdDescriptionFirstText.text =
            setBulletPoint(viewModel.mockTendencyResult.tendencyBoxInfo[2].first)
        binding.tvThirdDescriptionSecondText.text =
            setBulletPoint(viewModel.mockTendencyResult.tendencyBoxInfo[2].second)
        binding.tvThirdDescriptionThirdText.text =
            setBulletPoint(viewModel.mockTendencyResult.tendencyBoxInfo[2].third)
    }

    private fun setBulletPoint(text: String): SpannableString {
        val string = SpannableString(text)
        string.setSpan(BulletSpan(10), 0, text.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return string
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnTendencyTestResultSave.setOnSingleClickListener {
            // 이미지 저장
        }
    }

    private fun initFinishBtnClickListener() {
        binding.btnTendencyTestResultFinish.setOnSingleClickListener {
            // 페이지 이동
        }
    }
}
