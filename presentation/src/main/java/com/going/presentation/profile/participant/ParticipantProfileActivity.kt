package com.going.presentation.profile.participant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.going.domain.entity.response.ParticipantProfileResponseModel
import com.going.presentation.R
import com.going.presentation.databinding.ActivityParticipantProfileBinding
import com.going.presentation.profile.edit.ProfileEditActivity
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.presentation.util.downloadImage
import com.going.ui.base.BaseActivity
import com.going.ui.extension.getWindowHeight
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.Behavior.DragCallback
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ParticipantProfileActivity :
    BaseActivity<ActivityParticipantProfileBinding>(R.layout.activity_participant_profile) {
    private val participantProfileViewModel by viewModels<ParticipantProfileViewModel>()
    private val participantId: Long by lazy {
        intent.getLongExtra(PARTICIPANT_ID, 0)
    }
    var isEmpty: Boolean = true

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

    private fun getParticipantProfile() =
        participantProfileViewModel.getUserInfoState(participantId)

    private fun observeParticipantProfileState() {
        participantProfileViewModel.participantProfile.flowWithLifecycle(lifecycle).onEach {
            it?.let { bindData(it) } ?: toast(getString(R.string.server_error))
        }.launchIn(lifecycleScope)
    }

    private fun bindData(profile: ParticipantProfileResponseModel) {
        binding.run {
            if (profile.result != -1) {
                isEmpty = false
                UserTendencyResultList[profile.result].run {
                    ivProfile.load(profileImage) {
                        transformations(CircleCropTransformation())
                    }
                }
            } else {
                setEmptyFragment()
                ivProfile.load(R.drawable.img_profile_guest) {
                    transformations(CircleCropTransformation())
                }
            }

            tvProfileName.text = profile.name
            tvProfileOneLine.text = profile.intro

            profile.isOwner.run {
                btnTripProfileDownload.isVisible = this && profile.result != -1
                btnProfileEdit.isVisible = this

                if (!this) tvTripProfileTitle.setText(
                    getString(
                        R.string.participant_profile_friend_title,
                        profile.name
                    )
                )
            }
        }
    }

    private fun setViewPager() {
        binding.vpTripProfile.adapter = ParticipantProfileViewPagerAdapter(this)
        binding.vpTripProfile.isUserInputEnabled = false
    }

    private fun setViewPagerDebounce() {
        binding.tabTripProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val params =
                    binding.appbarTripProfile.layoutParams as CoordinatorLayout.LayoutParams
                val behavior = params.behavior as AppBarLayout.Behavior?

                with(tab.position == 0 && isEmpty) {
                    behavior?.setDragCallback(object : DragCallback() {
                        override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                            return !this@with
                        }
                    })
                    setFragmentHeight(this)

                    if (this) binding.appbarTripProfile.setExpanded(true)

                }

                binding.vpTripProfile.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setEmptyFragment() {
        val params = binding.appbarTripProfile.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as AppBarLayout.Behavior?

        behavior?.setDragCallback(object : DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })


        setFragmentHeight()
    }

    private fun setFragmentHeight(temp: Boolean = true) {
        val displayHeight = getWindowHeight()
        val toolbarHeight = binding.tbTripProfile.height
        val appBarHeight = binding.appbarTripProfile.totalScrollRange
        val tabHeight = binding.tabTripProfile.height

        binding.vpTripProfile.layoutParams = binding.vpTripProfile.layoutParams.also {
            it.height =
                if (temp) displayHeight - toolbarHeight - appBarHeight - tabHeight else displayHeight
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnTripProfileBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initSaveImgBtnClickListener() {
        binding.btnTripProfileDownload.setOnSingleClickListener {
            downloadImage(participantProfileViewModel.number)
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
