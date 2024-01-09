package com.going.presentation.profile

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityProfileBinding
import com.going.ui.base.BaseActivity

class ProfileActivity :
    BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
    private val viewModel by viewModels<ProfileViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindProfileInfo()
    }

    private fun bindProfileInfo() {
        with(binding) {
            viewModel?.mockProfileResult?.apply {
                tvProfileType.text = tendencyTitle
                tvProfileSubType.text = tendencySubTitle

                tvProfileTag1.text = tags[0]
                tvProfileTag2.text = tags[1]
                tvProfileTag3.text = tags[2]

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
}