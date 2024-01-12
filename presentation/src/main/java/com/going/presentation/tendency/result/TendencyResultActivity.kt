package com.going.presentation.tendency.result

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestResultBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class TendencyResultActivity :
    BaseActivity<ActivityTendencyTestResultBinding>(R.layout.activity_tendency_result) {
    private val viewModel by viewModels<TendencyResultViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindTendencyInfo()

        initSaveImgBtnClickListener()
        initFinishBtnClickListener()
    }

    private fun bindTendencyInfo() {
        with(binding) {
            tvTendencyTestResultTitle.text = getString(R.string.tendency_test_result_title, "찐두릅")

            viewModel.mockTendencyResult.apply {
                tvTendencyTestResultType.text = profileTitle
                tvTendencyTestResultSubType.text = profileSubTitle

                tvTendencyTestResultTag1.text = tags[0]
                tvTendencyTestResultTag2.text = tags[1]
                tvTendencyTestResultTag3.text = tags[2]

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
