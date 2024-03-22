package com.going.presentation.todo.edittrip.editinfo

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
                viewModel.currentStartYear ?: calendar.get(Calendar.YEAR),
                (viewModel.currentStartMonth ?: calendar.get(Calendar.MONTH)) - 1,
                viewModel.currentStartDay ?: calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        if (viewModel.endYear != viewModel.currentEndYear || viewModel.endMonth != viewModel.currentEndMonth || viewModel.endDay != viewModel.currentEndDay) {
            calendar.set(
                viewModel.endYear ?: 0,
                (viewModel.endMonth ?: 0) - 1,
                viewModel.endDay ?: 0
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
                viewModel.currentEndYear ?: calendar.get(Calendar.YEAR),
                (viewModel.currentEndMonth ?: calendar.get(Calendar.MONTH)) - 1,
                viewModel.currentEndDay ?: calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        if (viewModel.startYear != viewModel.currentStartYear || viewModel.startMonth != viewModel.currentStartMonth || viewModel.startDay != viewModel.currentStartDay) {
            calendar.set(
                viewModel.startYear ?: 0,
                (viewModel.startMonth ?: 0) - 1,
                viewModel.startDay ?: 0
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
            viewModel.checkTripAvailable(viewModel.title, viewModel.currentTitle, viewModel.startDate, viewModel.endDate, viewModel.currentStartDate, viewModel.currentEndDate)
            dismiss()
        }
    }
}

