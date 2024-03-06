package com.going.presentation.profile.trip.tag

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentTripProfileTagBinding
import com.going.presentation.entertrip.preferencetag.PreferenceTagDecoration
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripProfileTagFragment :
    BaseFragment<FragmentTripProfileTagBinding>(R.layout.fragment_trip_profile_tag) {

    private val viewModel by activityViewModels<TripProfileTagViewModel>()

    private var _adapter: TripProfileTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initItemDecoration()
    }

    private fun initAdapter() {
        _adapter = TripProfileTagAdapter(requireContext())
        binding.rvPreferenceTag.adapter = adapter
        adapter.submitList(viewModel.preferenceTagList)
    }

    private fun initItemDecoration() {
        val itemDeco = PreferenceTagDecoration(requireContext())
        binding.rvPreferenceTag.addItemDecoration(itemDeco)
    }

}
