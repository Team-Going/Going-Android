package com.going.presentation.todo.editinfo

import android.os.Bundle
import android.view.View
import com.going.presentation.R
import com.going.presentation.databinding.FragmentBottomSheetEditDateTripBinding
import com.going.ui.base.BaseBottomSheet
import com.going.ui.extension.setOnSingleClickListener
import java.util.Calendar

class BottomSheetEditDateFragment(val viewModel: EditTripInfoViewModel, val isStart: Boolean) :
    BaseBottomSheet<FragmentBottomSheetEditDateTripBinding>(R.layout.fragment_bottom_sheet_edit_date_trip) {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        if (isStart)
            customStartDate()
        else customEndDate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initFinishBtnClickListener()
    }

    private fun customStartDate() {
        val datePicker = binding.dpEditTripDate
        val calendar = Calendar.getInstance()

        val currentStartYear = calendar.get(Calendar.YEAR)
        val currentStartMonth = calendar.get(Calendar.MONTH)
        val currentStartDay = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.set(2000, 0, 1)
        datePicker.minDate = calendar.timeInMillis

        if (viewModel.endYear.value != null && viewModel.endMonth.value != null && viewModel.endDay.value != null) {
            calendar.set(
                viewModel.endYear.value ?: 0,
                (viewModel.endMonth.value ?: 0) - 1,
                viewModel.endDay.value ?: 0
            )
            datePicker.maxDate = calendar.timeInMillis
        } else {
            calendar.set(2100, 0, 1)
            datePicker.maxDate = calendar.timeInMillis
        }

        datePicker.updateDate(currentStartYear, currentStartMonth, currentStartDay)
    }

    private fun customEndDate() {
        val datePicker = binding.dpEditTripDate
        val calendar = Calendar.getInstance()

        val currentEndYear = viewModel.startYear.value ?: 0
        val currentEndMonth = viewModel.startMonth.value ?: 0
        val currentEndDay = viewModel.startDay.value ?: 0

        calendar.set(currentEndYear, currentEndMonth - 1, currentEndDay)
        datePicker.minDate = calendar.timeInMillis

        calendar.set(2100, 0, 1)
        datePicker.maxDate = calendar.timeInMillis
    }

    private fun sendDateInfo() {
        with(binding.dpEditTripDate) {
            if (isStart) {
                viewModel.setStartDate(year, month + 1, dayOfMonth)
            } else {
                customEndDate()
                viewModel.setEndDate(year, month + 1, dayOfMonth)
            }
        }
    }

    private fun initFinishBtnClickListener() {
        binding.btnEditTripSelect.setOnSingleClickListener {
            sendDateInfo()
            dismiss()
        }
    }
}

