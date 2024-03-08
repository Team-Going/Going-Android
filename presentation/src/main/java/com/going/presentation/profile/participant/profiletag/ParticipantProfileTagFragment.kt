package com.going.presentation.profile.participant.profiletag

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentParticipantProfileTagBinding
import com.going.presentation.profile.participant.profiletag.changetag.ChangeTagActivity
import com.going.ui.base.BaseFragment
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParticipantProfileTagFragment :
    BaseFragment<FragmentParticipantProfileTagBinding>(R.layout.fragment_participant_profile_tag) {

    private val viewModel by activityViewModels<ParticipantProfileTagViewModel>()

    private var _adapter: ParticipantProfileTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initItemDecoration()
        initRestartBtnClickListener()
    }

    private fun initAdapter() {
        _adapter = ParticipantProfileTagAdapter()
        binding.rvPreferenceTag.adapter = adapter
        adapter.submitList(viewModel.setPreferenceData(1, 2, 3, 4, 5))
    }

    private fun initItemDecoration() {
        val itemDeco = ParticipantProfileTagDecoration(requireContext())
        binding.rvPreferenceTag.addItemDecoration(itemDeco)
    }

    private fun initRestartBtnClickListener() {
        binding.btnTripProfileRestart.setOnSingleClickListener {
            Intent(requireContext(), ChangeTagActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

}
