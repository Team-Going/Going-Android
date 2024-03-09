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
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
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
    }

    private fun customEndDate() {
        binding.dpEditTripDate.apply {
            minDate = Calendar.getInstance().apply {
                set(
                    viewModel.startYear.value ?: 0,
                    (viewModel.startMonth.value ?: 0) - 1,
                    viewModel.startDay.value ?: 0
                )
            }.timeInMillis
            maxDate = Calendar.getInstance().apply {
                set(2100, 0, 1)
            }.timeInMillis
        }
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

