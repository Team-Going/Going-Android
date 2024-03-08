package com.going.presentation.profile.trip.tripprofiletag.profiletag

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentTripProfileTagBinding
import com.going.presentation.profile.trip.tripprofiletag.changetag.ChangeTagActivity
import com.going.ui.base.BaseFragment
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripProfileTagFragment :
    BaseFragment<FragmentTripProfileTagBinding>(R.layout.fragment_trip_profile_tag) {

    private val viewModel by activityViewModels<TripProfileTagViewModel>()

    private var _adapter: PreferenceTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initItemDecoration()
        initRestartBtnClickListener()
    }

    private fun initAdapter() {
        _adapter = PreferenceTagAdapter()
        binding.rvPreferenceTag.adapter = adapter
        adapter.submitList(viewModel.setPreferenceData(1, 2, 3, 4, 5))
    }

    private fun initItemDecoration() {
        val itemDeco = TripProfileTagDecoration(requireContext())
        binding.rvPreferenceTag.addItemDecoration(itemDeco)
    }

    private fun initRestartBtnClickListener() {
        binding.btnTripProfileRestart.setOnSingleClickListener {
            Intent(requireContext(), ChangeTagActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    fun scrollTop() {
        binding.testNV.scrollTo(0, 0)
    }
}
