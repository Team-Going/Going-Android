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
        with(binding) {
            tvTendencyTestResultTitle.text = getString(R.string.tendency_test_result_title, "찐두릅")

            viewModel?.mockTendencyResult?.apply {
                tvTendencyTestResultType.text = tendencyTitle
                tvTendencyTestResultSubType.text = tendencySubTitle

                tvTendencyTestResultTag1.text = tags[0]
                tvTendencyTestResultTag2.text = tags[1]
                tvTendencyTestResultTag3.text = tags[2]

                tvFirstDescriptionTitle.text = tendencyBoxInfo[0].title
                tvFirstDescriptionFirstText.text =
                    setBulletPoint(tendencyBoxInfo[0].first)
                tvFirstDescriptionSecondText.text =
                    setBulletPoint(tendencyBoxInfo[0].second)
                tvFirstDescriptionThirdText.text =
                    setBulletPoint(tendencyBoxInfo[0].third)

                tvSecondDescriptionTitle.text =
                    tendencyBoxInfo[1].title
                tvSecondDescriptionFirstText.text =
                    setBulletPoint(tendencyBoxInfo[1].first)
                tvSecondDescriptionSecondText.text =
                    setBulletPoint(tendencyBoxInfo[1].second)
                tvSecondDescriptionThirdText.text =
                    setBulletPoint(tendencyBoxInfo[1].third)

                tvThirdDescriptionTitle.text = tendencyBoxInfo[2].title
                tvThirdDescriptionFirstText.text =
                    setBulletPoint(tendencyBoxInfo[2].first)
                tvThirdDescriptionSecondText.text =
                    setBulletPoint(tendencyBoxInfo[2].second)
                tvThirdDescriptionThirdText.text =
                    setBulletPoint(tendencyBoxInfo[2].third)
            }
        }
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
