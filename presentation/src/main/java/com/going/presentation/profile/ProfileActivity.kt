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
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindProfileInfo()
    }

    private fun bindProfileInfo() {
        with(binding) {
            profileViewModel.mockProfileResult.apply {
                tvProfileType.text = profileTitle
                tvProfileSubType.text = profileSubTitle

                tvProfileTag1.text = tags[0]
                tvProfileTag2.text = tags[1]
                tvProfileTag3.text = tags[2]

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
}