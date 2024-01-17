package com.going.presentation.entertrip.createtrip.choosedate

import android.os.Bundle
import android.view.View
import com.going.presentation.R
import com.going.presentation.databinding.FragmentBottomSheetDateContentBinding
import com.going.ui.base.BaseBottomSheet
import com.going.ui.extension.setOnSingleClickListener
import java.util.Calendar

class BottomSheetDateContentFragment(val viewModel: CreateTripViewModel, val isStart: Boolean) :
    BaseBottomSheet<FragmentBottomSheetDateContentBinding>(R.layout.fragment_bottom_sheet_date_content) {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        customDate()
        customEndDate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initFinishBtnClickListener()
    }

    private fun customDate() {
        val datePicker = binding.dpCreateTripDate
        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.set(2000, 0, 1)
        datePicker.minDate = calendar.timeInMillis

        calendar.set(2100, 0, 1)
        datePicker.maxDate = calendar.timeInMillis

        datePicker.updateDate(currentYear, currentMonth, currentDay)
    }


    private fun customEndDate() {
        val datePicker = binding.dpCreateTripDate
        val calendar = Calendar.getInstance()

        val currentYear = viewModel.startYear.value ?: 0
        val currentMonth = viewModel.startMonth.value ?: 0
        val currentDay = viewModel.startDay.value ?: 0

        calendar.set(currentYear, currentMonth - 1, currentDay)
        datePicker.minDate = calendar.timeInMillis

        calendar.set(2100, 0, 1)
        datePicker.maxDate = calendar.timeInMillis
    }

    private fun sendDateInfo() {
        if (isStart) {
            viewModel.startYear.value = binding.dpCreateTripDate.year
            viewModel.startMonth.value = binding.dpCreateTripDate.month + 1
            viewModel.startDay.value = binding.dpCreateTripDate.dayOfMonth
            viewModel.checkStartDateAvailable()
        } else {
            customEndDate()
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

                if (startDate.before(endDate) || startDate.equals(endDate)) {
                    viewModel.checkStartDateAvailable()
                    viewModel.checkEndDateAvailable()
                    dismiss()
                }
            } else
                dismiss()
        }
    }
}



