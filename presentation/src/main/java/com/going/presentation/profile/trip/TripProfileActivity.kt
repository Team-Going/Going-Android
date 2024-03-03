package com.going.presentation.profile.trip

import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTripProfileBinding
import com.going.presentation.profile.edit.ProfileEditActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripProfileActivity :
    BaseActivity<ActivityTripProfileBinding>(R.layout.activity_trip_profile) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnClickListener()
        initSaveImgBtnClickListener()
        initProfileEditBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnTripProfileBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnTripProfileDownload.setOnSingleClickListener {
            // downloadImage(profileViewModel.profileId.value)
        }
    }

    private fun initProfileEditBtnClickListener() {
        binding.btnProfileEdit.setOnSingleClickListener {
            ProfileEditActivity.createIntent(
                this,
                binding.tvProfileName.text.toString(),
                binding.tvProfileOneLine.text.toString()
            ).apply {
                startActivity(this)
            }
        }
    }
}
