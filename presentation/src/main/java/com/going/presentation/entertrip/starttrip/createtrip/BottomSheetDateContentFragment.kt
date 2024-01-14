package com.going.presentation.entertrip.starttrip.createtrip

import android.os.Bundle
import android.view.View
import com.going.presentation.R
import com.going.presentation.databinding.FragmentBottomSheetDateContentBinding
import com.going.presentation.entertrip.starttrip.invitetrip.CreateTripViewModel
import com.going.ui.base.BaseBottomSheet
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import java.util.Calendar

class BottomSheetDateContentFragment(val viewModel: CreateTripViewModel, val isStart: Boolean) :
    BaseBottomSheet<FragmentBottomSheetDateContentBinding>(R.layout.fragment_bottom_sheet_date_content) {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFinishBtnClickListener()
    }

    private fun sendDateInfo() {
        if (isStart) {
            viewModel.startYear.value = binding.dpCreateTripDate.year
            viewModel.startMonth.value = binding.dpCreateTripDate.month + 1
            viewModel.startDay.value = binding.dpCreateTripDate.dayOfMonth
            viewModel.checkStartDateAvailable()
        } else {
            viewModel.endYear.value = binding.dpCreateTripDate.year
            viewModel.endMonth.value = binding.dpCreateTripDate.month + 1
            viewModel.endDay.value = binding.dpCreateTripDate.dayOfMonth
            viewModel.checkEndDateAvailable()
        }
    }

    private fun initFinishBtnClickListener() {
        binding.btnCreateTripFinish.setOnSingleClickListener {
            sendDateInfo()
            if (viewModel.isStartDateAvailable.value == true && viewModel.isEndDateAvailable.value == true) {
                val calendar = Calendar.getInstance()

                calendar.set(Calendar.YEAR, viewModel.startYear.value ?: 0)
                calendar.set(Calendar.MONTH, viewModel.startMonth.value ?: 0)
                calendar.set(Calendar.DAY_OF_MONTH, viewModel.startDay.value ?: 0)
                val startDate = calendar.time

                calendar.set(Calendar.YEAR, viewModel.endYear.value ?: 0)
                calendar.set(Calendar.MONTH, viewModel.endMonth.value ?: 0)
                calendar.set(Calendar.DAY_OF_MONTH, viewModel.endDay.value ?: 0)
                val endDate = calendar.time

                if(startDate.before(endDate)) {
                    dismiss()
                } else {
                    toast(getString(R.string.create_trip_toast_error))
                }
            } else {
                dismiss()
            }
        }
    }
}
