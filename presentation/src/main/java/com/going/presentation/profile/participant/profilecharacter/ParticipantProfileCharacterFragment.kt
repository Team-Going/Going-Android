package com.going.presentation.profile.participant.profilecharacter

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.going.presentation.R
import com.going.presentation.databinding.FragmentParticipantProfileCharacterBinding
import com.going.presentation.designsystem.textview.ChartTextView
import com.going.presentation.profile.participant.ParticipantProfileViewModel
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.navigateToScreenClear
import com.going.ui.base.BaseFragment
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ParticipantProfileCharacterFragment :
    BaseFragment<FragmentParticipantProfileCharacterBinding>(R.layout.fragment_participant_profile_character) {
    private val participantViewModel by lazy {
        ViewModelProvider(requireActivity())[ParticipantProfileViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()
        btnParticipantProfileEmptyOwnerClickListener()
    }

    private fun setViewModel() {
        participantViewModel.participantProfile.flowWithLifecycle(lifecycle).onEach {
            it?.let { bindData(it.isOwner, it.result) } ?: toast(getString(R.string.server_error))
        }.launchIn(lifecycleScope)
    }

    private fun bindData(isOwner: Boolean, result: Int) {
        binding.run {
            btnTripProfileRestart.isVisible = isOwner
            btnParticipantProfileEmptyOwner.isVisible = isOwner
            tvParticipantProfileEmptyOwner.isVisible = isOwner

            viewParticipantProfileEmpty.isVisible = result == -1
            viewParticipantProfile.isVisible = result != -1

            if (result != -1) {
                UserTendencyResultList[result].run {
                    ivTripProfileBig.load(resultImage)

                    tvTripProfileTag1.text = getString(R.string.tag, tags[0])
                    tvTripProfileTag2.text = getString(R.string.tag, tags[1])
                    tvTripProfileTag3.text = getString(R.string.tag, tags[2])

                    with(profileBoxInfo[0]) {
                        setChartInfo(tvTripProfileChartFirst, title, first, second, third)
                    }
                    with(profileBoxInfo[1]) {
                        setChartInfo(tvTripProfileChartSecond, title, first, second, third)
                    }
                    with(profileBoxInfo[2]) {
                        setChartInfo(tvTripProfileChartThird, title, first, second, third)
                    }
                }
            }
        }
    }

    private fun btnParticipantProfileEmptyOwnerClickListener() {
        binding.btnParticipantProfileEmptyOwner.setOnClickListener {
            requireActivity().navigateToScreenClear<TendencySplashActivity>()
        }
    }

    private fun setChartInfo(
        chart: ChartTextView,
        title: String,
        first: String,
        second: String,
        third: String,
    ) {
        with(chart) {
            setTitle(title)
            setFirstDescription(first)
            setSecondDescription(second)
            setThirdDescription(third)
        }
    }
}