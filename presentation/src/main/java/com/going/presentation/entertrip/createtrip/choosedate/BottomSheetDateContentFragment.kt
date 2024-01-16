package com.going.presentation.entertrip.createtrip.choosedate

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        destroyToast()
        initFinishBtnClickListener()
    }


    private fun destroyToast() {
        dialog?.setOnDismissListener {
            binding.tvErrorToast.visibility = View.GONE
        }
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

                if (startDate.before(endDate) || startDate.equals(endDate)) {
                    viewModel.checkStartDateAvailable()
                    viewModel.checkEndDateAvailable()
                    dismiss()
                } else {
                    viewModel.apply {
                        startYear.value = null
                        endYear.value = null
                        checkStartDateAvailable()
                        checkEndDateAvailable()
                    }
                    binding.viewBlank.visibility = View.VISIBLE
                    binding.tvErrorToast.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.tvErrorToast.visibility = View.GONE
                    }, 2000)
                }
            } else {
                dismiss()
            }
        }
    }
}



