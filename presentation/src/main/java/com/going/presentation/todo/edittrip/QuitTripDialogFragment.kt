package com.going.presentation.todo.edittrip

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.FragmentQuitTripDialogBinding
import com.going.presentation.todo.edittrip.detail.DetailTripViewModel
import com.going.ui.base.BaseDialog
import com.going.ui.extension.setOnSingleClickListener

class QuitTripDialogFragment :
    BaseDialog<FragmentQuitTripDialogBinding>(R.layout.fragment_quit_trip_dialog) {

    private val viewModel by activityViewModels<DetailTripViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNegativeClickListener()
        initPositiveClickListener()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
        }
    }

    private fun initPositiveClickListener() {
        binding.tvDialogPositive.setOnSingleClickListener {
            dismiss()
        }
    }

    private fun initNegativeClickListener() {
        binding.tvDialogNegative.setOnSingleClickListener {
            viewModel.patchQuitTripFromServer()
            Intent(requireActivity(), DashBoardActivity::class.java).apply {
                requireActivity().startActivity(this)
            }
        }
    }
}
