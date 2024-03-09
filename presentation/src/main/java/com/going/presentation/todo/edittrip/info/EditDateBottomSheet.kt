package com.going.presentation.todo.edittrip.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentEditDateTripBottomSheetBinding
import com.going.ui.base.BaseBottomSheet
import com.going.ui.extension.setOnSingleClickListener
import java.util.Calendar

class EditDateBottomSheet(val isStart: Boolean) :
    BaseBottomSheet<FragmentEditDateTripBottomSheetBinding>(R.layout.fragment_edit_date_trip_bottom_sheet) {
    private val viewModel by activityViewModels<EditTripInfoViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        if (isStart) customStartDate()
        else customEndDate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initFinishBtnClickListener()
    }

    private fun customStartDate() {
        val calendar = Calendar.getInstance()
        val datePicker = binding.dpEditTripDate.apply {
            updateDate(
                viewModel.currentStartYear.value ?: calendar.get(Calendar.YEAR),
                (viewModel.currentStartMonth.value ?: calendar.get(Calendar.MONTH)) - 1,
                viewModel.currentStartDay.value ?: calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        if (viewModel.endYear.value != viewModel.currentEndYear.value || viewModel.endMonth.value != viewModel.currentEndMonth.value || viewModel.endDay.value != viewModel.currentEndDay.value) {
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
    }

    private fun customEndDate() {
        val calendar = Calendar.getInstance()
        val datePicker = binding.dpEditTripDate.apply {
            updateDate(
                viewModel.currentEndYear.value ?: calendar.get(Calendar.YEAR),
                (viewModel.currentEndMonth.value ?: calendar.get(Calendar.MONTH)) - 1,
                viewModel.currentEndDay.value ?: calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        if (viewModel.startYear.value != viewModel.currentStartYear.value || viewModel.startMonth.value != viewModel.currentStartMonth.value || viewModel.startDay.value != viewModel.currentStartDay.value) {
            calendar.set(
                viewModel.startYear.value ?: 0,
                (viewModel.startMonth.value ?: 0) - 1,
                viewModel.startDay.value ?: 0
            )
            datePicker.minDate = calendar.timeInMillis
        } else {
            calendar.set(2000, 0, 1)
            datePicker.minDate = calendar.timeInMillis
        }
    }

    private fun sendDateInfo() {
        with(binding.dpEditTripDate) {
            if (isStart) {
                viewModel.setStartDate(year, month + 1, dayOfMonth)
            } else {
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

