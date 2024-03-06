package com.going.presentation.profile.participant

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.going.presentation.R
import com.going.presentation.databinding.FragmentTripProfileCharacterBinding
import com.going.presentation.designsystem.textview.ChartTextView
import com.going.presentation.tendency.result.UserTendencyResultList
import com.going.ui.base.BaseFragment
import com.going.ui.state.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ParticipantProfileCharacterFragment :
    BaseFragment<FragmentTripProfileCharacterBinding>(R.layout.fragment_trip_profile_character) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val participantViewModel =
            ViewModelProvider(requireActivity()).get(ParticipantProfileViewModel::class.java)

        participantViewModel.participantProfileState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> bindData(state.data.isOwner, state.data.result)
                else -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun bindData(isOwner: Boolean, result: Int) {
        binding.run {
            binding.btnTripProfileRestart.isVisible = isOwner

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
            } else {
                // empty view
            }
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