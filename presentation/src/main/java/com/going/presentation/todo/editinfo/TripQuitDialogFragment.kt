package com.going.presentation.todo.editinfo

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.going.presentation.R
import com.going.presentation.databinding.FragmentTripQuitDialogBinding
import com.going.ui.base.BaseDialog
import com.going.ui.extension.setOnSingleClickListener

class TripQuitDialogFragment :
    BaseDialog<FragmentTripQuitDialogBinding>(R.layout.fragment_trip_quit_dialog) {


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
            //대시보드 뷰로 이동
        }
    }
}
