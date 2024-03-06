package com.going.presentation.profile.trip

import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTripProfileBinding
import com.going.presentation.profile.edit.ProfileEditActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripProfileActivity :
    BaseActivity<ActivityTripProfileBinding>(R.layout.activity_trip_profile) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViewPager()
        setViewPagerDebounce()
        initBackBtnClickListener()
        initSaveImgBtnClickListener()
        initProfileEditBtnClickListener()
    }

    private fun setViewPager() {
        binding.vpTripProfile.adapter = TripProfileViewPagerAdapter(this)
    }

    private fun setViewPagerDebounce() {
        binding.tabTripProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpTripProfile.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initBackBtnClickListener() {
        binding.btnTripProfileBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnTripProfileDownload.setOnSingleClickListener {
            // downloadImage(tripProfileViewModel.profileId.value)
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
