package com.going.presentation.profile.participant.profiletag

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentParticipantProfileTagBinding
import com.going.presentation.profile.participant.ParticipantProfileViewModel
import com.going.presentation.profile.participant.profiletag.changetag.ChangeTagActivity
import com.going.ui.base.BaseFragment
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ParticipantProfileTagFragment :
    BaseFragment<FragmentParticipantProfileTagBinding>(R.layout.fragment_participant_profile_tag) {
    private val participantViewModel by lazy {
        ViewModelProvider(requireActivity())[ParticipantProfileViewModel::class.java]
    }

    private val tagViewModel by activityViewModels<ParticipantProfileTagViewModel>()

    private var _adapter: ParticipantProfileTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        getPreferenceIndex()
        initItemDecoration()
    }

    private fun initAdapter() {
        _adapter = ParticipantProfileTagAdapter()
        binding.rvPreferenceTag.adapter = adapter
    }

    private fun getPreferenceIndex() {
        participantViewModel.participantProfile.flowWithLifecycle(lifecycle).onEach {
            it?.let {
                adapter.submitList(
                    tagViewModel.setPreferenceData(
                        it.styleA,
                        it.styleB,
                        it.styleC,
                        it.styleD,
                        it.styleE
                    )
                )
                sendPreferenceWithClickListener(
                    it.styleA,
                    it.styleB,
                    it.styleC,
                    it.styleD,
                    it.styleE
                )
                checkIsOwner(it.isOwner)
            }
                ?: toast(getString(R.string.server_error))
        }.launchIn(lifecycleScope)
    }

    private fun sendPreferenceWithClickListener(
        styleA: Int,
        styleB: Int,
        styleC: Int,
        styleD: Int,
        styleE: Int
    ) {
        binding.btnTripProfileRestart.setOnSingleClickListener {
            ChangeTagActivity.createIntent(
                requireContext(),
                styleA, styleB, styleC, styleD, styleE, participantViewModel.tripId
            ).apply { startActivity(this) }
        }
    }

    private fun checkIsOwner(isOwner: Boolean) {
        binding.btnTripProfileRestart.isVisible = isOwner
    }

    private fun initItemDecoration() {
        val itemDeco = ParticipantProfileTagDecoration(requireContext())
        binding.rvPreferenceTag.addItemDecoration(itemDeco)
    }

    fun scrollTop() = binding.nsvPreferenceTag.scrollTo(0, 0)

}
