package com.going.presentation.createtrip

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.going.presentation.R
import com.going.presentation.databinding.FragmentBottomSheetDateContentBinding
import com.going.ui.base.BaseBottomSheet
import com.going.ui.extension.setOnSingleClickListener

class DateBottomSheet(val viewModel: CreateTripViewModel, val isStart: Boolean) :
    BaseBottomSheet<FragmentBottomSheetDateContentBinding>(R.layout.fragment_bottom_sheet_date_content) {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFinishBtnClickListener()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun sendDateInfo() {
        if (isStart) {
            viewModel.StartYear.value = binding.dpCreateTripDate.year
            viewModel.StartMonth.value = binding.dpCreateTripDate.month + 1
            viewModel.StartDay.value = binding.dpCreateTripDate.dayOfMonth
            viewModel.checkStartDateAvailable()
        } else if (viewModel.StartYear.value?.let { binding.dpCreateTripDate.year > it } ?: true) {
            viewModel.EndYear.value = binding.dpCreateTripDate.year
            viewModel.EndMonth.value = binding.dpCreateTripDate.month + 1
            viewModel.EndDay.value = binding.dpCreateTripDate.dayOfMonth
            viewModel.checkEndDateAvailable()
        }
    }

    private fun initFinishBtnClickListener() {
        binding.btnCreateTripFinish.setOnSingleClickListener {
            sendDateInfo()
            dismiss()
        }
    }
}
