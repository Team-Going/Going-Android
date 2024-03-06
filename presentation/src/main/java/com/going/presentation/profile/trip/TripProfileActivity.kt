package com.going.presentation.profile.trip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.going.domain.entity.response.ParticipantProfileResponseModel
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTripProfileBinding
import com.going.presentation.profile.edit.ProfileEditActivity
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TripProfileActivity :
    BaseActivity<ActivityTripProfileBinding>(R.layout.activity_trip_profile) {
    private val participantProfileViewModel by viewModels<ParticipantProfileViewModel>()
    private val participantId: Long by lazy {
        intent.let { intent.getLongExtra(PARTICIPANT_ID, 0) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getParticipantProfile()
        observeParticipantProfileState()
        setViewPager()
        setViewPagerDebounce()
        initBackBtnClickListener()
        initSaveImgBtnClickListener()
        initProfileEditBtnClickListener()
    }

    private fun getParticipantProfile() = participantProfileViewModel.getUserInfoState(participantId)

    private fun observeParticipantProfileState() {
        participantProfileViewModel.participantProfileState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                when (state) {
                    is UiState.Loading -> return@onEach
                    is UiState.Success -> bindData(state.data)
                    is UiState.Failure -> toast(state.msg)
                    is UiState.Empty -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun bindData(profile: ParticipantProfileResponseModel) {
        binding.run {
            if (profile.result != -1) {
                UserTendencyResultList[profile.result].run {
                    ivProfile.load(profileImage) {
                        transformations(CircleCropTransformation())
                    }
                }
            } else {
                ivProfile.load(R.drawable.img_profile_guest) {
                    transformations(CircleCropTransformation())
                }
            }

            tvProfileName.text = profile.name
            tvProfileOneLine.text = profile.intro




            profile.isOwner.run {
                btnTripProfileDownload.isVisible = this
                btnProfileEdit.isVisible = this
            }
        }
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


    companion object {
        private const val PARTICIPANT_ID = "PARTICIPANT_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            participantId: String,
        ): Intent = Intent(context, ProfileEditActivity::class.java).apply {
            putExtra(PARTICIPANT_ID, participantId)
        }
    }
}
